package com.wcc.platform.util;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;


public class LoggerUtil {

    public static void error(Class<?> clazz, String msg) {
        getLogger(clazz).info(msg);
    }

    public static void error(Class<?> clazz, String msg, Exception e) {
        getLogger(clazz).info(msg, e);
    }

    public static void warn(Class<?> clazz, String msg) {
        getLogger(clazz).info(msg);
    }

    public static void info(Class<?> clazz, String msg) {
        getLogger(clazz).info(msg);
    }

    public static void debug(Class<?> clazz, String msg) {
        getLogger(clazz).debug(msg);
    }

    private static Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }

}
