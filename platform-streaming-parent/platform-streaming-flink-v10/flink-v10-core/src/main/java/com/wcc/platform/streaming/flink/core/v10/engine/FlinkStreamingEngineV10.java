package com.wcc.platform.streaming.flink.core.v10.engine;

import com.wcc.platform.streaming.flink.core.engine.AbstractFlinkStreamingEngine;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.java.StreamTableEnvironment;

public class FlinkStreamingEngineV10 extends AbstractFlinkStreamingEngine {

    @Override
    protected TableEnvironment createTableEnv(StreamExecutionEnvironment exeEnv) {
        return StreamTableEnvironment.create(exeEnv);
    }

}