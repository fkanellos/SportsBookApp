plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = ProjectConfig.appId
    compileSdk = 34

    defaultConfig {
        applicationId = ProjectConfig.appId
        minSdk = ProjectConfig.minSdk
        targetSdk = ProjectConfig.targetSdk
        versionCode = ProjectConfig.versionCode
        versionName = ProjectConfig.versionName
        multiDexEnabled = true
        resourceConfigurations += mutableListOf("en","el")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    testOptions {
        unitTests.apply {
            isIncludeAndroidResources = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs += "-Xopt-in=kotlin.time.ExperimentalTime"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Compose.composeCompilerVersion
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(Compose.compiler)
    implementation(Compose.ui)
    implementation(Compose.uiToolingPreview)
    implementation(Compose.hiltNavigationCompose)
    implementation(Compose.liveData)
    implementation(AndroidX.material3)
    implementation(Compose.runtime)
    implementation(Compose.navigation)
    implementation(Compose.viewModelCompose)
    implementation(Compose.activityCompose)

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Kotlin.kotlinStdLib)
    implementation(AndroidX.coreKtx)
    implementation(AndroidX.appCompat)
    implementation(General.constraintLayout)

    implementation(Retrofit.okHttp)
    implementation(Retrofit.retrofit)
    implementation(Retrofit.okHttpLoggingInterceptor)
    implementation(Retrofit.moshiConverter)

    implementation(General.volley)
    implementation(General.materialDialogs)
    implementation(General.snackbar)

    implementation(Google.material)

    implementation(Coil.coilCompose)

    // Logging framework
    implementation(Logging.slf4j)
    implementation(Logging.logback)
    implementation(Logging.securityLogging) {
        exclude(group = "ch.qos.logback")
    }

    implementation(Navigation.navigationFragmentKtx)
    implementation(Navigation.navigationUiKtx)

    implementation(DaggerHilt.hiltAndroid)
    implementation("androidx.test.ext:junit-ktx:1.1.5")
    testImplementation("junit:junit:4.12")
    kapt(DaggerHilt.hiltCompiler)

    implementation(platform(Compose.composeBom))

// Unit Testing
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.mockito:mockito-core:3.3.3")
    testImplementation("androidx.arch.core:core-testing:2.2.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

    // AndroidX Test - Local Unit Tests
    testImplementation("androidx.test:core:1.5.0")
    testImplementation("androidx.test.ext:junit:1.1.5")

    // For LiveData testing
    testImplementation("androidx.test.ext:junit-ktx:1.1.5")
    testImplementation("androidx.arch.core:core-testing:2.2.0")

    // For Coroutine testing
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    testImplementation("org.mockito:mockito-core:4.7.0") // Adjust the version as needed
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0") // Adjust the version as needed
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3") // Use the latest version


}