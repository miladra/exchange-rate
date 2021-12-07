package com.disqo.exchangerate.service;

import com.disqo.exchangerate.entity.CurrencyExchangeRate;
import com.disqo.exchangerate.config.Cache;
import com.disqo.exchangerate.dto.response.ExchangeRate;
import com.disqo.exchangerate.repository.ExchangeRateRepository;
import io.reactivex.Single;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static java.util.List.copyOf;
import static org.slf4j.LoggerFactory.getLogger;

public class LiveCurrencyExchangeRateServiceImpl implements LiveCurrencyExchangeRateService {

    private final Logger logger = getLogger(LiveCurrencyExchangeRateServiceImpl.class);

    @Autowired
    ExchangeRateRepository exchangeRateRepository;

    private final List<ExchangeRateGateway> exchangeRateGateways;
    static String base = "";
    static Integer timestamp = 0;


    public LiveCurrencyExchangeRateServiceImpl(List<ExchangeRateGateway> exchangeRateGateways) {
        this.exchangeRateGateways = copyOf(exchangeRateGateways);
    }

    @Override
    public Single<ExchangeRate> getLiveExchangeRateUpdateCacheBaseTime() {

        exchangeRateGateways.forEach(gateway -> {
            ExchangeRate exchangeRate = gateway.getExchangeRate().blockingGet();
            Map<String, Double> rates = exchangeRate.getCurrencyRates();
            logger.info("Old timestamp is: " + timestamp + " New timestamp  is: " + exchangeRate.getTimestamp());
            if (Integer.valueOf(exchangeRate.getTimestamp()) > timestamp) {
                timestamp = Integer.valueOf(exchangeRate.getTimestamp());
                base = exchangeRate.getBaseCurrency();
                logger.info("Current local rate size is: " + Cache.integratedRates.size() + " New rate size  from Api is: " + rates.size());
                for (Map.Entry<String, Double> item : rates.entrySet()) {
                    Cache.integratedRates.put(item.getKey(), item.getValue());
                    SaveToDateBase(base , item.getKey() , item.getValue());
                }
            }
        });

        return Single.just(new ExchangeRate(timestamp.toString(), base, Cache.integratedRates));
    }


   @Override
    public Single<ExchangeRate> getLiveExchangeRateUpdateCacheBaseHigherًRate() {

        exchangeRateGateways.forEach(gateway-> {
            ExchangeRate exchangeRate = gateway.getExchangeRate().blockingGet();
            Map<String, Double> rates = exchangeRate.getCurrencyRates();
            base = exchangeRate.getBaseCurrency();
            timestamp = Integer.valueOf(exchangeRate.getTimestamp());
            logger.info("Current local rate size is: " + Cache.integratedRates.size() + " New rate size  from Api is: " + rates.size());
            for (Map.Entry<String, Double> item : rates.entrySet()) {
                Double firstItem  = item.getValue();
                if (Cache.integratedRates.containsKey(item.getKey())) {
                    Double secondItem = Cache.integratedRates.get(item.getKey());
                    if (firstItem.compareTo(secondItem) > 0 ) {
                        Cache.integratedRates.put(item.getKey(), firstItem);
                        SaveToDateBase(base , item.getKey() , firstItem);
                    }
                } else {
                    Cache.integratedRates.put(item.getKey(), firstItem);
                    SaveToDateBase(base , item.getKey() , firstItem);
                }

            }
        });
        return Single.just(new ExchangeRate(timestamp.toString(), base , Cache.integratedRates));
    }

    private void SaveToDateBase(String baseCurrency , String currencyName , Double rate){
        CurrencyExchangeRate currencyExchangeRate = new CurrencyExchangeRate();
        currencyExchangeRate.setBase(baseCurrency);
        currencyExchangeRate.setName(currencyName);
        currencyExchangeRate.setRate(rate);
        currencyExchangeRate.setSyncDate(new Date());
        exchangeRateRepository.save(currencyExchangeRate);
    }

    /*
        Observable<ExchangeRate> exchangeRateObservable = Observable
                .fromIterable(exchangeRateGateways)
                .flatMap(adapter -> adapter.getExchangeRate(currencyConversion).toObservable())
                .doOnNext(exchangeRate -> {
                    logger.info("Currency rates size: " + exchangeRate.getCurrencyRates().size());
                    Map<String, BigDecimal> rates = exchangeRate.getCurrencyRates();
                    for (Map.Entry<String, BigDecimal> item : rates.entrySet()) {
                        BigDecimal firstItem  = item.getValue();
                        if (integratedRates.containsKey(item.getKey())) {
                            BigDecimal secondItem = integratedRates.get(item.getKey());
                            if (firstItem.compareTo(secondItem) > 0 ) {
                                integratedRates.put(item.getKey(), firstItem);
                            }
                        } else {
                            integratedRates.put(item.getKey(), firstItem);
                        }
                    }
                })
                .map(response -> new ExchangeRate(response.getTimestamp(), response.getBaseCurrency() , integratedRates));
*/


}
