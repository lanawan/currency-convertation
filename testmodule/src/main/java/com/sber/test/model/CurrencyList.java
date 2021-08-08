package com.sber.test.model;

import com.sber.currency.model.Currency;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CurrencyList {
    private List<Currency> currencies;

    public CurrencyList() {
        currencies = new ArrayList<>();
    }
}
