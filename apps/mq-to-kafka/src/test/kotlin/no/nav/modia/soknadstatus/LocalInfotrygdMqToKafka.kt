package no.nav.modia.soknadstatus

import io.ktor.server.application.*
import io.ktor.server.cio.*
import no.nav.personoversikt.common.ktor.utils.KtorServer

fun main() {
    System.setProperty("APP_NAME", "infotrygd-mq-to-kafka")
    System.setProperty("APP_VERSION", "dev")

    System.setProperty("KAFKA_TOPIC", "infotrygd-soknadstatus")
    System.setProperty("KAFKA_BROKER_URL", "localhost:9092")

    System.setProperty("JMS_QUEUE", "infotrygd-soknadstatus")
    System.setProperty("JMS_HOST", "localhost")
    System.setProperty("JMS_PORT", "61616")
    System.setProperty("JMS_QUEUEMANAGER", "")
    System.setProperty("JMS_USERNAME", "")
    System.setProperty("JMS_PASSWORD", "")

    KtorServer.create(
        factory = CIO,
        port = 9000,
        application = Application::mqToKafkaModule
    ).start(wait = true)
}
