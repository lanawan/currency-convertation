package com.sber.test.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class TestConfig {
    private final TestProperties testProperties;
}
