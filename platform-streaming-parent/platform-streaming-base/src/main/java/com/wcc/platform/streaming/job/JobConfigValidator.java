package com.wcc.platform.streaming.job;

public interface JobConfigValidator {

    /**
     * 必要参数校验
     *
     * @param jobConfig jobConfig
     * @author wangwei
     * @since 2021-09-10
     */
    void validate(JobConfig jobConfig);
}
