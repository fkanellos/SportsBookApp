plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("de.mannodermaus.android-junit5") version "1.9.3.0"
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

        testInstrumentationRunner = "gr.sportsbook.presentation.HiltTestRunner"
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
    // AndroidX Libraries
    implementation(AndroidX.coreKtx)
    implementation(AndroidX.appCompat)
    implementation(AndroidX.material3)
    implementation(General.constraintLayout)

    // Compose Libraries
    implementation(platform(Compose.composeBom))
    implementation(Compose.compiler)
    implementation(Compose.ui)
    implementation(Compose.uiAndroid)
    implementation(Compose.uiToolingPreview)
    implementation(Compose.runtime)
    implementation(Compose.liveData)
    implementation(Compose.navigation)
    implementation(Compose.viewModelCompose)
    implementation(Compose.activityCompose)
    implementation(Compose.hiltNavigationCompose)

    // Kotlin Standard Library
    implementation(Kotlin.kotlinStdLib)

    // Networking Libraries
    implementation(Retrofit.retrofit)
    implementation(Retrofit.okHttpLoggingInterceptor)
    implementation(Retrofit.moshiConverter)
    implementation(General.volley)

    // Google Material Design & Other UI Components
    implementation(Google.material)
    implementation(General.materialDialogs)
    implementation(General.snackbar)

    // Image Loading
    implementation(Coil.coilCompose)

    // Dagger Hilt (Dependency Injection)
    implementation(DaggerHilt.hiltAndroid)
    kapt(DaggerHilt.hiltCompiler)
    androidTestImplementation(DaggerHilt.hiltAndroidTesting)
    kaptAndroidTest(DaggerHilt.hiltAndroidCompiler)

    // Testing Libraries
    implementation(TestingLibs.junitKtx)
    implementation(TestingLibs.androidTestRunner)
    testImplementation(TestingLibs.junitJupiter)
    testImplementation(TestingLibs.kotlinxCoroutinesTest)
    androidTestImplementation(TestingLibs.composeUiTestJunit4)

    // Local Jar Dependencies
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
}
