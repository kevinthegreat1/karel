plugins {
    id("java")
}

group = "stanford"
version = "3.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("junit:junit:4.13.2")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

java {
    withSourcesJar()
}

tasks.test {
    useJUnitPlatform()
}
