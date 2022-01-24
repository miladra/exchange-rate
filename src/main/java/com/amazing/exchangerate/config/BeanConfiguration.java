package com.amazing.exchangerate.config;

import com.amazing.exchangerate.adapter.ExchangeRateApiAdapter;
import com.amazing.exchangerate.adapter.FixerAdapter;
import com.amazing.exchangerate.service.ExchangeRateGateway;
import com.amazing.exchangerate.service.ExchangeRateGatewayImpl;
import com.amazing.exchangerate.service.LiveCurrencyExchangeRateService;
import com.amazing.exchangerate.service.LiveCurrencyExchangeRateServiceImpl;
import com.amazing.exchangerate.utility.ApiCallTimeout;
import com.amazing.exchangerate.adapter.*;
import io.reactivex.schedulers.Schedulers;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static java.util.Arrays.asList;


@Configuration
@RequiredArgsConstructor
class BeanConfiguration {

  @Value("${api.call.timeout}")
  private String apiCallTimeout;

  private final FixerAdapter fixerAdapter;
  private final ExchangeRateApiAdapter exchangeRateApiAdapter;


  @Bean(name="LiveCurrencyExchangeRateService")
  LiveCurrencyExchangeRateService getLiveCurrencyExchangeRateService() {
    ApiCallTimeout timeout = ApiCallTimeout.SECONDS(Long.valueOf(apiCallTimeout));
    return new LiveCurrencyExchangeRateServiceImpl(getExchangeRateGateways(timeout));
  }

  @Bean(name="FixerLiveCurrencyExchangeRateService")
  LiveCurrencyExchangeRateService getFixerCurrencyExchangeRateService() {
    ApiCallTimeout timeout = ApiCallTimeout.SECONDS(Long.valueOf(apiCallTimeout));
    return new LiveCurrencyExchangeRateServiceImpl(asList(getFixerGateway(timeout)));
  }

  @Bean(name="ExchangeRateApiLiveCurrencyExchangeRateService")
  LiveCurrencyExchangeRateService getExchangeRateApiCurrencyExchangeRateService() {
    ApiCallTimeout timeout = ApiCallTimeout.SECONDS(Long.valueOf(apiCallTimeout));
    return new LiveCurrencyExchangeRateServiceImpl(asList(getExchangeRateApiGateway(timeout)));
  }

  @Bean
  List<ExchangeRateGateway> getExchangeRateGateways(ApiCallTimeout timeout) {
    return asList(getExchangeRateApiGateway(timeout) , getFixerGateway(timeout));
  }

  @Bean
  ExchangeRateGateway getFixerGateway(ApiCallTimeout timeout) {
    return new ExchangeRateGatewayImpl(fixerAdapter, Schedulers.io(), timeout);
  }

  @Bean
  ExchangeRateGateway getExchangeRateApiGateway(ApiCallTimeout timeout) {
    return new ExchangeRateGatewayImpl(exchangeRateApiAdapter, Schedulers.io(), timeout);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
