plugins {
    id("java")
}

group = "acm"
version = "3.0.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

java {
    withSourcesJar()
}

tasks.test {
    useJUnitPlatform()
}
