package com.wcc.platform.controller;

import com.wcc.platform.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class PlatformController {

    @Value("${spring.application.name}")
    String platform;

    @GetMapping("/hello")
    public Object hello() {
        return ResultUtil.ok(String.format("welcome to %s", platform));
    }

    @ExceptionHandler(value = Exception.class)
    public Object exceptionHandler(Exception e) {
        log.error(e.toString());
        return ResultUtil.fail(e.getLocalizedMessage());
    }

}
