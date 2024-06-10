import org.jooq.meta.jaxb.Logging

plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("io.micronaut.application") version "4.4.0"
    id("io.micronaut.aot") version "4.4.0"
    id("nu.studer.jooq") version "9.0"
}

version = "0.1"
group = "pl.greywarden.tutorial"

repositories {
    mavenCentral()
}

dependencies {
    micronaut()
    postgresDriver()

    implementation("commons-codec:commons-codec:1.17.0")
    implementation("org.jooq:jooq:3.19.9")
    runtimeOnly("ch.qos.logback:logback-classic")
}


application {
    mainClass = "pl.greywarden.tutorial.Application"
}
java {
    sourceCompatibility = JavaVersion.toVersion("21")
    targetCompatibility = JavaVersion.toVersion("21")
}


graalvmNative.toolchainDetection = true
micronaut {
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("pl.greywarden.tutorial.*")
    }
    aot {
    // Please review carefully the optimizations enabled below
    // Check https://micronaut-projects.github.io/micronaut-aot/latest/guide/ for more details
        optimizeServiceLoading = false
        convertYamlToJava = false
        precomputeOperations = true
        cacheEnvironment = true
        optimizeClassLoading = true
        deduceEnvironment = true
        optimizeNetty = true
        replaceLogbackXml = true
    }
}


tasks.named<io.micronaut.gradle.docker.NativeImageDockerfile>("dockerfileNative") {
    jdkVersion = "21"
}

jooq {
    configurations {
        create("main") {
            jooqConfiguration.apply {
                logging = Logging.INFO
                jdbc.apply {
                    driver = "org.postgresql.Driver"
                    url = "jdbc:postgresql://localhost:5432/todo_list"
                    user = "postgres"
                    password = "postgres"
                }
                generator.apply {
                    name = "org.jooq.codegen.DefaultGenerator"
                    database.apply {
                        name = "org.jooq.meta.postgres.PostgresDatabase"
                        inputSchema = "public"
                    }
                    generate.apply {
                        isDeprecated = false
                    }
                    target.apply {
                        packageName = "pl.greywarden.tutorial.jooq"
                    }
                    strategy.name = "org.jooq.codegen.DefaultGeneratorStrategy"
                }
            }
        }
    }
}


fun DependencyHandlerScope.postgresDriver() {
    val postgres = "org.postgresql:postgresql:42.7.3"
    implementation(postgres)
    jooqGenerator(postgres)
}

fun DependencyHandlerScope.micronaut() {
    annotationProcessor("io.micronaut:micronaut-http-validation")
    annotationProcessor("io.micronaut.serde:micronaut-serde-processor")
    implementation("io.micronaut.serde:micronaut-serde-jackson")
    implementation("io.micronaut.groovy:micronaut-runtime-groovy")
    compileOnly("io.micronaut:micronaut-http-client")
    testImplementation("io.micronaut:micronaut-http-client")
}
