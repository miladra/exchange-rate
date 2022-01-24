package com.amazing.exchangerate.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
    public static Map<String, Double> integratedRates = new ConcurrentHashMap<>();
}
