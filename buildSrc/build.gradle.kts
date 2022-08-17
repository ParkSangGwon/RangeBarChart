plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}
repositories {
    mavenCentral()
    google()
}

dependencies {
    implementation(libs.gradle)
    implementation(libs.kotlin)
}
