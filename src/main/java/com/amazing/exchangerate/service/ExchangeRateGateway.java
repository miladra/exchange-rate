package com.amazing.exchangerate.service;

import com.amazing.exchangerate.dto.response.ExchangeRate;
import io.reactivex.Single;

public interface ExchangeRateGateway {

  Single<ExchangeRate> getExchangeRate();
}
