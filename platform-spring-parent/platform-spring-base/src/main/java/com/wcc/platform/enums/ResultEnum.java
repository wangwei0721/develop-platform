package com.wcc.platform.enums;

public enum ResultEnum {

    SUCCESS("200"),
    FAILURE("444");

    private String code;

    ResultEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
