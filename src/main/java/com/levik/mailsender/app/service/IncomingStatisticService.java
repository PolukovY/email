package com.levik.mailsender.app.service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class IncomingStatisticService {

    public static final String INCOMING = "incoming";
    public static final String COMPLETED = "completed";
    public static final String FAILED = "processed";
    public static final String ACTIVE = "active";

    private final ConcurrentHashMap<String, AtomicInteger> statistic;

    public IncomingStatisticService(ConcurrentHashMap<String, AtomicInteger> statistic) {
        this.statistic = statistic;
    }

    public void incStatisticByKey(String key) {
        AtomicInteger counter = statistic.putIfAbsent(key, new AtomicInteger(1));
        if (counter != null) {
            counter.incrementAndGet();
        }
    }

    public void decStatisticByKey(String key) {
        AtomicInteger counter = statistic.putIfAbsent(key, new AtomicInteger(1));
        if (counter != null) {
            counter.decrementAndGet();
        }
    }

    public ConcurrentHashMap<String, AtomicInteger> getStatistic() {
        return statistic;
    }
}
