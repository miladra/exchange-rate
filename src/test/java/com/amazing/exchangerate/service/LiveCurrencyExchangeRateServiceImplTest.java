package com.amazing.exchangerate.service;


import com.amazing.exchangerate.adapter.ExchangeRateApiAdapter;
import com.amazing.exchangerate.adapter.FixerAdapter;
import com.amazing.exchangerate.dto.adoptor.ExchangeRateResponse;
import com.amazing.exchangerate.dto.adoptor.FixerApiResponse;
import com.amazing.exchangerate.dto.response.ExchangeRate;
import com.amazing.exchangerate.utility.ApiCallTimeout;
import com.amazing.exchangerate.utility.*;
import com.google.gson.Gson;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.TestScheduler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest()
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LiveCurrencyExchangeRateServiceImplTest {

    @Value("${api.call.timeout}")
    private String apiCallTimeout;

    @MockBean
    ExchangeRateApiAdapter exchangeRateApiAdapter;

    @MockBean
    FixerAdapter fixerAdapter;

    @Autowired
    @Qualifier("LiveCurrencyExchangeRateService")
    LiveCurrencyExchangeRateService liveCurrencyExchangeRateService;

    Single<ExchangeRateResponse> exchangeRateResponse;
    Single<FixerApiResponse> fixerResponse;
    private ApiCallTimeout timeout;
    TestScheduler testScheduler;


    @BeforeAll
    public void setUp() {
        timeout = ApiCallTimeout.SECONDS(Long.valueOf(apiCallTimeout));
        testScheduler = new TestScheduler();
        String exchangeRateApiSampleDate = "{\"result\":\"success\",\"documentation\":\"https://www.exchangerate-api.com/docs\",\"terms_of_use\":\"https://www.exchangerate-api.com/terms\",\"time_last_update_unix\":1638704643,\"time_last_update_utc\":\"Sun,05Dec202100:00:02+0000\",\"time_next_update_unix\":1638748802,\"time_next_update_utc\":\"Mon,06Dec202100:00:02+0000\",\"base_code\":\"USD\",\"conversion_rates\":{\"USD\":1,\"AED\":3.6725,\"AFN\":96.1405,\"ALL\":106.9545,\"AMD\":489.5844,\"ANG\":1.7900,\"AOA\":572.9666,\"ARS\":101.0107,\"AUD\":1.4189,\"AWG\":1.7900,\"AZN\":1.6982,\"BAM\":1.7302,\"BBD\":2.0000,\"BDT\":85.6947,\"BGN\":1.7306,\"BHD\":0.3760,\"BIF\":1989.1526,\"BMD\":1.0000,\"BND\":1.3680,\"BOB\":6.8714,\"BRL\":5.6396,\"BSD\":1.0000,\"BTN\":75.3046,\"BWP\":11.7861,\"BYN\":2.5417,\"BZD\":2.0000,\"CAD\":1.2824,\"CDF\":2002.4979,\"CHF\":0.9187,\"CLP\":837.6367,\"CNY\":6.3803,\"COP\":3983.1212,\"CRC\":628.9132,\"CUC\":1.0000,\"CUP\":25.0000,\"CVE\":97.5437,\"CZK\":22.4766,\"DJF\":177.7210,\"DKK\":6.5997,\"DOP\":56.4117,\"DZD\":139.1118,\"EGP\":15.7102,\"ERN\":15.0000,\"ETB\":48.3232,\"EUR\":0.8846,\"FJD\":2.1272,\"FKP\":0.7546,\"FOK\":6.5997,\"GBP\":0.7546,\"GEL\":3.1157,\"GGP\":0.7546,\"GHS\":6.5271,\"GIP\":0.7546,\"GMD\":52.6488,\"GNF\":9530.4068,\"GTQ\":7.7232,\"GYD\":209.3956,\"HKD\":7.7937,\"HNL\":24.0982,\"HRK\":6.6652,\"HTG\":99.8085,\"HUF\":321.9972,\"IDR\":14392.4569,\"ILS\":3.1672,\"IMP\":0.7546,\"INR\":75.3079,\"IQD\":1461.0134,\"IRR\":41899.3539,\"ISK\":129.9022,\"JMD\":155.2774,\"JOD\":0.7090,\"JPY\":113.0412,\"KES\":113.0539,\"KGS\":84.7908,\"KHR\":4076.3720,\"KID\":1.4189,\"KMF\":435.2094,\"KRW\":1180.6107,\"KWD\":0.2996,\"KYD\":0.8333,\"KZT\":437.7734,\"LAK\":10835.6766,\"LBP\":1507.5000,\"LKR\":201.4760,\"LRD\":141.8508,\"LSL\":15.9650,\"LYD\":4.6027,\"MAD\":9.0983,\"MDL\":17.7127,\"MGA\":3156.2696,\"MKD\":54.6214,\"MMK\":1784.3755,\"MNT\":2869.0841,\"MOP\":8.0275,\"MRU\":36.3063,\"MUR\":43.4436,\"MVR\":15.4236,\"MWK\":820.1084,\"MXN\":21.3070,\"MYR\":4.2304,\"MZN\":63.9172,\"NAD\":15.9650,\"NGN\":410.0071,\"NIO\":35.1951,\"NOK\":9.1437,\"NPR\":120.4873,\"NZD\":1.4775,\"OMR\":0.3845,\"PAB\":1.0000,\"PEN\":4.0732,\"PGK\":3.5296,\"PHP\":50.5133,\"PKR\":176.1954,\"PLN\":4.0634,\"PYG\":6924.1912,\"QAR\":3.6400,\"RON\":4.3682,\"RSD\":103.7696,\"RUB\":73.6387,\"RWF\":1041.5431,\"SAR\":3.7500,\"SBD\":7.9495,\"SCR\":13.0982,\"SDG\":437.9932,\"SEK\":9.1140,\"SGD\":1.3680,\"SHP\":0.7546,\"SLL\":11127.3939,\"SOS\":579.3071,\"SRD\":21.5783,\"SSP\":412.7807,\"STN\":21.6734,\"SYP\":2479.7949,\"SZL\":15.9650,\"THB\":33.9138,\"TJS\":11.2883,\"TMT\":3.5003,\"TND\":2.7911,\"TOP\":2.2757,\"TRY\":13.7367,\"TTD\":6.7690,\"TVD\":1.4189,\"TWD\":27.7104,\"TZS\":2303.8134,\"UAH\":27.2801,\"UGX\":3567.0332,\"UYU\":43.9758,\"UZS\":10670.8884,\"VES\":4.6291,\"VND\":22699.4523,\"VUV\":113.6637,\"WST\":2.6084,\"XAF\":580.2792,\"XCD\":2.7000,\"XDR\":0.7154,\"XOF\":580.2792,\"XPF\":105.5647,\"YER\":250.1252,\"ZAR\":15.9656,\"ZMW\":17.8567}}";
        String fixerSampledata = "{\"success\":true,\"timestamp\":1638662402,\"base\":\"EUR\",\"date\":\"2021-12-05\",\"rates\":{\"AED\":4.155546,\"AFN\":108.635063,\"ALL\":120.953369,\"AMD\":554.613743,\"ANG\":2.03782,\"AOA\":639.210027,\"ARS\":114.25769,\"AUD\":1.615745,\"AWG\":2.036986,\"AZN\":1.927777,\"BAM\":1.955561,\"BBD\":2.282986,\"BDT\":96.923973,\"BGN\":1.962219,\"BHD\":0.426441,\"BIF\":2252.790252,\"BMD\":1.131344,\"BND\":1.548687,\"BOB\":7.796228,\"BRL\":6.396321,\"BSD\":1.130745,\"BTC\":2.2970604e-5,\"BTN\":84.907626,\"BWP\":13.31057,\"BYN\":2.874505,\"BYR\":22174.351133,\"BZD\":2.279187,\"CAD\":1.452692,\"CDF\":2264.951989,\"CHF\":1.038263,\"CLF\":0.034161,\"CLP\":942.611795,\"CNY\":7.213909,\"COP\":4451.587928,\"CRC\":710.562301,\"CUC\":1.131344,\"CUP\":29.980628,\"CVE\":110.249841,\"CZK\":25.456891,\"DJF\":201.292323,\"DKK\":7.437576,\"DOP\":64.179176,\"DZD\":157.070404,\"EGP\":17.773556,\"ERN\":16.970518,\"ETB\":54.32803,\"EUR\":1,\"FJD\":2.406714,\"FKP\":0.843343,\"GBP\":0.854974,\"GEL\":3.524183,\"GGP\":0.843343,\"GHS\":6.970842,\"GIP\":0.843343,\"GMD\":59.286808,\"GNF\":10750.221896,\"GTQ\":8.745997,\"GYD\":236.637463,\"HKD\":8.82008,\"HNL\":27.307245,\"HRK\":7.552521,\"HTG\":111.530665,\"HUF\":364.475103,\"IDR\":16433.909416,\"ILS\":3.577346,\"IMP\":0.843343,\"INR\":85.124058,\"IQD\":1649.693175,\"IRR\":47799.303224,\"ISK\":146.60005,\"JEP\":0.843343,\"JMD\":175.603855,\"JOD\":0.802168,\"JPY\":127.621355,\"KES\":127.430379,\"KGS\":95.931904,\"KHR\":4603.0671,\"KMF\":492.757508,\"KPW\":1018.210397,\"KRW\":1339.936122,\"KWD\":0.342463,\"KYD\":0.94227,\"KZT\":495.931812,\"LAK\":12316.806498,\"LBP\":1709.864902,\"LKR\":228.402296,\"LRD\":160.651305,\"LSL\":18.226391,\"LTL\":3.340566,\"LVL\":0.684339,\"LYD\":5.195386,\"MAD\":10.436665,\"MDL\":20.07004,\"MGA\":4497.661593,\"MKD\":61.606529,\"MMK\":2018.533861,\"MNT\":3233.629751,\"MOP\":9.076552,\"MRO\":403.889772,\"MUR\":48.643312,\"MVR\":17.4797,\"MWK\":923.734991,\"MXN\":24.063475,\"MYR\":4.786762,\"MZN\":72.214143,\"NAD\":18.226386,\"NGN\":463.829022,\"NIO\":39.834523,\"NOK\":10.312805,\"NPR\":135.852321,\"NZD\":1.678179,\"OMR\":0.43556,\"PAB\":1.130745,\"PEN\":4.609466,\"PGK\":4.004649,\"PHP\":57.088151,\"PKR\":199.852021,\"PLN\":4.597648,\"PYG\":7714.139343,\"QAR\":4.119269,\"RON\":4.948279,\"RSD\":117.648824,\"RUB\":83.688986,\"RWF\":1171.428934,\"SAR\":4.244073,\"SBD\":9.134833,\"SCR\":16.846684,\"SDG\":494.967356,\"SEK\":10.352537,\"SGD\":1.55281,\"SHP\":1.558318,\"SLL\":12597.520784,\"SOS\":660.705539,\"SRD\":24.361283,\"STD\":23416.545848,\"SVC\":9.89364,\"SYP\":1421.855098,\"SZL\":17.991526,\"THB\":38.31076,\"TJS\":12.765645,\"TMT\":3.971019,\"TND\":3.256579,\"TOP\":2.586937,\"TRY\":15.502478,\"TTD\":7.673045,\"TWD\":31.306794,\"TZS\":2604.011961,\"UAH\":30.862557,\"UGX\":4030.945764,\"USD\":1.131344,\"UYU\":49.926135,\"UZS\":12166.687139,\"VEF\":241915634335.53558,\"VND\":25839.907137,\"VUV\":125.940821,\"WST\":2.900361,\"XAF\":655.866821,\"XAG\":0.050176,\"XAU\":0.000634,\"XCD\":3.057515,\"XDR\":0.807389,\"XOF\":655.866821,\"XPF\":119.781135,\"YER\":283.119359,\"ZAR\":18.256692,\"ZMK\":10183.461688,\"ZMW\":20.166127,\"ZWL\":364.29245}}";
        Gson gson = new Gson();
        exchangeRateResponse = Single.just(gson.fromJson(exchangeRateApiSampleDate, ExchangeRateResponse.class));
        fixerResponse = Single.just(gson.fromJson(fixerSampledata, FixerApiResponse.class));
    }

    @Test
    void getLiveExchangeRateUpdateCacheBaseTime() {

        Mockito.doReturn(exchangeRateResponse)
                .when(exchangeRateApiAdapter).getApiResponse();

        Mockito.doReturn(fixerResponse)
                .when(fixerAdapter).getApiResponse();

        TestObserver<ExchangeRate> exchangeRateObserver = liveCurrencyExchangeRateService
                .getLiveExchangeRateUpdateCacheBaseTime()
                .subscribeOn(testScheduler)
                .test();

        testScheduler.advanceTimeBy(timeout.getTimeout() + 100, TimeUnit.SECONDS);
        exchangeRateObserver
                .assertComplete()
                .assertNoErrors()
                .assertValueCount(1)
                .assertValue(t -> t.getCurrencyRates().size() == 160);
    }


    @Test
    void getLiveExchangeRateUpdateCacheBaseHigherًRate() {

        Mockito.doReturn(exchangeRateResponse)
                .when(exchangeRateApiAdapter).getApiResponse();

        Mockito.doReturn(fixerResponse)
                .when(fixerAdapter).getApiResponse();

        TestObserver<ExchangeRate> exchangeRateObserver = liveCurrencyExchangeRateService
                .getLiveExchangeRateUpdateCacheBaseTime()
                .subscribeOn(testScheduler)
                .test();

        testScheduler.advanceTimeBy(timeout.getTimeout() + 100, TimeUnit.SECONDS);
        exchangeRateObserver
                .assertComplete()
                .assertNoErrors()
                .assertValueCount(1)
                .assertValue(t -> t.getCurrencyRates().size() == 160);
    }
}