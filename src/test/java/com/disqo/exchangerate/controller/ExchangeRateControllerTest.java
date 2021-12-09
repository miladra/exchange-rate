package com.disqo.exchangerate.controller;

import com.disqo.exchangerate.dto.request.ExchangeRateDto;
import com.disqo.exchangerate.dto.response.BaseWebResponse;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.h2.console.enabled=true")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ExchangeRateControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int serverPort;

    String base64Creds;
    HttpHeaders headers;
    @BeforeAll
    public void setUp() {
        String authStr = "admin:admin";
        base64Creds = Base64.getEncoder().encodeToString(authStr.getBytes());
        headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds);
    }

    @Test
    @Order(1)
    void add() {
        String url ="http://localhost:" + serverPort + "/v1/exchangerate/";

        ExchangeRateDto exchangeRateDto = new ExchangeRateDto();
        exchangeRateDto.setBase("USD");
        exchangeRateDto.setCurrency("EUR");
        exchangeRateDto.setRate(Double.valueOf(1.2));

        HttpEntity<ExchangeRateDto> request = new HttpEntity<>(exchangeRateDto, headers);

        ResponseEntity<BaseWebResponse> result = restTemplate.exchange(url, HttpMethod.POST, request, BaseWebResponse.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertTrue(result.getBody().getResult().equals("success"));
    }

    @Test
    @Order(2)
    void get() {

        String saveUrl = "http://localhost:" + serverPort + "/v1/exchangerate/currency/{currency}";

        Map<String, String > params = new HashMap <> ();
        params.put("currency", "EUR");

        HttpEntity<?> request = new HttpEntity<>(headers);

        ResponseEntity<BaseWebResponse> result = restTemplate.exchange(saveUrl, HttpMethod.GET, request, BaseWebResponse.class , params);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertTrue(result.getBody().getResult().equals("success"));
    }

    @Test
    @Order(3)
    void update() {

        String url = "http://localhost:" + serverPort + "/v1/exchangerate";

        ExchangeRateDto exchangeRateDto = new ExchangeRateDto();
        exchangeRateDto.setBase("USD");
        exchangeRateDto.setCurrency("EUR");
        exchangeRateDto.setRate(Double.valueOf(1.5));

        HttpEntity<ExchangeRateDto> request = new HttpEntity<>(exchangeRateDto, headers);

        ResponseEntity<BaseWebResponse> result = restTemplate.exchange(url, HttpMethod.PUT, request, BaseWebResponse.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertTrue(result.getBody().getResult().equals("success"));
    }

    @Test
    @Order(4)
    void delete() {

        String url = "http://localhost:" + serverPort + "/v1/exchangerate";

        ExchangeRateDto exchangeRateDto = new ExchangeRateDto();
        exchangeRateDto.setBase("USD");
        exchangeRateDto.setCurrency("EUR");
        exchangeRateDto.setRate(Double.valueOf(1.5));

        HttpEntity<ExchangeRateDto> request = new HttpEntity<>(exchangeRateDto, headers);

        ResponseEntity<BaseWebResponse> result = restTemplate.exchange(url, HttpMethod.DELETE, request, BaseWebResponse.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertTrue(result.getBody().getResult().equals("success"));
    }
}