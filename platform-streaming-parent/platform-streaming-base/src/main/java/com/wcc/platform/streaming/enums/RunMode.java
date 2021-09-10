package com.wcc.platform.streaming.enums;

/**
 * 运行模式
 *
 * @author wangwei
 * @since 2021-09-10
 */
public enum RunMode {

    LOCAL(1),
    STANDALONE(2),
    YARN(3);

    private int type;

    RunMode(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
