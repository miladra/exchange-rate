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
    String url = apiUrl + "?access_key={token}&base="+baseCurrency;
    return Single.fromCallable(() -> callService(url));
  }

  private FixerApiResponse callService(String url) {
    try {
      String data = "{\"success\":true,\"timestamp\":1638662402,\"base\":\"EUR\",\"date\":\"2021-12-05\",\"rates\":{\"AED\":4.155546,\"AFN\":108.635063,\"ALL\":120.953369,\"AMD\":554.613743,\"ANG\":2.03782,\"AOA\":639.210027,\"ARS\":114.25769,\"AUD\":1.615745,\"AWG\":2.036986,\"AZN\":1.927777,\"BAM\":1.955561,\"BBD\":2.282986,\"BDT\":96.923973,\"BGN\":1.962219,\"BHD\":0.426441,\"BIF\":2252.790252,\"BMD\":1.131344,\"BND\":1.548687,\"BOB\":7.796228,\"BRL\":6.396321,\"BSD\":1.130745,\"BTC\":2.2970604e-5,\"BTN\":84.907626,\"BWP\":13.31057,\"BYN\":2.874505,\"BYR\":22174.351133,\"BZD\":2.279187,\"CAD\":1.452692,\"CDF\":2264.951989,\"CHF\":1.038263,\"CLF\":0.034161,\"CLP\":942.611795,\"CNY\":7.213909,\"COP\":4451.587928,\"CRC\":710.562301,\"CUC\":1.131344,\"CUP\":29.980628,\"CVE\":110.249841,\"CZK\":25.456891,\"DJF\":201.292323,\"DKK\":7.437576,\"DOP\":64.179176,\"DZD\":157.070404,\"EGP\":17.773556,\"ERN\":16.970518,\"ETB\":54.32803,\"EUR\":1,\"FJD\":2.406714,\"FKP\":0.843343,\"GBP\":0.854974,\"GEL\":3.524183,\"GGP\":0.843343,\"GHS\":6.970842,\"GIP\":0.843343,\"GMD\":59.286808,\"GNF\":10750.221896,\"GTQ\":8.745997,\"GYD\":236.637463,\"HKD\":8.82008,\"HNL\":27.307245,\"HRK\":7.552521,\"HTG\":111.530665,\"HUF\":364.475103,\"IDR\":16433.909416,\"ILS\":3.577346,\"IMP\":0.843343,\"INR\":85.124058,\"IQD\":1649.693175,\"IRR\":47799.303224,\"ISK\":146.60005,\"JEP\":0.843343,\"JMD\":175.603855,\"JOD\":0.802168,\"JPY\":127.621355,\"KES\":127.430379,\"KGS\":95.931904,\"KHR\":4603.0671,\"KMF\":492.757508,\"KPW\":1018.210397,\"KRW\":1339.936122,\"KWD\":0.342463,\"KYD\":0.94227,\"KZT\":495.931812,\"LAK\":12316.806498,\"LBP\":1709.864902,\"LKR\":228.402296,\"LRD\":160.651305,\"LSL\":18.226391,\"LTL\":3.340566,\"LVL\":0.684339,\"LYD\":5.195386,\"MAD\":10.436665,\"MDL\":20.07004,\"MGA\":4497.661593,\"MKD\":61.606529,\"MMK\":2018.533861,\"MNT\":3233.629751,\"MOP\":9.076552,\"MRO\":403.889772,\"MUR\":48.643312,\"MVR\":17.4797,\"MWK\":923.734991,\"MXN\":24.063475,\"MYR\":4.786762,\"MZN\":72.214143,\"NAD\":18.226386,\"NGN\":463.829022,\"NIO\":39.834523,\"NOK\":10.312805,\"NPR\":135.852321,\"NZD\":1.678179,\"OMR\":0.43556,\"PAB\":1.130745,\"PEN\":4.609466,\"PGK\":4.004649,\"PHP\":57.088151,\"PKR\":199.852021,\"PLN\":4.597648,\"PYG\":7714.139343,\"QAR\":4.119269,\"RON\":4.948279,\"RSD\":117.648824,\"RUB\":83.688986,\"RWF\":1171.428934,\"SAR\":4.244073,\"SBD\":9.134833,\"SCR\":16.846684,\"SDG\":494.967356,\"SEK\":10.352537,\"SGD\":1.55281,\"SHP\":1.558318,\"SLL\":12597.520784,\"SOS\":660.705539,\"SRD\":24.361283,\"STD\":23416.545848,\"SVC\":9.89364,\"SYP\":1421.855098,\"SZL\":17.991526,\"THB\":38.31076,\"TJS\":12.765645,\"TMT\":3.971019,\"TND\":3.256579,\"TOP\":2.586937,\"TRY\":15.502478,\"TTD\":7.673045,\"TWD\":31.306794,\"TZS\":2604.011961,\"UAH\":30.862557,\"UGX\":4030.945764,\"USD\":1.131344,\"UYU\":49.926135,\"UZS\":12166.687139,\"VEF\":241915634335.53558,\"VND\":25839.907137,\"VUV\":125.940821,\"WST\":2.900361,\"XAF\":655.866821,\"XAG\":0.050176,\"XAU\":0.000634,\"XCD\":3.057515,\"XDR\":0.807389,\"XOF\":655.866821,\"XPF\":119.781135,\"YER\":283.119359,\"ZAR\":18.256692,\"ZMK\":10183.461688,\"ZMW\":20.166127,\"ZWL\":364.29245}}";
      //String data = restTemplate.getForEntity(url, String.class, getRequestParams()).getBody();
      Gson gson = new Gson();
      FixerApiResponse fixerApiResponse =  gson.fromJson(data, FixerApiResponse.class);
      if(fixerApiResponse.getResult().equals("true")){
        return fixerApiResponse;
      }else{
        logger.debug("Fixer Api response was not success");
        return null;
      }

    }catch (Exception ex){
      System.out.println(ex.getMessage());
    }
    return null;
  }

  private Map<String, String> getRequestParams() {
    Map<String, String> uriParams = new HashMap<>();
    uriParams.put("token", token);
    return uriParams;
  }
}
