package com.sber.currency.controller;

import com.sber.currency.service.ICurrencyService;
import com.sber.currency.model.Currency;
import com.sber.currency.model.CurrencyRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api/currency")
@AllArgsConstructor
public class CurrencyController {

    private ICurrencyService iCurrencyService;

    @GetMapping
    public List<Currency> getCurrencyCharCode() {
        return iCurrencyService.getAllCurrencies();
    }

    @PostMapping
    public String currencyConversion(@RequestBody CurrencyRequest currencyRequest) throws ParseException {
        return iCurrencyService.currencyConversion(currencyRequest);
    }
}
