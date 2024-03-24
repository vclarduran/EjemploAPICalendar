plugins {
    id("java")
    id("application")
}

application {
    mainClass.set("src.main.java.AppointmentBookingUI")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.api-client:google-api-client:2.0.0")
    implementation("com.google.oauth-client:google-oauth-client-jetty:1.34.1")
    implementation("com.google.apis:google-api-services-calendar:v3-rev20220715-2.0.0")
}
