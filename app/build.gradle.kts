// build.gradle (Module: app)
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    // Panggil plugin Compose Compiler di sini (TANPA VERSI)
    // Versi dideklarasikan di settings.gradle.kts atau build.gradle (project level)
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.example.dnicksaklarpintar"
    compileSdk = 34 // API Level 34 (Android 14)

    defaultConfig {
        applicationId = "com.example.dnicksaklarpintar"
        minSdk = 26 // Minimal API Level 26 (Android 8.0 Oreo) untuk ikon adaptif dan kompatibilitas modern
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
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
        compose = true
    }
    composeOptions {
        // Compose Compiler Extension version yang kompatibel dengan Kotlin 2.1.0
        // Cek https://developer.android.com/jetpack/androidx/releases/compose-kotlin
        // Untuk Kotlin 2.1.0, umumnya 1.6.0 adalah yang cocok.
        kotlinCompilerExtensionVersion = "1.6.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Firebase BOM (Pastikan versi terbaru yang kompatibel dengan Kotlin 2.1.0)
    // 33.15.0 seharusnya sudah kompatibel dengan Kotlin 2.1.0
    implementation(platform("com.google.firebase:firebase-bom:33.15.0"))

    // Firebase Authentication
    implementation("com.google.firebase:firebase-auth-ktx")

    // Firebase Realtime Database
    implementation("com.google.firebase:firebase-database-ktx")

    // Material Icons Extended (untuk Visibility dan VisibilityOff)
    implementation("androidx.compose.material:material-icons-extended")

    // Biometric Prompt (AndroidX Biometric Library)
    // Versi stabil terbaru: https://developer.android.com/jetpack/androidx/releases/biometric
    implementation("androidx.biometric:biometric-ktx:1.2.0-alpha05") // Anda bisa cek versi stabil terbaru

    // Navigation Compose
    // Versi stabil terbaru: https://developer.android.com/jetpack/androidx/releases/navigation
    implementation("androidx.navigation:navigation-compose:2.8.0-beta02") // Anda bisa cek versi stabil terbaru
    implementation("com.google.android.material:material:1.12.0")
    // Core KTX
    // Versi stabil terbaru: https://developer.android.com/jetpack/androidx/releases/core
    implementation("androidx.core:core-ktx:1.13.1")

    // Lifecycle KTX
    // Versi stabil terbaru: https://developer.android.com/jetpack/androidx/releases/lifecycle
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.3")

    // Activity Compose
    // Versi stabil terbaru: https://developer.android.com/jetpack/androidx/releases/activity
    implementation("androidx.activity:activity-compose:1.9.0")

    // Compose BOM (Pastikan versi terbaru yang kompatibel dengan kotlinCompilerExtensionVersion)
    // Versi stabil terbaru: https://developer.android.com/jetpack/compose/setup#versions
    implementation(platform("androidx.compose:compose-bom:2024.06.00")) // Ini yang Anda gunakan

    // Compose UI
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    // Compose Material3
    implementation("androidx.compose.material3:material3")
    implementation("androidx.appcompat:appcompat:1.7.1")

    // Testing Dependencies
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    // Compose Testing
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.06.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    // Debugging and Tools
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}