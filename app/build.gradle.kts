plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    kotlin("kapt")

    id("kotlin-kapt")
    alias(libs.plugins.androidx.navigation.safeargs)

}

android {
    namespace = "com.example.pcmovies"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.pcmovies"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
        languageVersion = "1.9"
        apiVersion = "1.9"
    }

    buildFeatures{
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.hilt.common)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation (libs.material.v1110)


    implementation (libs.androidx.swiperefreshlayout)

    // Retrofit + Gson
    implementation (libs.retrofit)
    implementation (libs.converter.gson)
    implementation(libs.logging.interceptor)

// Paging 3
    implementation (libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.runtime.ktx)

// Glide
    implementation(libs.glide)
    kapt(libs.compiler)

// Room
    implementation (libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    kapt (libs.androidx.room.compiler)
    implementation(libs.androidx.room.paging)

// Kotlin Coroutines
    implementation (libs.kotlinx.coroutines.android)

// Lifecycle and ViewModel
    implementation (libs.androidx.lifecycle.viewmodel.ktx)
    implementation (libs.androidx.lifecycle.livedata.ktx)

// WorkManager
    implementation (libs.androidx.work.runtime.ktx)

// Hilt (Dependency Injection)
    implementation (libs.hilt.android)
    kapt (libs.hilt.compiler)

    kapt (libs.hilt.android.compiler)

// Navigation
    implementation (libs.androidx.navigation.fragment.ktx)
    implementation (libs.androidx.navigation.ui.ktx)

    // Kotlin
    implementation (libs.kotlin.stdlib)
    kapt (libs.javapoet)

// WorkManager with Hilt
    implementation (libs.androidx.hilt.work)
    kapt (libs.androidx.hilt.compiler)

    implementation(libs.dagger)
    kapt(libs.dagger.compiler)


// Hilt + WorkManager integration
    implementation (libs.androidx.hilt.work.v100)
    kapt (libs.androidx.hilt.compiler.v100)

// WorkManager
    implementation (libs.androidx.work.runtime.ktx.v290)

    implementation (libs.hilt.work.v100)
    kapt (libs.hilt.compiler.v100)



}

//kapt {
//    correctErrorTypes = true
//}