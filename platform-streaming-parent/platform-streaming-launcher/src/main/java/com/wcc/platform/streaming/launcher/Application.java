package com.wcc.platform.streaming.launcher;

import com.wcc.platform.streaming.constant.StreamingConstant;
import com.wcc.platform.streaming.engine.StreamingEngine;
import com.wcc.platform.streaming.engine.StreamingEngineFactory;
import com.wcc.platform.util.ArgsUtil;

import java.util.Map;

/**
 * 启动入口
 *
 * @author wangwei
 * @since 2021-09-10
 */
public class Application {

    /**
     * @param args args
     *             engineType=flinkSql jobConfig=/xxx/yyy
     * @author wangwei
     * @since 2021-09-10
     */
    public static void main(String[] args) {
        args = new String[]{"engineType=flinkSql", "jobConfig=/Users/wangwei/IdeaProjects/example/develop-platform/platform-streaming-parent/platform-streaming-launcher/src/main/resources/task/cs.json"};
        Map<String, String> argsMap = ArgsUtil.convert2Map(args);
        String engineType = argsMap.get(StreamingConstant.ENGINE_TYPE);
        String jobConfig = argsMap.get(StreamingConstant.JOB_CONF);
        StreamingEngine streamingEngine = StreamingEngineFactory.getEngine(engineType);
        try {
            streamingEngine.init(jobConfig);
            streamingEngine.start();
        } catch (Exception e) {
            streamingEngine.stop();
            e.printStackTrace();
        }
    }

}