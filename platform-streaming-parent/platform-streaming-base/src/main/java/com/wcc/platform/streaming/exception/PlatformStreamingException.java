package com.wcc.platform.streaming.exception;

import java.io.Serializable;

public class PlatformStreamingException extends RuntimeException implements Serializable {


    public PlatformStreamingException() {
    }

    public PlatformStreamingException(Throwable cause) {
        super(cause);
    }
}
