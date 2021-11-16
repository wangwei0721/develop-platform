package com.wcc.platform.streaming.flink.core.v10.sink;

import com.wcc.platform.streaming.exception.PlatformStreamingException;
import com.wcc.platform.streaming.flink.core.sink.AbsStreamSinkFactory;
import com.wcc.platform.streaming.flink.core.sink.SinkTableInfo;
import com.wcc.platform.streaming.flink.core.sink.StreamSinkGener;

public class StreamSinkFactoryV10 extends AbsStreamSinkFactory {

    @Override
    public <T> T getTableSink(SinkTableInfo sinkTableInfo) {
        try {
            String type = sinkTableInfo.getType();
            Class<?> clazz = Class.forName(type);
            if (!clazz.isAssignableFrom(StreamSinkGener.class)) {
                throw new PlatformStreamingException("class " + clazz.getName() + " not subClass of StreamSinkGener");
            }
            StreamSinkGener gener = clazz.asSubclass(StreamSinkGener.class).newInstance();
            return (T) gener.genStreamSink(sinkTableInfo);
        } catch (Exception e) {
            throw new PlatformStreamingException(e);
        }
    }

}
