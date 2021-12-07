package com.disqo.exchangerate.dto.response;

import lombok.Data;


@Data
public class ExchangeRateDtoResponse {

    private String base;
    private String currency;
    private Double rate;
}
