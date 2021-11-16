package com.wcc.platform.streaming.engine;

import com.wcc.platform.streaming.enums.EngineType;
import com.wcc.platform.streaming.exception.PlatformStreamingException;

import java.util.HashMap;
import java.util.Map;

public class StreamingEngineFactory {

    static Map<String, String> engineMap = new HashMap<>();

    static {
        engineMap.put(EngineType.FLINKSQL.name(), "com.wcc.platform.streaming.flink.core.engine.FlinkStreamingEngine");
    }

    public static StreamingEngine getEngine(String engineType) {
        if (engineType == null) {
            throw new PlatformStreamingException();
        }
        try {
            String className = engineMap.get(engineType.toUpperCase());
            Class<?> clazz = Class.forName(className);
            if (!StreamingEngine.class.isAssignableFrom(clazz)) {
                throw new PlatformStreamingException();
            }
            return clazz.asSubclass(StreamingEngine.class).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            throw new PlatformStreamingException(e);
        }
    }
}