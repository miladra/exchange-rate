package com.disqo.exchangerate.config;

import com.disqo.exchangerate.adapter.*;
import com.disqo.exchangerate.service.ExchangeRateGateway;
import com.disqo.exchangerate.service.ExchangeRateGatewayImpl;
import com.disqo.exchangerate.service.LiveCurrencyExchangeRateService;
import com.disqo.exchangerate.service.LiveCurrencyExchangeRateServiceImpl;
import com.disqo.exchangerate.utility.ApiCallTimeout;
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
