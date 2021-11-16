package com.wcc.platform.streaming.flink.core.constant;

public class ConfigConstant {

    public static final String FLINK_CHECKPOINT_STATE_BACKEND = "sql.checkpoint.state.backend";

    public static final String FLINK_CHECKPOINT_INTERVAL_KEY = "sql.checkpoint.interval";

    public static final String FLINK_CHECKPOINT_MODE_KEY = "sql.checkpoint.mode";

    public static final String FLINK_CHECKPOINT_TIMEOUT_KEY = "sql.checkpoint.timeout";

    public static final String FLINK_MAXCONCURRENTCHECKPOINTS_KEY = "sql.max.concurrent.checkpoints";

    public static final String FLINK_CHECKPOINT_CLEANUPMODE_KEY = "sql.checkpoint.cleanup.mode";

    public static final String FLINK_CHECKPOINT_DATAURI_KEY = "flinkCheckpointDataURI";

    public static final String SQL_ENV_PARALLELISM = "sql.env.parallelism";

    public static final String SQL_MAX_ENV_PARALLELISM = "sql.max.env.parallelism";

    public static final String SQL_BUFFER_TIMEOUT_MILLIS = "sql.buffer.timeout.millis";

    public static final String FLINK_TIME_CHARACTERISTIC_KEY = "time.characteristic";

    public static final String SQL_TTL_MINTIME = "sql.ttl.min";

    public static final String SQL_TTL_MAXTIME = "sql.ttl.max";

    public static final String SQL_EXECUTE_ENGINE = "sql.execute.engine";

    public static final String TASK_RESTART_STRATEGY = "restart-strategy";

    public static final String FAILURE_RATE_MAX_FAILURES_PER_INTERVAL = "restart-strategy.failure-rate.max-failures-per-interval";

    public static final String FAILURE_RATE_FAILURE_RATE_INTERVAL = "restart-strategy.failure-rate.failure-rate-interval";

    public static final String FAILURE_RATE_DELAY = "restart-strategy.failure-rate.delay";

    /**
     * default restart plocy
     */
    public static final int failureRate = 3;

    public static final int failureInterval = 6; //min

    public static final int delayInterval = 10; //sec
}
