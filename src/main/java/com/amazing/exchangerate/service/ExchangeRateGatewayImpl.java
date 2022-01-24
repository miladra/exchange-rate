package com.amazing.exchangerate.service;

import com.amazing.exchangerate.adapter.ExchangeRateAdapter;
import com.amazing.exchangerate.dto.response.ExchangeRate;
import com.amazing.exchangerate.utility.ApiCallTimeout;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ExchangeRateGatewayImpl implements ExchangeRateGateway {

  private final Logger logger = LoggerFactory.getLogger(ExchangeRateGatewayImpl.class);

  private ExchangeRateAdapter connection;
  private Scheduler scheduler;
  private ApiCallTimeout timeout;

  public ExchangeRateGatewayImpl(ExchangeRateAdapter connection,
                                 Scheduler scheduler,
                                 ApiCallTimeout timeout) {
    this.connection = connection;
    this.scheduler = scheduler;
    this.timeout = timeout;
  }

  @Override
  public Single<ExchangeRate> getExchangeRate() {

    Single<ExchangeRate> result = connection.getApiResponse()
        .subscribeOn(scheduler)
        .map(response -> new ExchangeRate(response.getTimestamp(), response.getBase() , response.getRates()))
        .timeout(timeout.getTimeout(), timeout.getTimeUnit(), scheduler)
        .doOnError(ex -> logger.error("Encountered an error while trying to retrieve an exchange rate.", ex))
        .onErrorReturnItem(new ExchangeRate("" , "" ,null));
    return result;
  }
}
