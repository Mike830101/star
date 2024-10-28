plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.3.4"
    id("io.spring.dependency-management") version "1.1.6"
}

group = "com.mike.star"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
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
    //外部jar引用
//    implementation(fileTree("libs"))

    //spring boot
    implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.3")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-web")
//    implementation("org.springframework.boot:spring-boot-starter-security")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // poi excel匯出
    implementation("org.apache.poi:poi:5.3.0")
    implementation("org.apache.poi:poi-ooxml:5.3.0")

    //validation
    implementation("jakarta.validation:jakarta.validation-api:3.1.0")

    // encryption: bouncy castle, jasypt
    implementation("org.bouncycastle:bcprov-jdk15on:1.70")
    implementation("com.github.ulisesbocchio:jasypt-spring-boot-starter:3.0.5")

    // uuid generator
    implementation("com.fasterxml.uuid:java-uuid-generator:4.2.0")

    // mybatis
    implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.3")
    implementation("org.mybatis.dynamic-sql:mybatis-dynamic-sql:1.5.2")
//    implementation("org.mybatis:mybatis-typehandlers-jsr310:1.0.2")

    // Postgres
    runtimeOnly("org.postgresql:postgresql")

    // Lombok
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    // Test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.springframework.security:spring-security-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
