package com.disqo.exchangerate.utility;

import com.disqo.exchangerate.exception.InvalidCurrencyException;

import java.util.Currency;

public class util {

    public static Currency parseToCurrency(String stringCurrency) {
        Currency currency;
        try {
            currency = Currency.getInstance(stringCurrency);
        } catch (IllegalArgumentException ex) {
            throw new InvalidCurrencyException(String.format("Given currency: '%s' is invalid.", stringCurrency), ex);
        }
        return currency;
    }
}
