plugins {
    id("java")
}

group = "com.kousenit"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven { setUrl("https://jitpack.io") }
}

dependencies {
    // Gson parsing
    implementation("com.google.code.gson:gson:2.11.0")

    // Simple logging
    implementation("org.slf4j:slf4j-api:2.0.13")
    implementation("org.slf4j:slf4j-simple:2.0.13")

    // Bean validation
    implementation("org.hibernate.validator:hibernate-validator:8.0.1.Final")
    //implementation("jakarta.validation:jakarta.validation-api:3.0.2")
    implementation("org.glassfish.expressly:expressly:5.0.0")

    // Audio player
    implementation("com.github.umjammer:jlayer:1.0.3")

    // JUnit 5 dependencies
    testImplementation(platform("org.junit:junit-bom:5.10.3"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}