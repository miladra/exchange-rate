package com.amazing.exchangerate.dto.adoptor;


import lombok.Data;

import java.util.HashMap;

@Data
public class FixerApiResponse implements ExchangeRateApiResponse {


  private String success;
  private String error;
  private String timestamp;
  private String base;
  private HashMap<String, Double> rates;

  @Override
  public String getResult() {
    return success;
  }
}
