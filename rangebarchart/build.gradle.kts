plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
}

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("com.vanniktech:gradle-maven-publish-plugin:0.21.0")
    }
}

android {
    defaultConfig {
        compileSdk = Config.compileSdk
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk
        multiDexEnabled = true
    }

    buildTypes {
        named("debug") {
            isMinifyEnabled = true
            consumerProguardFiles("proguard-rules.pro")
        }
        named("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        dataBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core)
    implementation(libs.material)

    api(libs.chart)
}

apply {
    plugin("com.vanniktech.maven.publish")
}
