package com.example.demo;

import org.junit.jupiter.api.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {
	Logger logger = LoggerFactory.getLogger(getClass());
	@Test
	void contextLoads() {
		logger.trace("trace....");
		logger.debug("debug....");
		logger.info("info....");
		logger.warn("warn....");
		logger.error("error....");
	}

}
