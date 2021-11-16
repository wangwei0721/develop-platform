package com.wcc.platform.streaming.flink.core.v10.source;

import com.wcc.platform.streaming.exception.PlatformStreamingException;
import com.wcc.platform.streaming.flink.core.source.AbsStreamSourceFactory;
import com.wcc.platform.streaming.flink.core.source.SourceTableInfo;
import com.wcc.platform.streaming.flink.core.source.StreamSourceGener;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.TableEnvironment;

public class StreamSourceFactoryV10 extends AbsStreamSourceFactory {

    @Override
    public <T> T generateStreamSource(SourceTableInfo sourceTableInfo, StreamExecutionEnvironment env, TableEnvironment tableEnv) {
        try {
            String type = sourceTableInfo.getType();
            Class<?> clazz = Class.forName(type);
            if (!clazz.isAssignableFrom(StreamSourceGener.class)) {
                throw new PlatformStreamingException("class " + clazz.getName() + " not subClass of StreamSourceGener");
            }
            StreamSourceGener gener = clazz.asSubclass(StreamSourceGener.class).newInstance();
            return (T) gener.genStreamSource(sourceTableInfo, env, tableEnv);
        } catch (Exception e) {
            throw new PlatformStreamingException(e);
        }
    }
}
