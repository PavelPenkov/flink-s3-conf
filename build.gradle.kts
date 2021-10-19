plugins {
    java
}

group = "me.penkov"
version = "1.0-SNAPSHOT"

val flinkVersion = "1.14.0"
val scalaVersion = "2.12"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.apache.flink:flink-streaming-java_$scalaVersion:$flinkVersion")
    implementation("org.apache.flink:flink-runtime-web_$scalaVersion:$flinkVersion")
    implementation("org.apache.flink:flink-s3-fs-hadoop:$flinkVersion")
    implementation("org.apache.flink:flink-clients_$scalaVersion:$flinkVersion")
    implementation("ch.qos.logback:logback-classic:1.2.6")
}