package com.disqo.exchangerate.controller;

import com.disqo.exchangerate.dto.request.ExchangeRateDto;
import com.disqo.exchangerate.dto.response.BaseWebResponse;
import com.disqo.exchangerate.service.ExchangeRateService;
import com.disqo.exchangerate.service.LiveCurrencyExchangeRateService;
import com.disqo.exchangerate.utility.util;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;


@RestController
@RequestMapping(value = "/v1/exchangerate")
public class ExchangeRateController {

    private final Logger logger = LoggerFactory.getLogger(ExchangeRateController.class);

    @Autowired
    private ExchangeRateService exchangeRateService;

    @Autowired
    @Qualifier("LiveCurrencyExchangeRateService")
    LiveCurrencyExchangeRateService liveCurrencyExchangeRateService;

    @GetMapping("/currency/{currency}")
    public Single<ResponseEntity<BaseWebResponse>> get(@PathVariable("currency") String currency) {
        return exchangeRateService.findCurrency(util.parseToCurrency(currency))
                .subscribeOn(Schedulers.io())
                .map(er -> ResponseEntity.ok(BaseWebResponse.successWithData(er)));
    }

    @GetMapping("/live")
    public Single<ResponseEntity<BaseWebResponse>> getLive() {
        return liveCurrencyExchangeRateService.getLiveExchangeRateUpdateCacheBaseTime()
                .subscribeOn(Schedulers.io())
                .map(er -> Objects.nonNull(er.getCurrencyRates()) && er.getCurrencyRates().size() > 0
                          ? ResponseEntity.ok(BaseWebResponse.successWithData(er))
                          : ResponseEntity.ok(BaseWebResponse.error("No data was received from public APIs.")) );
    }

    @GetMapping(value = "/from/{from}/to/{to}/amount/{amount}")
    public Single<ResponseEntity<BaseWebResponse>> convert(@PathVariable("from") String from, @PathVariable("to") String to, @PathVariable("amount") String amount) {
        return exchangeRateService.Convert(util.parseToCurrency(from), util.parseToCurrency(to),Double.valueOf(amount))
                .subscribeOn(Schedulers.io())
                .map(er -> ResponseEntity.ok(BaseWebResponse.successWithData(er)));

    }

    @PostMapping(value = "")
    public Single<ResponseEntity<BaseWebResponse>> add(@RequestBody ExchangeRateDto exchangeRateDto) {
        return exchangeRateService.Add(exchangeRateDto)
                .subscribeOn(Schedulers.io())
                .map(er -> ResponseEntity.ok(BaseWebResponse.successWithData(er)));
    }

    @PutMapping(value = "")
    public Single<ResponseEntity<BaseWebResponse>> update(@RequestBody ExchangeRateDto exchangeRateDto) {
       return  exchangeRateService.Update(exchangeRateDto)
                    .subscribeOn(Schedulers.io())
                    .toSingle(() -> ResponseEntity.ok(BaseWebResponse.successWithData("Has been updated successfully")));
    }

    @DeleteMapping(value = "")
    public Single<ResponseEntity<BaseWebResponse>> delete(@RequestBody ExchangeRateDto exchangeRateDto) {
       return  exchangeRateService.Delete(exchangeRateDto)
                    .subscribeOn(Schedulers.io())
                    .toSingle(() -> ResponseEntity.ok(BaseWebResponse.successWithData("Has been deleted successfully")));
    }
}
