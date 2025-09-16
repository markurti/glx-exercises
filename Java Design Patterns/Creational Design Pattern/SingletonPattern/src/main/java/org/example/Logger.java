package org.example;

import java.io.Serial;
import java.io.Serializable;

public class Logger implements Serializable, Cloneable {
    private static Logger instance;

    private Logger() {}

    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("Clone is not supported for Logger object.");
    }

    public static Logger getLoggerInstance() {
        if (instance == null) {
            synchronized (Logger.class) {
                if (instance == null) {
                    instance = new Logger();
                    return instance;
                }
            }
        }
        return instance;
    }

    @Serial
    private Object readResolve() {
        return instance;
    }

    public void Log(String message) {
        System.out.println(message);
    }
}
