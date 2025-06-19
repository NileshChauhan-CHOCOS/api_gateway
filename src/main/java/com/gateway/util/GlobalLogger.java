package com.gateway.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GlobalLogger {
    private static final Logger Log = LoggerFactory.getLogger(GlobalLogger.class);
    
    public static Logger logger(){
        return Log;
    }
}
