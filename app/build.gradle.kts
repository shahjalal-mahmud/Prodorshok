import java.util.Properties

plugins {
    id("com.android.application")
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.prodorshok"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.prodorshok"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        // Add this block to read API key from local.properties
        val localProperties = Properties().apply {
            load(File(rootProject.rootDir, "local.properties").inputStream())
        }
        buildConfigField("String", "OPENROUTER_API_KEY", "\"${localProperties.getProperty("OPENROUTER_API_KEY")}\"")
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
        freeCompilerArgs += listOf(
            "-Xcontext-receivers",
            "-opt-in=kotlin.RequiresOptIn",
            "-P", "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=${buildDir}/compose-metrics",
            "-P", "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=${buildDir}/compose-reports"
        )
    }

    buildFeatures {
        compose = true
        buildConfig = true // Enable BuildConfig generation
    }
}

dependencies {
    // Core dependencies
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.9.0") // Add for ViewModel support

    // Compose dependencies
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation ("androidx.compose.material3:material3:1.3.2")
    implementation(libs.androidx.material.icons.extended)

    // Navigation
    implementation(libs.androidx.navigation.compose)

    // Firebase dependencies
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth.ktx)
    implementation(libs.firebase.firestore.ktx)
    implementation(libs.firebase.storage.ktx)

    // Google Services
    implementation(libs.play.services.auth)
    implementation(libs.play.services.maps)

    // Networking
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0") // For debugging API calls

    // Image Loading
    implementation(libs.coil.compose)
    implementation(libs.accompanist.coil)
    implementation(libs.picasso)

    // Animations
    implementation(libs.lottie.compose)

    // Ensure correct version for SwipeRefresh (Stable)
    implementation ("com.google.accompanist:accompanist-swiperefresh:0.24.13-rc")

    // Kotlin Coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // Firebase Task support for coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.3")

    // Parsing
    implementation("io.github.jeziellago:compose-markdown:0.3.0")

    // Testing
    testImplementation(libs.junit)

    // Android Test
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

}
