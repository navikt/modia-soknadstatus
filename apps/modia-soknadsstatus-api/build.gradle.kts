import org.gradle.jvm.tasks.Jar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val ktor_version: String by project
val kotlinx_serialization_version: String by project
val modia_common_version: String by project
val logback_version: String by project
val junit_version: String by project
val postgres_version: String by project
val graphql_version: String by project
val nav_common_version: String by project
val mockk_version: String by project
val caffeine_version: String by project
val mock_webserver_version: String by project

plugins {
    application
    id("setup.repository")
    kotlin("jvm") version "1.8.21"
    kotlin("plugin.serialization") version "1.8.21"
    id("com.expediagroup.graphql") version "6.4.0"
}

dependencies {
    implementation(project(":common:kafka-stream-transformer"))
    implementation(project(":common:dataformat"))
    implementation(project(":common:ktor"))
    implementation(project(":common:kafka"))
    implementation(project(":common:utils"))
    implementation(project(":tjenestespesifikasjoner:norg-api"))
    implementation(project(":tjenestespesifikasjoner:kodeverk-api"))
    implementation(project(":tjenestespesifikasjoner:skjermede-personer-pip-api"))
    implementation(project(":tjenestespesifikasjoner:pdl-api"))
    implementation(project(":tjenestespesifikasjoner:saf-api"))
    implementation("io.ktor:ktor-server-cio:$ktor_version")
    implementation("io.ktor:ktor-server-status-pages:$ktor_version")
    implementation("io.ktor:ktor-server-cors:$ktor_version")
    implementation("io.ktor:ktor-server-content-negotiation:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
    implementation("io.ktor:ktor-server-call-logging:$ktor_version")
    implementation("io.ktor:ktor-client-okhttp:$ktor_version")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinx_serialization_version")
    implementation("org.apache.kafka:kafka-streams:3.3.1")
    implementation("no.nav.personoversikt:ktor-utils:$modia_common_version")
    implementation("no.nav.personoversikt:logging:$modia_common_version")
    implementation("no.nav.personoversikt:kabac:$modia_common_version")
    implementation("com.zaxxer:HikariCP:5.0.1")
    implementation("org.flywaydb:flyway-core:9.8.3")
    implementation("org.postgresql:postgresql:$postgres_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
    implementation("com.expediagroup:graphql-kotlin-client:$graphql_version")
    implementation("com.expediagroup:graphql-kotlin-ktor-client:$graphql_version")
    implementation("no.nav.common:sts:2.2023.01.10_13.49-81ddc732df3a")
    implementation("no.nav.common:token-client:$nav_common_version")
    implementation("no.nav.common:client:$nav_common_version")
    implementation("com.github.ben-manes.caffeine:caffeine:$caffeine_version")
    implementation("com.github.ben-manes.caffeine:caffeine:$caffeine_version")
    testImplementation("com.google.guava:guava-testlib:32.0.0-jre")
    testImplementation("org.junit.jupiter:junit-jupiter:$junit_version")
    testImplementation("io.mockk:mockk-jvm:$mockk_version")
    testImplementation("com.squareup.okhttp3:mockwebserver:$mock_webserver_version")
    testImplementation("no.nav.personoversikt:kabac:$modia_common_version") {
        artifact {
            classifier = "tests"
        }
    }
}

group = "no.nav.modia.soknadsstatus"
version = ""

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

application {
    mainClass.set("no.nav.modia.soknadsstatus.MainKt")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

val fatJar = task("fatJar", type = Jar::class) {
    archiveBaseName.set("app")
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
    manifest {
        attributes["Implementation-Title"] = "Modia Soknadsstatus API"
        attributes["Implementation-Version"] = archiveVersion
        attributes["Main-Class"] = "no.nav.modia.soknadsstatus.MainKt"
    }
    exclude("META-INF/*.RSA", "META-INF/*.SF", "META-INF/*.DSA")
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    with(tasks.jar.get() as CopySpec)
}

tasks {
    "build" {
        dependsOn(fatJar)
    }
}
