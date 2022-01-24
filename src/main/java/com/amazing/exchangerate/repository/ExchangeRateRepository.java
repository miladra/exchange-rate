package com.amazing.exchangerate.repository;

import com.amazing.exchangerate.entity.CurrencyExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeRateRepository extends JpaRepository<CurrencyExchangeRate, Long> {


    CurrencyExchangeRate findTopByBaseAndNameOrderBySyncDateDesc(String base, String currency);
    CurrencyExchangeRate findTopByBaseAndNameAndRateOrderBySyncDateDesc(String base, String currency, Double rate);
}
