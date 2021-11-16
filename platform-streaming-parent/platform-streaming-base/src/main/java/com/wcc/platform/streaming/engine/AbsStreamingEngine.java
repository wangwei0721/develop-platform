package com.wcc.platform.streaming.engine;

import com.wcc.platform.streaming.enums.EngineType;

public abstract class AbsStreamingEngine implements StreamingEngine {

    @Override
    public void stop() {
    }

    /**
     * 计算引擎类型
     *
     * @return com.wcc.platform.streaming.enums.EngineType
     * @author wangwei
     * @since 2021-09-10
     */
    protected abstract EngineType getEngineType();
}