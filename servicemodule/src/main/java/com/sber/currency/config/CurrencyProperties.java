package com.sber.currency.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "currency")
@Getter
@Setter
public class CurrencyProperties {
    private String host;
    private String schemaXSD;
}
