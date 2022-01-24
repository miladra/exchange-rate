package com.amazing.exchangerate.adapter;

import com.amazing.exchangerate.dto.adoptor.ExchangeRateApiResponse;
import io.reactivex.Single;

public interface ExchangeRateAdapter {

  Single<? extends ExchangeRateApiResponse> getApiResponse();
}
