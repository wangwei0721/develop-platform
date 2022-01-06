package com.wcc.platform.eureka.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class WebConfig extends WebSecurityConfigurerAdapter {


    @Value("${spring.security.basic.enabled:false}")
    private boolean enabled;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        if (!enabled) {
            /**
             * 关闭csrf
             */
            http.httpBasic().and().csrf().disable();
        } else {
            /**
             * 开启认证
             */
            http.csrf().ignoringAntMatchers("/eureka/**");
            super.configure(http);
        }
        // http.csrf().disable();
        // http.authorizeRequests().anyRequest().authenticated().and().httpBasic();
    }
}
