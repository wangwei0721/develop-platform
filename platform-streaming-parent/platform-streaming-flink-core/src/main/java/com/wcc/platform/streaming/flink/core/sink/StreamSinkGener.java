package com.wcc.platform.streaming.flink.core.sink;

public interface StreamSinkGener<T> {

    /**
     * 构造sink链接
     *
     * @param sinkTableInfo sinkTableInfo
     * @return T
     * @author wangwei
     * @since 2021-11-10
     */
    T genStreamSink(SinkTableInfo sinkTableInfo);
}
