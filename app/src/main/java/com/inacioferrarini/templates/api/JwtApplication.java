package com.inacioferrarini.templates.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JwtApplication {

    private static final Logger logger = LoggerFactory.getLogger(JwtApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(
                JwtApplication.class,
                args
        );

        logger.trace("Hello from Logback - trace");
        logger.debug("Hello from Logback - debug");
        logger.info("Hello from Logback - info");
        logger.warn("Hello from Logback - warn");
        logger.error("Hello from Logback - error");
    }

}
