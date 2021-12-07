package com.disqo.exchangerate.controller;

import com.disqo.exchangerate.dto.request.ExchangeRateDto;
import com.disqo.exchangerate.dto.response.BaseWebResponse;
import com.disqo.exchangerate.service.ExchangeRateService;
import com.disqo.exchangerate.service.LiveCurrencyExchangeRateService;
import com.disqo.exchangerate.utility.util;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/v1/exchangerates")
@Api(value = "Exchange Rate")
@ApiResponses(value = {@ApiResponse(code = 200, message = "Success|OK"), @ApiResponse(code = 401, message = "not authorized!"), @ApiResponse(code = 403, message = "forbidden!!!"), @ApiResponse(code = 404, message = "not found!!!"), @ApiResponse(code = 500, message = "Resource not found")})
public class ExchangeRateController {

    private final Logger logger = LoggerFactory.getLogger(ExchangeRateController.class);

    @Autowired
    private ExchangeRateService exchangeRateService;

    @Autowired
    @Qualifier("LiveCurrencyExchangeRateService")
    LiveCurrencyExchangeRateService liveCurrencyExchangeRateService;


    @GetMapping("/all")
    public Single<ResponseEntity<BaseWebResponse>> getAll() {
        return liveCurrencyExchangeRateService.getLiveExchangeRateUpdateCacheBaseTime()
                .subscribeOn(Schedulers.io())
                .map(er -> ResponseEntity.ok(BaseWebResponse.successWithData(er)));
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
                    .toSingle(() -> ResponseEntity.ok(BaseWebResponse.successNoData()));
    }

    @DeleteMapping(value = "")
    public Single<ResponseEntity<BaseWebResponse>> delete(@RequestBody ExchangeRateDto exchangeRateDto) {
       return  exchangeRateService.Delete(exchangeRateDto)
                    .subscribeOn(Schedulers.io())
                    .toSingle(() -> ResponseEntity.ok(BaseWebResponse.successNoData()));
    }
}
