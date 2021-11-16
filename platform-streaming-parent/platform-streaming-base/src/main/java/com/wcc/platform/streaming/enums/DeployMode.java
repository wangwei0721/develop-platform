package com.wcc.platform.streaming.enums;

/**
 * 部署运行模式
 *
 * @author wangwei
 * @since 2021-09-10
 */
public enum DeployMode {

    LOCAL(1),
    STANDALONE(2),
    YARN(3);

    private int type;

    DeployMode(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public static DeployMode getOrDefault(String modeName) {
        for (DeployMode deployMode : values()) {
            if (deployMode.name().toLowerCase().equalsIgnoreCase(modeName)) {
                return deployMode;
            }
        }
        return LOCAL;
    }

}
