package com.sber.currency.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CurrencyRequest {
    private String charCode;
    private Double amount;
}
