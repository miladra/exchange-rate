package com.amazing.exchangerate.dto.adoptor;

import java.util.HashMap;

public interface ExchangeRateApiResponse {

     String getResult();
     String getTimestamp();
     String getBase();
     HashMap<String, Double> getRates();
}
