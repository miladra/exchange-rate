package com.disqo.exchangerate.adapter;


import com.disqo.exchangerate.dto.adoptor.ExchangeRateApiResponse;
import com.disqo.exchangerate.dto.adoptor.FixerApiResponse;
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
public class FixerAdapter implements ExchangeRateAdapter {

    private final Logger logger = getLogger(FixerAdapter.class);
    @Value("${fixer.api.url}")
    private String apiUrl;
    @Value("${fixer.api.token}")
    private String token;
    @Value("${base.currency}")
    private String baseCurrency;

    private final RestTemplate restTemplate;

    public FixerAdapter(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    @Override
    public Single<? extends ExchangeRateApiResponse> getApiResponse() {
        String url = apiUrl + "?access_key={token}&base=" + baseCurrency;
        return Single.fromCallable(() -> callService(url));
    }

    private FixerApiResponse callService(String url) {
        try {
            String data = restTemplate.getForEntity(url, String.class, getRequestParams()).getBody();
            Gson gson = new Gson();
            FixerApiResponse fixerApiResponse = gson.fromJson(data, FixerApiResponse.class);
            if (Objects.nonNull(fixerApiResponse.getResult()) && fixerApiResponse.getResult().equals("true")) {
                return fixerApiResponse;
            } else {
                logger.debug("Fixer Api response was not success");
                return null;
            }

        } catch (Exception ex) {
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
