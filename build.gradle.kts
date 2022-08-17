import org.gradle.plugin.management.internal.autoapply.AutoAppliedGradleEnterprisePlugin.GROUP

// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
        jcenter()
    }
    dependencies {
        classpath(libs.kotlin)
        classpath("com.vanniktech:gradle-maven-publish-plugin:0.21.0")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        jcenter()
        maven(url = "https://www.jitpack.io")
    }
}
