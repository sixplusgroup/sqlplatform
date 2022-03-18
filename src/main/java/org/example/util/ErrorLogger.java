package org.example.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author shenyichen
 * @date 2022/3/6
 **/
public class ErrorLogger {
    private static Logger logger = Logger.getLogger("Error Logger");

    public static void log(Level level, String msg) {
        logger.log(level,msg + "\n");
    }

    public static void log(Level level, String msg, Exception e) {
        StringWriter trace = new StringWriter();
        e.printStackTrace(new PrintWriter(trace));
        logger.log(level,msg + "\n" + trace.toString());
    }

    public static void logSevere(String msg) {
        log(Level.SEVERE, msg);
    }

    public static void logSevere(String msg, Exception e) {
        log(Level.SEVERE, msg, e);
    }
}
