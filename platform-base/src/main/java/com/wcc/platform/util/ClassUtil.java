package com.wcc.platform.util;

import com.wcc.platform.exception.PlatformException;
import com.wcc.platform.exception.PlatformRuntimeException;

public class ClassUtil<T> {

    public static <T> T instance(Class<T> superClazz, String className) throws PlatformException {
        try {
            Class<?> clazz = Class.forName(className);
            if (!clazz.isAssignableFrom(superClazz)) {
                throw new PlatformRuntimeException(String.format("%s not subClass of %s", className, superClazz.getName()));
            }
            return clazz.asSubclass(superClazz).newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new PlatformRuntimeException(e);
        }
    }
}
