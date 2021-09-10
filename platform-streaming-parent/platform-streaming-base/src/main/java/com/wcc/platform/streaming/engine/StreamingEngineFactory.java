package com.wcc.platform.streaming.engine;

import com.wcc.platform.streaming.exception.PlatformStreamingException;

import java.util.HashMap;
import java.util.Map;

public class StreamingEngineFactory {

    static Map<String, Class<? extends StreamingEngine>> engineMap = new HashMap<>();

    public static StreamingEngine getEngine(String engineType) {
        if (engineType == null) {
            throw new PlatformStreamingException();
        }
        Class<? extends StreamingEngine> clazz = engineMap.get(engineType.toUpperCase());
        if (clazz == null) {
            throw new PlatformStreamingException();
        }
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new PlatformStreamingException(e);
        }
    }
}