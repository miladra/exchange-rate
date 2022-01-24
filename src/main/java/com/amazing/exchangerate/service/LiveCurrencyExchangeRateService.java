package com.amazing.exchangerate.service;

import com.amazing.exchangerate.dto.response.ExchangeRate;
import io.reactivex.Single;

public interface LiveCurrencyExchangeRateService {

  Single<ExchangeRate> getLiveExchangeRateUpdateCacheBaseTime();

  Single<ExchangeRate> getLiveExchangeRateUpdateCacheBaseHigherًRate();
}
