package com.disqo.exchangerate.service;

import com.disqo.exchangerate.dto.response.ExchangeRate;
import io.reactivex.Single;

public interface ExchangeRateGateway {

  Single<ExchangeRate> getExchangeRate();
}
