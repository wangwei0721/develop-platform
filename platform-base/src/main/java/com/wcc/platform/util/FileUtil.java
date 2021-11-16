package com.wcc.platform.util;

import java.io.*;

public class FileUtil {

    public static String readContent(String path) {
        if (path == null) {
            return null;
        }
        File file = new File(path);
        if (!file.exists()) {
            return null;
        }
        if (!file.isFile()) {
            return null;
        }
        FileReader reader = null;
        BufferedReader bufferedReader = null;
        try {
            reader = new FileReader(file);
            bufferedReader = new BufferedReader(reader);
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line);
            }

            return builder.toString();
        } catch (IOException e) {
            return null;
        } finally {
            CloseUtil.close(bufferedReader);
            CloseUtil.close(reader);
        }
    }
}
