package com.amazing.exchangerate.config;

import com.amazing.exchangerate.adapter.ExchangeRateApiAdapter;
import com.amazing.exchangerate.adapter.FixerAdapter;
import com.amazing.exchangerate.service.ExchangeRateGatewayImpl;
import com.amazing.exchangerate.utility.ApiCallTimeout;
import com.amazing.exchangerate.service.ExchangeRateGateway;
import com.amazing.exchangerate.service.LiveCurrencyExchangeRateService;
import com.amazing.exchangerate.service.LiveCurrencyExchangeRateServiceImpl;
import io.reactivex.schedulers.Schedulers;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static java.util.Arrays.asList;


@Configuration
@AllArgsConstructor
class BeanConfiguration {


  private FixerAdapter fixerAdapter;
  private ExchangeRateApiAdapter exchangeRateApiAdapter;


  @Bean(name="LiveCurrencyExchangeRateService")
  LiveCurrencyExchangeRateService getLiveCurrencyExchangeRateService() {
    ApiCallTimeout timeout = ApiCallTimeout.SECONDS(100000);
    return new LiveCurrencyExchangeRateServiceImpl(getExchangeRateGateways(timeout));
  }

  @Bean(name="FixerLiveCurrencyExchangeRateService")
  LiveCurrencyExchangeRateService getFixerCurrencyExchangeRateService() {
    ApiCallTimeout timeout = ApiCallTimeout.SECONDS(100000);
    return new LiveCurrencyExchangeRateServiceImpl(asList(getFixerGateway(timeout)));
  }

  @Bean(name="ExchangeRateApiLiveCurrencyExchangeRateService")
  LiveCurrencyExchangeRateService getExchangeRateApiCurrencyExchangeRateService() {
    ApiCallTimeout timeout = ApiCallTimeout.SECONDS(100000);
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
