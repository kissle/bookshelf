plugins {
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.allopen") version "1.9.22"
    id("io.quarkus")
}

repositories {
    mavenCentral()
    mavenLocal()
}

val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusPlatformVersion: String by project

dependencies {

    implementation(enforcedPlatform("${quarkusPlatformGroupId}:${quarkusPlatformArtifactId}:${quarkusPlatformVersion}"))
    implementation("io.quarkus:quarkus-resteasy-reactive-jackson")
    implementation("io.quarkus:quarkus-resteasy-reactive")
    implementation("io.quarkus:quarkus-reactive-pg-client")
//    implementation("io.quarkus:quarkus-minikube")
    //implementation("io.quarkus:quarkus-rest-client-reactive")
    //implementation("io.quarkus:quarkus-container-image-docker")

    //implementation("io.quarkus:quarkus-smallrye-fault-tolerance")
    //implementation("io.quarkus:quarkus-smallrye-graphql")
    implementation("io.quarkus:quarkus-smallrye-health")
    //implementation("io.quarkus:quarkus-rest-client-reactive-jackson")
    //implementation("io.quarkus:quarkus-jdbc-postgresql")
    //implementation("io.quarkus:quarkus-hibernate-validator")
    //implementation("io.quarkus:quarkus-smallrye-openapi")
    implementation("io.quarkus:quarkus-kotlin")
    implementation("io.quarkus:quarkus-hibernate-reactive-panache-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("io.quarkus:quarkus-arc")

    // neo4j
    // https://mvnrepository.com/artifact/io.quarkiverse.neo4j/quarkus-neo4j
    implementation("io.quarkiverse.neo4j:quarkus-neo4j:3.9.0")
    // https://mvnrepository.com/artifact/org.neo4j/neo4j-ogm-quarkus
    implementation("org.neo4j:neo4j-ogm-quarkus:3.7.0")
    testImplementation("org.testcontainers:neo4j:1.19.7")

    testImplementation("io.quarkus:quarkus-junit5")
    testImplementation("io.quarkus:quarkus-junit5-mockito")
    testImplementation("org.mockito.kotlin:mockito-kotlin:3.2.0")
    testImplementation("io.quarkus:quarkus-test-vertx")
    testImplementation("io.rest-assured:rest-assured")
    testImplementation("org.testcontainers:testcontainers")
    testImplementation("org.testcontainers:postgresql")
}

group = "secondbrain.kissle"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<Test> {
    systemProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager")
}
allOpen {
    annotation("jakarta.ws.rs.Path")
    annotation("jakarta.enterprise.context.ApplicationScoped")
    annotation("jakarta.persistence.Entity")
    annotation("io.quarkus.test.junit.QuarkusTest")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_17.toString()
    kotlinOptions.javaParameters = true
}
