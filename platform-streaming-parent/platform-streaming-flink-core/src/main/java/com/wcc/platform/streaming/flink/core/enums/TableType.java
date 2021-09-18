package com.wcc.platform.streaming.flink.core.enums;

public enum TableType {

    /**
     * 源表
     */
    SOURCE(1),
    /**
     * 目标表
     */
    SINK(2);

    int type;

    TableType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
