import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.android.application")
    id("kotlin-android")
}

android {
    compileSdk = 32
    buildToolsVersion = "31.0.0"

    defaultConfig {
        applicationId = "cz.jaro.dopravnipodniky"
        minSdk = 26
        targetSdk = 32
        versionCode = 1
        versionName = "2.2 (2022 04. 08. A)"

        multiDexEnabled = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables.useSupportLibrary = true
    }

    viewBinding {
        isEnabled = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        getByName("release") {

        }
    }

    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_1_8)
        targetCompatibility(JavaVersion.VERSION_1_8)
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
    tasks.withType(KotlinCompile::class).all {
        kotlinOptions.freeCompilerArgs = listOf("-Xcontext-receivers")
    }
    
    namespace = "cz.jaro.dopravnipodniky"
}

dependencies {

    val navigationVersion = "2.4.2"

    implementation("com.google.code.gson:gson:2.9.0")

    implementation(files("libs/processing-core.jar"))

    implementation("com.google.android.material:material:1.7.0-alpha01")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("com.google.android.material:material:1.7.0-alpha01")
    implementation("androidx.constraintlayout:constraintlayout:2.1.3")
    implementation("androidx.navigation:navigation-fragment-ktx:$navigationVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navigationVersion")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}
