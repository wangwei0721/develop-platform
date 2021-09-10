package com.wcc.platform.streaming.engine;

import com.wcc.platform.streaming.enums.EngineType;

public abstract class AbstractStreamingEngine implements StreamingEngine {

    public AbstractStreamingEngine() {
        StreamingEngineFactory.engineMap.put(getEngineType().name(), this.getClass());
    }

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
    abstract EngineType getEngineType();
}