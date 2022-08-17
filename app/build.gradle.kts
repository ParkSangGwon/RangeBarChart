plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    defaultConfig {
        applicationId = ApplicationId.id
        compileSdk = Config.compileSdk
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk

        versionCode = Releases.versionCode
        versionName = Releases.versionName
        multiDexEnabled = true
    }

    buildTypes {
        named("debug") {
            isMinifyEnabled = true
            proguardFiles("proguard-rules.pro")
        }
        named("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    // For Kotlin projects
    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        dataBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core)
    implementation(libs.material)

    implementation(projects.rangebarchart)
}
