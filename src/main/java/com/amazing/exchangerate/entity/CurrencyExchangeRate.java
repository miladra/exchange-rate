package com.amazing.exchangerate.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;


@Entity
@Table(name = "ExchangeRate")
@Data
public class CurrencyExchangeRate extends BaseEntity{

    @Column(name = "base")
    private String base;

    @Column(name = "name")
    private String name;

    @Column(name = "rate")
    private Double rate;

    @Column(name = "sync_date")
    private Date syncDate;

}
