package com.wcc.platform.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private String code;
    private String msg;
    private T data;
    private long ctime;

}