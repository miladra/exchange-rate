package com.amazing.exchangerate.scheduler;

import com.amazing.exchangerate.service.LiveCurrencyExchangeRateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@ConditionalOnProperty(value = "app.scheduling.enable", havingValue = "true", matchIfMissing = true)
@Component
public class Scheduler {

    private static final Logger log = LoggerFactory.getLogger(Scheduler.class);

    @Autowired
    @Qualifier("FixerLiveCurrencyExchangeRateService")
    LiveCurrencyExchangeRateService fixerLiveCurrencyExchangeRateService;

    @Autowired
    @Qualifier("ExchangeRateApiLiveCurrencyExchangeRateService")
    LiveCurrencyExchangeRateService exchangeRateApiLiveCurrencyExchangeRateService;

    @Scheduled(cron ="${fixer.api.scheduler}")
    public void fixerScheduledTask() {
        log.info("Fixer Rate Api will be run in scheduler");
        fixerLiveCurrencyExchangeRateService.getLiveExchangeRateUpdateCacheBaseTime();
    }

    @Scheduled(cron ="${exchangerateapi.api.scheduler}")
    public void exchangerateapiScheduledTask() {
        log.info("Exchange Rate Api will be run in scheduler");
        exchangeRateApiLiveCurrencyExchangeRateService.getLiveExchangeRateUpdateCacheBaseTime();
    }


}
