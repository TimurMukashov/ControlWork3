package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.logging.*;

public class LoggerConfig {
    public static Logger createLogger(Class<?> className) throws IOException {
        Handler fileHandler = new FileHandler("logger.log", true);
        fileHandler.setFormatter(new SimpleFormatter());
        Logger logger = Logger.getLogger(className.getName());
        logger.addHandler(fileHandler);
        logger.setLevel(Level.FINE);
        return logger;
    }
}
