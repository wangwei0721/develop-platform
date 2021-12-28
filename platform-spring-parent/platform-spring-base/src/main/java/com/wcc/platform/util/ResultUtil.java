package com.wcc.platform.util;

import com.wcc.platform.enums.ResultEnum;
import com.wcc.platform.vo.Result;

public class ResultUtil {

    public static Result ok() {
        return build().build();
    }

    public static Result ok(Object data) {
        return build().data(data).build();
    }

    public static Result fail() {
        return build().code(ResultEnum.FAILURE.getCode()).build();
    }

    public static Result fail(Object data) {
        return build().code(ResultEnum.FAILURE.getCode()).data(data).build();
    }

    private static Result.ResultBuilder<Object> build() {
        Result.ResultBuilder<Object> builder = Result.builder();
        builder.code(ResultEnum.SUCCESS.getCode())
                .ctime(System.currentTimeMillis());
        return builder;
    }
}
