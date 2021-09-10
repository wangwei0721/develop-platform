package com.wcc.platform.streaming.launcher;

import com.wcc.platform.streaming.constant.StreamingConstant;
import com.wcc.platform.streaming.engine.StreamingEngine;
import com.wcc.platform.streaming.engine.StreamingEngineFactory;
import com.wcc.platform.streaming.job.JobConfig;
import com.wcc.platform.streaming.job.JobParamsFactory;
import com.wcc.platform.util.ArgsUtil;

/**
 * 启动入口
 *
 * @author wangwei
 * @since 2021-09-10
 */
public class Application {

    /**
     * @param args args
     *             engineType=flinkSql runMode=local jobConfig=/xxx/yyy.json
     * @author wangwei
     * @since 2021-09-10
     */
    public static void main(String[] args) {
        String engineType = ArgsUtil.convert2Map(args).get(StreamingConstant.ENGINE_TYPE);
        JobConfig jobConfig = JobParamsFactory.getJobParams(engineType).parseJobConfig(args);
        StreamingEngine streamingEngine = StreamingEngineFactory.getEngine(engineType);
        streamingEngine.init(jobConfig);
        streamingEngine.start();
    }

}