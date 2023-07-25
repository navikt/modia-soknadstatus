package no.nav.modia.soknadsstatus.kafka

import io.micrometer.prometheus.PrometheusConfig
import io.micrometer.prometheus.PrometheusMeterRegistry
import java.util.concurrent.atomic.AtomicInteger

interface DeadLetterQueueMetricsGauge {
    fun increment()
    fun decrement()
    fun set(newValue: Int)
}

class DeadLetterQueueMetricsGaugeImpl(
    gaugeTitle: String,
    metricsRegistry: PrometheusMeterRegistry = PrometheusMeterRegistry(
        PrometheusConfig.DEFAULT
    )
) : DeadLetterQueueMetricsGauge {
    private val gauge = metricsRegistry.gauge(gaugeTitle, AtomicInteger(0))

    override fun increment() {
        gauge?.incrementAndGet()
    }

    override fun decrement() {
        gauge?.decrementAndGet()
    }

    override fun set(newValue: Int) {
        gauge?.set(newValue)
    }
}
