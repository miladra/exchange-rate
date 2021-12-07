package com.disqo.exchangerate.adapter;

import com.disqo.exchangerate.dto.adoptor.ExchangeRateApiResponse;
import io.reactivex.Single;

public interface ExchangeRateAdapter {

  Single<? extends ExchangeRateApiResponse> getApiResponse();
}
