package com.wcc.platform.streaming.job;

import com.wcc.platform.streaming.enums.EngineType;
import com.wcc.platform.streaming.enums.RunMode;

/**
 * 任务通用配置
 *
 * @author wangwei
 * @since 2021-09-10
 */
public abstract class JobConfig {

    private String id;
    private String name;
    private EngineType engineType;
    private RunMode runMode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EngineType getEngineType() {
        return engineType;
    }

    public void setEngineType(EngineType engineType) {
        this.engineType = engineType;
    }

    public RunMode getRunMode() {
        return runMode;
    }

    public void setRunMode(RunMode runMode) {
        this.runMode = runMode;
    }
}
