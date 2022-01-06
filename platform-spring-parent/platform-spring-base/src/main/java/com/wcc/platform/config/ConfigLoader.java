package com.wcc.platform.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.config.ConfigFileApplicationListener;
import org.springframework.context.ApplicationListener;

import java.util.Set;

@Slf4j
public class ConfigLoader {

    public static void load(SpringApplication application, String configName) {
        configName = "application," + configName;
        Set<ApplicationListener<?>> listeners = application.getListeners();
        for (ApplicationListener<?> listener : listeners) {
            log.warn("{}", listener.getClass().getName());
            if (listener instanceof ConfigFileApplicationListener) {
                log.info("启动加载配置文件：[{}]", configName);
                ((ConfigFileApplicationListener) listener).setSearchNames(configName);
            }
        }
    }
}
