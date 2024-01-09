package com.example.RSO.Metrics;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TimerMetric {

    private final MeterRegistry meterRegistry;
    private final Timer timer;
    private Timer.Sample sample;

    @Autowired
    public TimerMetric(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        timer = Timer.builder("custom.metric.responseTime.DropboxAPI")
                .description("Time taken for the operation")
                .register(meterRegistry);
    }

    public void startTimer() {
        this.sample = Timer.start(meterRegistry);

    }

    public void endTimer() {
        this.sample.stop(timer);
    }
}