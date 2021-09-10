package com.wcc.platform.streaming.job;

import com.wcc.platform.streaming.exception.PlatformStreamingException;

import java.util.HashMap;
import java.util.Map;

public class JobParamsFactory {

    static Map<String, Class<? extends JobParams>> jobParams = new HashMap<>();

    public static JobParams getJobParams(String engineType) {
        if (engineType == null) {
            throw new PlatformStreamingException();
        }
        Class<? extends JobParams> clazz = jobParams.get(engineType.toUpperCase());
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
