plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
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
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.play.services.base)
    implementation(libs.play.services.maps)
    implementation(libs.firebase.storage.ktx)
    implementation(libs.gms.play.services.base)
    implementation(libs.gms.play.services.maps)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Compose Navigation
    implementation(libs.androidx.navigation.compose)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth.ktx)
    implementation(libs.firebase.firestore.ktx)

    // OpenAI / AI Chatbot (We'll use Retrofit)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    // Coil for image loading
    implementation(libs.coil.compose)

    //  Google Sign-In Dependencies
    implementation (libs.play.services.auth)
    implementation (libs.firebase.auth)

    // Lottie Dependency
    implementation (libs.lottie.compose)

    // Picasso dependency
    implementation (libs.picasso)

    // Accompanist Coil for Jetpack Compose
    implementation (libs.accompanist.coil)

    // Coli Dependency
    implementation(libs.coil.compose.v240)

    implementation (libs.androidx.material.icons.extended)


}
apply(plugin = "com.google.gms.google-services")
