package com.disqo.exchangerate.adapter;

import com.disqo.exchangerate.dto.adoptor.ExchangeRateResponse;
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
public class ExchangeRateApiAdapter implements ExchangeRateAdapter {

  private final Logger logger = getLogger(FixerAdapter.class);
  @Value("${exchangerateapi.api.url}")
  private String apiUrl;
  @Value("${exchangerateapi.api.token}")
  private String token;
  @Value("${base.currency}")
  private String baseCurrency;

  private final RestTemplate restTemplate;

  public ExchangeRateApiAdapter(RestTemplateBuilder builder) {
    this.restTemplate = builder.build();
  }

  @Override
  public Single<? extends ExchangeRateResponse> getApiResponse() {
    String url = apiUrl + "/{token}/latest/"+baseCurrency;
    return Single.fromCallable(() -> callService(url));
  }

  private ExchangeRateResponse callService(String url) {
    try {
      String data = "{\"result\":\"success\",\"documentation\":\"https://www.exchangerate-api.com/docs\",\"terms_of_use\":\"https://www.exchangerate-api.com/terms\",\"time_last_update_unix\":1638704643,\"time_last_update_utc\":\"Sun,05Dec202100:00:02+0000\",\"time_next_update_unix\":1638748802,\"time_next_update_utc\":\"Mon,06Dec202100:00:02+0000\",\"base_code\":\"USD\",\"conversion_rates\":{\"USD\":1,\"AED\":3.6725,\"AFN\":96.1405,\"ALL\":106.9545,\"AMD\":489.5844,\"ANG\":1.7900,\"AOA\":572.9666,\"ARS\":101.0107,\"AUD\":1.4189,\"AWG\":1.7900,\"AZN\":1.6982,\"BAM\":1.7302,\"BBD\":2.0000,\"BDT\":85.6947,\"BGN\":1.7306,\"BHD\":0.3760,\"BIF\":1989.1526,\"BMD\":1.0000,\"BND\":1.3680,\"BOB\":6.8714,\"BRL\":5.6396,\"BSD\":1.0000,\"BTN\":75.3046,\"BWP\":11.7861,\"BYN\":2.5417,\"BZD\":2.0000,\"CAD\":1.2824,\"CDF\":2002.4979,\"CHF\":0.9187,\"CLP\":837.6367,\"CNY\":6.3803,\"COP\":3983.1212,\"CRC\":628.9132,\"CUC\":1.0000,\"CUP\":25.0000,\"CVE\":97.5437,\"CZK\":22.4766,\"DJF\":177.7210,\"DKK\":6.5997,\"DOP\":56.4117,\"DZD\":139.1118,\"EGP\":15.7102,\"ERN\":15.0000,\"ETB\":48.3232,\"EUR\":0.8846,\"FJD\":2.1272,\"FKP\":0.7546,\"FOK\":6.5997,\"GBP\":0.7546,\"GEL\":3.1157,\"GGP\":0.7546,\"GHS\":6.5271,\"GIP\":0.7546,\"GMD\":52.6488,\"GNF\":9530.4068,\"GTQ\":7.7232,\"GYD\":209.3956,\"HKD\":7.7937,\"HNL\":24.0982,\"HRK\":6.6652,\"HTG\":99.8085,\"HUF\":321.9972,\"IDR\":14392.4569,\"ILS\":3.1672,\"IMP\":0.7546,\"INR\":75.3079,\"IQD\":1461.0134,\"IRR\":41899.3539,\"ISK\":129.9022,\"JMD\":155.2774,\"JOD\":0.7090,\"JPY\":113.0412,\"KES\":113.0539,\"KGS\":84.7908,\"KHR\":4076.3720,\"KID\":1.4189,\"KMF\":435.2094,\"KRW\":1180.6107,\"KWD\":0.2996,\"KYD\":0.8333,\"KZT\":437.7734,\"LAK\":10835.6766,\"LBP\":1507.5000,\"LKR\":201.4760,\"LRD\":141.8508,\"LSL\":15.9650,\"LYD\":4.6027,\"MAD\":9.0983,\"MDL\":17.7127,\"MGA\":3156.2696,\"MKD\":54.6214,\"MMK\":1784.3755,\"MNT\":2869.0841,\"MOP\":8.0275,\"MRU\":36.3063,\"MUR\":43.4436,\"MVR\":15.4236,\"MWK\":820.1084,\"MXN\":21.3070,\"MYR\":4.2304,\"MZN\":63.9172,\"NAD\":15.9650,\"NGN\":410.0071,\"NIO\":35.1951,\"NOK\":9.1437,\"NPR\":120.4873,\"NZD\":1.4775,\"OMR\":0.3845,\"PAB\":1.0000,\"PEN\":4.0732,\"PGK\":3.5296,\"PHP\":50.5133,\"PKR\":176.1954,\"PLN\":4.0634,\"PYG\":6924.1912,\"QAR\":3.6400,\"RON\":4.3682,\"RSD\":103.7696,\"RUB\":73.6387,\"RWF\":1041.5431,\"SAR\":3.7500,\"SBD\":7.9495,\"SCR\":13.0982,\"SDG\":437.9932,\"SEK\":9.1140,\"SGD\":1.3680,\"SHP\":0.7546,\"SLL\":11127.3939,\"SOS\":579.3071,\"SRD\":21.5783,\"SSP\":412.7807,\"STN\":21.6734,\"SYP\":2479.7949,\"SZL\":15.9650,\"THB\":33.9138,\"TJS\":11.2883,\"TMT\":3.5003,\"TND\":2.7911,\"TOP\":2.2757,\"TRY\":13.7367,\"TTD\":6.7690,\"TVD\":1.4189,\"TWD\":27.7104,\"TZS\":2303.8134,\"UAH\":27.2801,\"UGX\":3567.0332,\"UYU\":43.9758,\"UZS\":10670.8884,\"VES\":4.6291,\"VND\":22699.4523,\"VUV\":113.6637,\"WST\":2.6084,\"XAF\":580.2792,\"XCD\":2.7000,\"XDR\":0.7154,\"XOF\":580.2792,\"XPF\":105.5647,\"YER\":250.1252,\"ZAR\":15.9656,\"ZMW\":17.8567}}";
      //String data = restTemplate.getForEntity(url, String.class, getRequestParams()).getBody();
      Gson gson = new Gson();
      ExchangeRateResponse exchangeRateResponse =  gson.fromJson(data, ExchangeRateResponse.class);
      if(exchangeRateResponse.getResult().equals("success")){
        return exchangeRateResponse;
      }else{
        logger.debug("ExchangeRateApi Response was not success");
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