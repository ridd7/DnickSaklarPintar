// settings.gradle.kts
pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal() // Pastikan ini ada
    }
    plugins {
        id("com.android.application") version "8.10.1" // Sesuaikan dengan versi AGP Anda
        id("org.jetbrains.kotlin.android") version "2.1.0" // Sesuaikan dengan versi Kotlin di project level
        id("com.google.gms.google-services") version "4.4.2"

        // DEKLARASI PLUGIN COMPOSE COMPILER DI SINI (Paling direkomendasikan)
        // Versinya HARUS SAMA dengan org.jetbrains.kotlin.android
        id("org.jetbrains.kotlin.plugin.compose") version "2.1.0"
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "DnickSaklarPintar"
include(":app")