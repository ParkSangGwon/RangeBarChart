pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
        jcenter()
    }
}
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "RangeBarChartDemo"
include(":app")
include(":rangebarchart")
