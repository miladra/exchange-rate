package com.disqo.exchangerate.service;

import com.disqo.exchangerate.entity.CurrencyExchangeRate;
import com.disqo.exchangerate.config.Cache;
import com.disqo.exchangerate.dto.request.ExchangeRateDto;
import com.disqo.exchangerate.dto.response.ExchangeRateDtoResponse;
import com.disqo.exchangerate.repository.ExchangeRateRepository;
import com.disqo.exchangerate.utility.util;
import io.reactivex.Completable;
import io.reactivex.Single;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Currency;
import java.util.Date;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {

    private final Logger logger = getLogger(ExchangeRateServiceImpl.class);

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    @Value("${base.currency}")
    private String baseCurrency;


    @Override
    public ExchangeRateDtoResponse findCurrency(Currency currency) {
        return findCurrencyInsideCacheOrDatabase(util.parseToCurrency(baseCurrency),currency);
    }

    @Override
    public Single<Double> Convert(Currency from, Currency to, Double amount) {

        if (from.toString().equals(baseCurrency)) {
            ExchangeRateDtoResponse toCurrency = findCurrencyInsideCacheOrDatabase(from , to);
            Double answer = toCurrency.getRate() * amount;
            return Single.just(answer);
        } else {
            ExchangeRateDtoResponse fromCurrency = findCurrencyInsideCacheOrDatabase(util.parseToCurrency(baseCurrency), from);
            ExchangeRateDtoResponse toCurrency = findCurrencyInsideCacheOrDatabase(util.parseToCurrency(baseCurrency),to);

            Double sourceCurrency = fromCurrency.getRate();
            Double sourceCurrencyToEquivalentUSD =  (1 / sourceCurrency) * amount;
            logger.info("Source currency to equivalent USD" + sourceCurrencyToEquivalentUSD);

            Double targetCurrencyExchangeRate = toCurrency.getRate();
            logger.info("USD base target currency exchange rate" + targetCurrencyExchangeRate);

            Double answer = sourceCurrencyToEquivalentUSD * targetCurrencyExchangeRate;
            return Single.just(answer);
        }
    }

    @Override
    public Single<String> Add(ExchangeRateDto exchangeRateDto) {
        CurrencyExchangeRate currencyExchangeRate = new CurrencyExchangeRate();
        currencyExchangeRate.setBase(exchangeRateDto.getBase());
        currencyExchangeRate.setRate(exchangeRateDto.getRate());
        currencyExchangeRate.setName(exchangeRateDto.getCurrency());
        currencyExchangeRate.setSyncDate(new Date());
        currencyExchangeRate = exchangeRateRepository.save(currencyExchangeRate);
        Cache.integratedRates.put(exchangeRateDto.getCurrency(), exchangeRateDto.getRate());
        return Single.just(currencyExchangeRate.getName());
    }

    @Override
    public Completable Update(ExchangeRateDto exchangeRateDto) {
        return Completable.create(completableSubscriber -> {
            CurrencyExchangeRate currencyExchangeRate = exchangeRateRepository.findTopByBaseAndNameOrderBySyncDateDesc(exchangeRateDto.getBase(), exchangeRateDto.getCurrency());
            if (currencyExchangeRate == null)
                completableSubscriber.onError(new EntityNotFoundException());
            else {
                currencyExchangeRate.setRate(exchangeRateDto.getRate());
                currencyExchangeRate.setSyncDate(new Date());
                exchangeRateRepository.save(currencyExchangeRate);
                Cache.integratedRates.put(exchangeRateDto.getCurrency(), exchangeRateDto.getRate());
                completableSubscriber.onComplete();
            }
        });
    }

    @Override
    public Completable Delete(ExchangeRateDto exchangeRateDto) {
        return Completable.create(completableSubscriber -> {
            CurrencyExchangeRate currencyExchangeRate = exchangeRateRepository.findTopByBaseAndNameOrderBySyncDateDesc(exchangeRateDto.getBase(), exchangeRateDto.getCurrency());
            if (currencyExchangeRate == null)
                completableSubscriber.onError(new EntityNotFoundException());
            else {
                exchangeRateRepository.delete(currencyExchangeRate);
                Cache.integratedRates.remove(exchangeRateDto.getCurrency());
                completableSubscriber.onComplete();
            }
        });
    }

    private ExchangeRateDtoResponse findCurrencyInsideCacheOrDatabase(Currency base , Currency currency) {
        ExchangeRateDtoResponse exchangeRateDtoResponse = new ExchangeRateDtoResponse();
        exchangeRateDtoResponse.setBase(base.toString());
        if (Cache.integratedRates.containsKey(currency.toString())) {
            exchangeRateDtoResponse.setRate(Cache.integratedRates.get(currency.toString()));
            exchangeRateDtoResponse.setCurrency(currency.toString());
        } else {
            CurrencyExchangeRate foundCurrency = exchangeRateRepository.findTopByBaseAndNameOrderBySyncDateDesc(base.toString() , currency.toString());
            if (foundCurrency == null) {
                logger.info("Currency not found");
                return null;
            }
            exchangeRateDtoResponse.setCurrency(currency.toString());
            exchangeRateDtoResponse.setRate(foundCurrency.getRate());
            Cache.integratedRates.put(foundCurrency.getName(), foundCurrency.getRate());
        }
        return exchangeRateDtoResponse;
    }
}
