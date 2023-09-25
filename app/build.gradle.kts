plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "cz.jaro.dopravnipodniky"
    compileSdk = 34

    defaultConfig {
        applicationId = "cz.jaro.dopravnipodniky"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "3.0.0-alpha.24"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures {
        compose = true
        buildConfig = true
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
    androidResources {
        @Suppress("UnstableApiUsage")
        generateLocaleConfig = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidx.jetpack.compose.kotlin.compiler.get()
    }
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = listOf("-Xcontext-receivers")
    }
}

dependencies {

    implementation(libs.core.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(libs.androidx.jetpack.compose.foundation)
    implementation(libs.androidx.jetpack.compose.ui.graphics)
    implementation(libs.androidx.jetpack.compose.ui)
    implementation(libs.androidx.jetpack.compose.ui.tooling)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.jetpack.navigation.compose)
    implementation(libs.androidx.jetpack.compose.material3)
    implementation(libs.androidx.jetpack.compose.material.icons.extended)
    implementation(libs.koin.android)
    implementation(libs.koin.annotations)
    implementation(libs.koin.navigation)
    implementation(libs.koin.compose)
    ksp(libs.koin.annotations.ksp)
    implementation(libs.compose.destinations.core)
    ksp(libs.compose.destinations.ksp)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.serialization)
    implementation(libs.androidx.datastore)
    implementation(kotlin("reflect"))
}
