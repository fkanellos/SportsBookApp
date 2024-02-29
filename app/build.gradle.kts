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

    implementation(Compose.compiler)
    implementation(Compose.ui)
    implementation(Compose.uiAndroid)
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
    kapt(DaggerHilt.hiltCompiler)
    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    implementation(DaggerHilt.hiltAndroid)
    implementation("androidx.test.ext:junit-ktx:1.1.5")
    implementation("androidx.test:runner:1.5.2")
    implementation("com.google.ar:core:1.41.0")
    implementation(platform(Compose.composeBom))

    // JUnit Jupiter (JUnit 5) for unit testing
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")

    // Kotlin coroutines testing
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

    // Mockito for mocking in tests
    testImplementation("org.mockito:mockito-core:4.7.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")

    // AssertK for fluent assertions in Kotlin
    testImplementation("com.willowtreeapps.assertk:assertk:0.26.1")

    // MockK for Kotlin specific mocking
    testImplementation("io.mockk:mockk:1.12.5")

    // MockWebServer for testing network calls
    testImplementation("com.squareup.okhttp3:mockwebserver:4.11.0")

    // Turbine for testing flows
    testImplementation("app.cash.turbine:turbine:0.7.0")

    // Android compose UI testing
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.6.2")

    // Dagger Hilt testing
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.45")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:2.50")

    // Robolectric for Android framework mocking in tests
    testImplementation("org.robolectric:robolectric:4.7.3")

}