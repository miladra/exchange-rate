package com.amazing.exchangerate.adapter;

import com.amazing.exchangerate.dto.adoptor.ExchangeRateResponse;
import com.google.gson.Gson;
import io.reactivex.Single;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

@Component
public class ExchangeRateApiAdapter implements ExchangeRateAdapter {

  private final Logger logger = getLogger(FixerAdapter.class);
  @Value("${exchangerateapi.api.url}")
  private String apiUrl;
  @Value("${exchangerateapi.api.token}")
  private String token;
  @Value("${base.currency}")
  private String baseCurrency;

  private final RestTemplate restTemplate;

  public ExchangeRateApiAdapter(RestTemplateBuilder builder) {
    this.restTemplate = builder.build();
  }

  @Override
  public Single<? extends ExchangeRateResponse> getApiResponse() {
    String url = apiUrl + "/{token}/latest/" + baseCurrency;
    return Single.fromCallable(() -> callService(url));
  }

  private ExchangeRateResponse callService(String url) {
    try {
      String data = restTemplate.getForEntity(url, String.class, getRequestParams()).getBody();
      Gson gson = new Gson();
      ExchangeRateResponse exchangeRateResponse =  gson.fromJson(data, ExchangeRateResponse.class);
      if(Objects.nonNull(exchangeRateResponse.getResult()) && exchangeRateResponse.getResult().equals("success")){
        return exchangeRateResponse;
      }else{
        logger.debug("ExchangeRateApi Response was not success");
        return null;
      }
    }catch (Exception ex){
      logger.error(ex.getMessage());
    }
    return null;
  }

  private Map<String, String> getRequestParams() {
    Map<String, String> uriParams = new HashMap<>();
    uriParams.put("token", token);
    return uriParams;
  }
}