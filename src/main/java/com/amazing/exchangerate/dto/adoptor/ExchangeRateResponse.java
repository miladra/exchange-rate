package com.amazing.exchangerate.dto.adoptor;


import lombok.Data;

import java.util.HashMap;

@Data
public class ExchangeRateResponse implements ExchangeRateApiResponse {

  private String result;
  private String time_last_update_unix;
  private String base_code;
  private HashMap<String, Double> conversion_rates;


  @Override
  public String getTimestamp() {
    return time_last_update_unix;
  }

  @Override
  public String getBase() {
    return base_code;
  }

  @Override
  public HashMap<String, Double> getRates() {
    return conversion_rates;
  }
}
