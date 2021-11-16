package com.wcc.platform.streaming.flink.core.engine;

import com.wcc.platform.streaming.flink.core.job.FlinkJobConfig;
import com.wcc.platform.streaming.flink.core.side.SideTableInfo;
import com.wcc.platform.streaming.flink.core.sql.SqlTree;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;

import java.net.URL;
import java.util.List;
import java.util.Map;

public interface FlinkStreamEnvironment {


    /**
     * 注册自定义函数
     *
     * @param tableEnv  tableEnv
     * @param sqlTree   sqlTree
     * @param jarURList jarURList
     * @author wangwei
     * @since 2021-10-21
     */
    void registerUDF(TableEnvironment tableEnv, SqlTree sqlTree, List<URL> jarURList);

    /**
     * 注册表
     *
     * @param config         config
     * @param env            env
     * @param tableEnv       tableEnv
     * @param sqlTree        sqlTree
     * @param sideTables     sideTables
     * @param registerTables registerTables
     * @author wangwei
     * @since 2021-10-21
     */
    void registerTable(FlinkJobConfig config, StreamExecutionEnvironment env, TableEnvironment tableEnv, SqlTree sqlTree,
                       Map<String, SideTableInfo> sideTables, Map<String, Table> registerTables);

    /**
     * 注册转换sql算法
     *
     * @param config         config
     * @param tableEnv       tableEnv
     * @param sqlTree        sqlTree
     * @param sideTables     sideTables
     * @param registerTables registerTables
     * @author wangwei
     * @since 2021-10-21
     */
    void sqlTranslation(FlinkJobConfig config, TableEnvironment tableEnv, SqlTree sqlTree,
                        Map<String, SideTableInfo> sideTables, Map<String, Table> registerTables);


}
