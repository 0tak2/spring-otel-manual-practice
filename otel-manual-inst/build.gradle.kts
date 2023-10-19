plugins {
    java
    id("org.springframework.boot") version "2.7.16"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
}

group = "kr.pe.otak2.study.otel"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("com.h2database:h2")
    implementation("io.opentelemetry:opentelemetry-api:1.31.0")
    implementation("io.opentelemetry:opentelemetry-sdk:1.31.0")
    implementation("io.opentelemetry:opentelemetry-sdk-metrics:1.31.0")
    implementation("io.opentelemetry:opentelemetry-exporter-logging:1.31.0")
    implementation("io.opentelemetry.semconv:opentelemetry-semconv:1.21.0-alpha")
    implementation("io.opentelemetry:opentelemetry-exporter-jaeger:1.31.0")
    implementation("io.opentelemetry:opentelemetry-sdk-extension-autoconfigure:1.31.0")
    implementation("io.opentelemetry:opentelemetry-sdk-extension-autoconfigure-spi:1.31.0")
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
