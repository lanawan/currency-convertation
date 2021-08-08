package com.sber.currency.service;

import com.sber.currency.model.Currency;
import com.sber.currency.model.CurrencyRequest;
import com.sber.currency.model.ValCurs;

import java.text.ParseException;
import java.util.List;

public interface ICurrencyService {
    List<Currency> getAllCurrencies();
    String currencyConversion(CurrencyRequest currencyRequest) throws ParseException;
    ValCurs getValCurs();
}
