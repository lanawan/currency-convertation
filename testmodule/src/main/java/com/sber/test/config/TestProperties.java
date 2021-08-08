package com.sber.test.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "scheduler")
@Getter
@Setter
public class TestProperties {
    private Integer maxErrors;
    private Integer maxTimes;
    private String testCurrencyHost;
}
