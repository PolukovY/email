package com.levik.mailsender.app;

import com.levik.mailsender.app.service.IncomingStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
public class KafkaMessageInfoContributor implements InfoContributor {


    private final IncomingStatisticService incomingStatisticService;

    @Autowired
    public KafkaMessageInfoContributor(IncomingStatisticService incomingStatisticService) {
        this.incomingStatisticService = incomingStatisticService;
    }

    /**
     * Contributes additional details using the specified {@link Info.Builder Builder}.
     *
     * @param builder the builder to use
     */
    @Override
    public void contribute(Info.Builder builder) {
        ConcurrentHashMap<String, AtomicInteger> statistic = incomingStatisticService.getStatistic();

        Map<String, Integer> emailDetails = statistic.entrySet()
                .stream()
                .map(it -> new StatisticDto(it.getKey(), it.getValue().get()))
                .collect(Collectors.toMap(StatisticDto::getName, StatisticDto::getCount));

        builder.withDetail("emailDetails", emailDetails);
    }

    static class StatisticDto {
        private final String name;
        private final int count;

        public StatisticDto(String name, int count) {
            this.name = name;
            this.count = count;
        }

        public String getName() {
            return name;
        }

        public int getCount() {
            return count;
        }
    }
}
