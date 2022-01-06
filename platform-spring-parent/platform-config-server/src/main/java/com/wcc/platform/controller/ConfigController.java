package com.wcc.platform.controller;


import com.wcc.platform.util.ResultUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * 配置管理
 *
 * @author wcc
 * @since 2022-01-06
 */
@RestController
public class ConfigController {

    @GetMapping("getConfig/{attr}")
    public Object getConfig(@PathVariable(name = "attr") String attr) {

        return ResultUtil.ok();
    }

}
