package com.wcc.platform.streaming.flink.core.util;

import com.wcc.platform.streaming.exception.PlatformStreamingException;
import com.wcc.platform.streaming.flink.core.constant.ConfigConstant;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.io.IOException;
import java.util.Properties;

public class FlinkUtil {


    /**
     * 默认并行度
     *
     * @param properties properties
     * @return int
     * @author wangwei
     * @since 2021-10-21
     */
    public static int getEnvParallelism(Properties properties) {
        return Integer.parseInt(properties.getProperty(ConfigConstant.SQL_ENV_PARALLELISM, "1"));
    }


    /**
     * 最大并发度
     *
     * @param properties properties
     * @return int
     * @author wangwei
     * @since 2021-10-21
     */
    public static int getMaxEnvParallelism(Properties properties) {
        return Integer.parseInt(properties.getProperty(ConfigConstant.SQL_MAX_ENV_PARALLELISM, "0"));
    }

    /**
     * buffer 超时时间
     *
     * @param properties properties
     * @return long
     * @author wangwei
     * @since 2021-10-21
     */
    public static long getBufferTimeoutMillis(Properties properties) {
        return Long.parseLong(properties.getProperty(ConfigConstant.SQL_BUFFER_TIMEOUT_MILLIS, "0"));
    }

    /**
     * #ProcessingTime(默认),IngestionTime,EventTime
     *
     * @param env
     * @param properties
     */
    public static void setStreamTimeCharacteristic(StreamExecutionEnvironment env, Properties properties) {
        if (!properties.containsKey(ConfigConstant.FLINK_TIME_CHARACTERISTIC_KEY)) {
            return;
        }
        String characteristicStr = properties.getProperty(ConfigConstant.FLINK_TIME_CHARACTERISTIC_KEY);
        TimeCharacteristic timeCharacteristic = TimeCharacteristic.valueOf(characteristicStr);
        if (timeCharacteristic == null) {
            throw new PlatformStreamingException("illegal property :" + ConfigConstant.FLINK_TIME_CHARACTERISTIC_KEY);
        }
        env.setStreamTimeCharacteristic(timeCharacteristic);
    }

    /**
     * 开启checkpoint
     *
     * @param env
     * @throws IOException
     */
    public static void openCheckpoint(StreamExecutionEnvironment env, Properties properties) throws IOException {
        String checkpointInterval = properties.getProperty(ConfigConstant.FLINK_CHECKPOINT_INTERVAL_KEY);
        if (checkpointInterval == null) {
            return;
        } else {
            env.enableCheckpointing(Long.parseLong(checkpointInterval));
        }
        CheckpointConfig checkpointConfig = env.getCheckpointConfig();
        String checkMode = properties.getProperty(ConfigConstant.FLINK_CHECKPOINT_MODE_KEY);
        if (checkMode != null) {
            checkpointConfig.setCheckpointingMode(CheckpointingMode.valueOf(checkMode));
        }
        String checkpointTimeout = properties.getProperty(ConfigConstant.FLINK_CHECKPOINT_TIMEOUT_KEY);
        if (checkpointTimeout != null) {
            checkpointConfig.setCheckpointTimeout(Long.parseLong(checkpointTimeout));
        }
        String maxConcurrCheckpoints = properties.getProperty(ConfigConstant.FLINK_MAXCONCURRENTCHECKPOINTS_KEY);
        if (maxConcurrCheckpoints != null) {
            checkpointConfig.setMaxConcurrentCheckpoints(Integer.parseInt(maxConcurrCheckpoints));
        }
        // checkpoint 清理模式 默认走 RETAIN_ON_CANCELLATION
        if (Boolean.parseBoolean(properties.getProperty(ConfigConstant.FLINK_CHECKPOINT_CLEANUPMODE_KEY))) {
            checkpointConfig.enableExternalizedCheckpoints(
                    CheckpointConfig.ExternalizedCheckpointCleanup.DELETE_ON_CANCELLATION);
        } else {
            checkpointConfig.enableExternalizedCheckpoints(
                    CheckpointConfig.ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION);
        }
    }


    public static TypeInformation[] transformTypes(Class[] fieldTypes) {
        TypeInformation[] types = new TypeInformation[fieldTypes.length];
        for (int i = 0; i < fieldTypes.length; i++) {
            types[i] = TypeInformation.of(fieldTypes[i]);
        }

        return types;
    }
}
