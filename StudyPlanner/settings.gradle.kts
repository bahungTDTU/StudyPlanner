// settings.gradle.kts

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        // THIS IS THE CORRECT SYNTAX FOR KOTLIN DSL
        maven { url = uri("https://jitpack.io") }
    }
}
rootProject.name = "Study Planner"
include(":app")