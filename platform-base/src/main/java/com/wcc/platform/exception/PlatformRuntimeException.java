package com.wcc.platform.exception;

import java.io.Serializable;

public class PlatformRuntimeException extends RuntimeException implements Serializable {


    public PlatformRuntimeException() {
    }

    public PlatformRuntimeException(String message) {
        super(message);
    }

    public PlatformRuntimeException(Throwable cause) {
        super(cause);
    }
}
