package com.amazing.exchangerate.dto.request;

import lombok.Data;

@Data
public class ExchangeRateDto {


    private String base;
    private String currency;
    private Double rate;
}
