package org.test.testspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class TestSpringApplication {

    public static void main(String[] args) {
        SpringApplication. run(TestSpringApplication.class, args);
    }

}
