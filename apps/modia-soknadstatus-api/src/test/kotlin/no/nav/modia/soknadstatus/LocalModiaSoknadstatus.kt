package no.nav.modia.soknadstatus

fun main() {
    System.setProperty("APP_NAME", "arena-soknadstatus-transform")
    System.setProperty("APP_VERSION", "dev")

    System.setProperty("KAFKA_SOURCE_TOPIC", "arena-soknadstatus")
    System.setProperty("KAFKA_TARGET_TOPIC", "modia-soknadstatus")
    System.setProperty("KAFKA_BROKER_URL", "localhost:9092")
    System.setProperty("JDBC_URL", "jdbc:postgresql://localhost:5432/modia-soknadstatus")
    System.setProperty("JDBC_USERNAME", "admin")
    System.setProperty("JDBC_PASSWORD", "admin")

    runApp(port = 9011)
}
