package com.wcc.platform.util;

import java.util.HashMap;
import java.util.Map;

public class ArgsUtil {

    public static Map<String, String> convert2Map(String[] args) {
        // 命令行参数
        Map<String, String> params = new HashMap<>();
        for (String arg : args) {
            String[] param = arg.split("=");
            String name = param[0].trim();
            if (!name.equals("")) {
                params.put(name, param[1].trim());
            }
        }
        return params;
    }
}
