package no.nav.modia.soknadsstatus

fun main() {
    System.setProperty("APP_NAME", "arena-soknadsstatus-transform")
    System.setProperty("APP_VERSION", "dev")

    System.setProperty("KAFKA_SOURCE_TOPIC", "arena-soknadsstatus")
    System.setProperty("KAFKA_TARGET_TOPIC", "modia-soknadsstatus")
    System.setProperty("KAFKA_BROKER_URL", "localhost:9092")

    runApp(port = 9011)
}
