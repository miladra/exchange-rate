package com.amazing.exchangerate.service;

import com.amazing.exchangerate.dto.request.ExchangeRateDto;
import com.amazing.exchangerate.dto.response.ExchangeRateDtoResponse;
import com.amazing.exchangerate.utility.util;
import com.amazing.exchangerate.utility.*;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.TestScheduler;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityNotFoundException;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest()
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ExchangeRateServiceImplTest {

    @Value("${api.call.timeout}")
    private String apiCallTimeout;

    @Autowired
    ExchangeRateService exchangeRateService;

    TestScheduler testScheduler;

    @BeforeAll
    public void setUp() {
        testScheduler = new TestScheduler();
    }

    @Test
    @Order(1)
    void add() {
        ExchangeRateDto exchangeRateDto = new ExchangeRateDto();
        exchangeRateDto.setBase("USD");
        exchangeRateDto.setCurrency("EUR");
        exchangeRateDto.setRate(Double.valueOf(1.2525));


        TestObserver<String> ressultObserver = exchangeRateService.Add(exchangeRateDto)
                .subscribeOn(testScheduler)
                .test();

        testScheduler.advanceTimeBy(10, TimeUnit.SECONDS);
        ressultObserver.assertComplete()
                .assertValueCount(1)
                .assertValue(t -> t.equals("EUR"));
    }

    @Test
    @Order(2)
    void findCurrency() {

        TestObserver<ExchangeRateDtoResponse> testObserver = exchangeRateService.findCurrency(util.parseToCurrency("EUR"))
                .subscribeOn(testScheduler)
                .test();

        testScheduler.advanceTimeBy( 10, TimeUnit.SECONDS);
        testObserver.assertComplete()
                .assertValueCount(1)
                .assertValue(t -> Double.valueOf(1.2525).equals(t.getRate()));
    }

    @Test
    @Order(3)
    void delete() {

        ExchangeRateDto exchangeRateDto = new ExchangeRateDto();
        exchangeRateDto.setBase("USD");
        exchangeRateDto.setCurrency("EUR");
        exchangeRateDto.setRate(Double.valueOf(1.2525));

        exchangeRateService.Delete(exchangeRateDto)
                .subscribeOn(testScheduler)
                .test();

        testScheduler.advanceTimeBy( 10, TimeUnit.SECONDS);
        assertThrows(EntityNotFoundException.class, () -> exchangeRateService.findCurrency(util.parseToCurrency("EUR")).blockingGet());

    }


    @Test
    @Order(4)
    void update() {

        ExchangeRateDto exchangeRateDto = new ExchangeRateDto();
        exchangeRateDto.setBase("USD");
        exchangeRateDto.setCurrency("EUR");
        exchangeRateDto.setRate(Double.valueOf(1.555));

        exchangeRateService.Add(exchangeRateDto).blockingGet();


        exchangeRateDto.setRate(Double.valueOf(1.666));
        TestObserver<Void> result = exchangeRateService.Update(exchangeRateDto)
                .subscribeOn(testScheduler)
                .test();

        testScheduler.advanceTimeBy( 10, TimeUnit.SECONDS);
        result.assertComplete();

        ExchangeRateDtoResponse exchangeRateDtoResponse = exchangeRateService.findCurrency(util.parseToCurrency("EUR")).blockingGet();

        assertEquals(Double.valueOf(1.666), exchangeRateDtoResponse.getRate());

    }

    @Test
    @Order(5)
    void convert() {

        ExchangeRateDto exchangeRateEUR = new ExchangeRateDto();
        exchangeRateEUR.setBase("USD");
        exchangeRateEUR.setCurrency("EUR");
        exchangeRateEUR.setRate(Double.valueOf(1.33));

        exchangeRateService.Add(exchangeRateEUR).blockingGet();


        ExchangeRateDto exchangeRateDtoGBP = new ExchangeRateDto();
        exchangeRateDtoGBP.setBase("USD");
        exchangeRateDtoGBP.setCurrency("GBP");
        exchangeRateDtoGBP.setRate(Double.valueOf(1.4));

        exchangeRateService.Add(exchangeRateDtoGBP).blockingGet();


        TestObserver<Double> result = exchangeRateService.Convert(util.parseToCurrency("EUR"), util.parseToCurrency("GBP"), Double.valueOf(1))
                .subscribeOn(testScheduler)
                .test();

        testScheduler.advanceTimeBy(10, TimeUnit.SECONDS);
        result.assertComplete()
                .assertValue(t -> Double.valueOf(1.0526315789473684).equals(t));
    }


}