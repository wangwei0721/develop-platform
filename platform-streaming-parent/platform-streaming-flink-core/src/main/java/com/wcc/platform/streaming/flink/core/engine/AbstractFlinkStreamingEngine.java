package com.wcc.platform.streaming.flink.core.engine;

import com.wcc.platform.streaming.engine.AbsStreamingEngine;
import com.wcc.platform.streaming.enums.EngineType;
import com.wcc.platform.streaming.exception.PlatformStreamingException;
import com.wcc.platform.streaming.flink.core.constant.ConfigConstant;
import com.wcc.platform.streaming.flink.core.job.FlinkJobConfig;
import com.wcc.platform.streaming.flink.core.job.FlinkJobParams;
import com.wcc.platform.streaming.flink.core.side.SideTableInfo;
import com.wcc.platform.streaming.flink.core.sql.SqlParser;
import com.wcc.platform.streaming.flink.core.sql.SqlTree;
import com.wcc.platform.streaming.flink.core.util.FlinkUtil;
import com.wcc.platform.util.ClassUtil;
import com.wcc.platform.util.LoggerUtil;
import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.api.common.JobExecutionResult;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.common.time.Time;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.graph.StreamGraph;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * flink引擎入口
 *
 * @author wangwei
 * @since 2021-09-18
 */
public abstract class AbstractFlinkStreamingEngine extends AbsStreamingEngine {

    protected FlinkJobConfig config;

    @Override
    protected EngineType getEngineType() {
        return EngineType.FLINKSQL;
    }

    @Override
    public void init(String jobConfig) {
        config = new FlinkJobParams(jobConfig).parse();
    }

    @Override
    public void start() {
        try {
            // 第一步：创建StreamExecutionEnvironment环境
            StreamExecutionEnvironment exeEnv = getStreamExeEnv(config.getParams(), config.getDeployMode());

            // 第二步：创建StreamTableEnvironment环境
            TableEnvironment tableEnv = createTableEnv(exeEnv);

            // 第三步：创建FlinkStreamEnvironment环境
            FlinkStreamEnvironment streamEnv = ClassUtil.instance(FlinkStreamEnvironment.class, config.getEngineDriver());

            // 第四步：sql语法解析
            List<URL> jarURList = new ArrayList<>();
            SqlTree sqlTree = SqlParser.parseSql(config.getSql());
            // 第5步：将sql解析结果注册到环境中去
            Map<String, SideTableInfo> sideTables = new HashMap<>();
            Map<String, Table> registerTableCache = new HashMap<>();
            // 注册udf
            streamEnv.registerUDF(tableEnv, sqlTree, jarURList);
            // 注册数据源表
            streamEnv.registerTable(config, exeEnv, tableEnv, sqlTree, sideTables, registerTableCache);
            // 注册算子
            streamEnv.sqlTranslation(config, tableEnv, sqlTree, sideTables, registerTableCache);

            // 第6步：提交执行
            String executionPlan = exeEnv.getExecutionPlan();
            // DAG图
            LoggerUtil.debug(AbstractFlinkStreamingEngine.class, executionPlan);
            StreamGraph streamGraph = exeEnv.getStreamGraph(config.getName());
            JobExecutionResult result = exeEnv.execute(streamGraph);

            System.out.println(result.getJobID());

        } catch (Exception e) {
            LoggerUtil.error(AbstractFlinkStreamingEngine.class, "start job err", e);
            throw new PlatformStreamingException(e);
        }
    }

    /**
     * 从StreamExecutionEnvironment构建StreamTableEnvironment
     * 版本原因，实际返回的是
     *
     * @param exeEnv exeEnv
     * @return org.apache.flink.table.api.TableEnvironment
     * @author wangwei
     * @since 2021-11-10
     */
    protected abstract TableEnvironment createTableEnv(StreamExecutionEnvironment exeEnv);

    /**
     * 构造执行环境
     *
     * @param confProperties confProperties
     * @param deployMode     deployMode
     * @return org.apache.flink.streaming.api.environment.StreamExecutionEnvironment
     * @author wangwei
     * @since 2021-10-21
     */
    private static StreamExecutionEnvironment getStreamExeEnv(Properties confProperties, String deployMode) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env.getConfig().disableClosureCleaner();

        Configuration globalJobParameters = new Configuration();
        Method method = Configuration.class.getDeclaredMethod("setValueInternal", String.class, Object.class);
        method.setAccessible(true);
        for (Map.Entry<Object, Object> prop : confProperties.entrySet()) {
            method.invoke(globalJobParameters, prop.getKey(), prop.getValue());
        }

        ExecutionConfig exeConfig = env.getConfig();
        if (exeConfig.getGlobalJobParameters() == null) {
            exeConfig.setGlobalJobParameters(globalJobParameters);
        } else if (exeConfig.getGlobalJobParameters() instanceof Configuration) {
            ((Configuration) exeConfig.getGlobalJobParameters()).addAll(globalJobParameters);
        }

        // 并行度
        env.setParallelism(FlinkUtil.getEnvParallelism(confProperties));
        // 最大并行度
        if (FlinkUtil.getMaxEnvParallelism(confProperties) > 0) {
            env.setMaxParallelism(FlinkUtil.getMaxEnvParallelism(confProperties));
        }
        if (FlinkUtil.getBufferTimeoutMillis(confProperties) > 0) {
            env.setBufferTimeout(FlinkUtil.getBufferTimeoutMillis(confProperties));
        }
        // 重启策略
        env.setRestartStrategy(RestartStrategies.failureRateRestart(
                ConfigConstant.failureRate,
                Time.of(ConfigConstant.failureInterval, TimeUnit.MINUTES),
                Time.of(ConfigConstant.delayInterval, TimeUnit.SECONDS)
        ));
        // 时间
        FlinkUtil.setStreamTimeCharacteristic(env, confProperties);
        // checkpoint
        FlinkUtil.openCheckpoint(env, confProperties);
        return env;
    }

}
