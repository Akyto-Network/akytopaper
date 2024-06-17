package org.github.paperspigot.utils;

import org.apache.commons.lang.exception.ExceptionUtils;

public class DebugUtils {

    public static String stack() {
        return ExceptionUtils.getFullStackTrace(new Throwable());
    }

    public static void exception(Throwable e) {
        exception(null, e);
    }

    public static void exception(String msg, Throwable e) {
        if (msg != null) {
            System.err.println(msg);
        }
        if (e.getMessage() != null) {
            System.err.println(e.getMessage());
        }
        for (String line : ExceptionUtils.getFullStackTrace(e).split("\n")) {
            System.err.println(line);
        }
    }

}
