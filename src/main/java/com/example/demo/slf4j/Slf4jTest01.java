package com.example.demo.slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by 25790 on 2021/11/28.
 */
public class Slf4jTest01 {
   private final static   Logger logger = LoggerFactory.getLogger(Slf4jTest01.class);

    public static void main(String[] args) {
        logger.trace("trace....");
        logger.debug("debug....");
        logger.info("info....");
        logger.warn("warn....");
        logger.error("error....");
    }
}
