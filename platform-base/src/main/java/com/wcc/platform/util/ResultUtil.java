package com.wcc.platform.util;

import com.wcc.platform.vo.Result;
import com.wcc.platform.enums.ResultEnum;

public class ResultUtil {

    public static Result ok() {
        return build();
    }

    public static Result ok(Object data) {
        Result<Object> result = build();
        result.setData(data);
        return result;
    }

    public static Result ok(String msg) {
        Result<Object> result = build();
        result.setMsg(msg);
        return result;
    }

    public static Result ok(Object data, String msg) {
        Result<Object> result = build();
        result.setData(data);
        result.setMsg(msg);
        return result;
    }

    public static Result fail() {
        Result<Object> result = build();
        result.setCode(ResultEnum.FAILURE.getCode());
        return result;
    }

    public static Result fail(String msg) {
        Result result = fail();
        result.setMsg(msg);
        return result;
    }

    private static Result<Object> build() {
        Result<Object> result = new Result<>();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setCtime(System.currentTimeMillis());
        return result;
    }

}
