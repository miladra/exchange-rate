package com.disqo.exchangerate.service;

import com.disqo.exchangerate.dto.response.ExchangeRate;
import io.reactivex.Single;

public interface LiveCurrencyExchangeRateService {

  Single<ExchangeRate> getLiveExchangeRateUpdateCacheBaseTime();

  Single<ExchangeRate> getLiveExchangeRateUpdateCacheBaseHigherًRate();
}
