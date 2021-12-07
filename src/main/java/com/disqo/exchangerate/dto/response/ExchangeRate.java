package com.disqo.exchangerate.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
@AllArgsConstructor
public class ExchangeRate implements Serializable {

  private static final long serialVersionUID = -3256019891156471009L;

  private String timestamp;
  private String baseCurrency;
  private Map<String,Double> CurrencyRates;

}
