package com.example.RSO.Metrics;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UploadCounter {

    private final MeterRegistry meterRegistry;

    @Autowired
    public UploadCounter(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    public void recordUploadMetric() {
        meterRegistry.counter("custom.metric.dropboxAPIcall").increment();
    }
}