package com.wcc.platform.streaming.flink.core.v10.source.console;

import com.wcc.platform.streaming.flink.core.sink.SinkTableInfo;

public class ConsoleSinkTable extends SinkTableInfo {

    private static final String CURR_TYPE = "console";

    public ConsoleSinkTable() {
        setType(CURR_TYPE);
    }

    @Override
    protected boolean check() {
        return true;
    }

    @Override
    public String getType() {
        return super.getType().toUpperCase();
    }
}
