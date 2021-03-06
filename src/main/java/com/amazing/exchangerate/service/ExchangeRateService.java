package com.amazing.exchangerate.service;

import com.amazing.exchangerate.dto.request.ExchangeRateDto;
import com.amazing.exchangerate.dto.response.ExchangeRateDtoResponse;
import io.reactivex.Completable;
import io.reactivex.Single;

import java.util.Currency;


public interface ExchangeRateService {


     Single<String> Add(ExchangeRateDto exchangeRateDto);

     Single<ExchangeRateDtoResponse> findCurrency(Currency currency);

     Single<Double> Convert(Currency from, Currency to, Double amount);

     Completable Update(ExchangeRateDto exchangeRateDto);

     Completable Delete(ExchangeRateDto exchangeRateDto);
}
