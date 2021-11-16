package com.wcc.platform.util;

import java.io.Closeable;
import java.io.IOException;

public class CloseUtil {

    public static void close(Object obj) {
        if (obj instanceof Closeable) {
            try {
                ((Closeable) obj).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (obj instanceof AutoCloseable) {
            try {
                ((AutoCloseable) obj).close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
