plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.example.inventariosapp"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.inventariosapp"
        minSdk = 29
        targetSdk = 36
        versionCode = 1
<<<<<<< Updated upstream
        versionName = "1.0"
=======
        versionName = "1.QA30"
>>>>>>> Stashed changes
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
        freeCompilerArgs = listOf("-XXLanguage:+PropertyParamAnnotationDefaultTargetMode")
    }

    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.lifecycle.runtime)

    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
<<<<<<< Updated upstream
=======
    implementation(libs.androidx.compose.ui.text)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.animation.core)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.ui.geometry)
    implementation(libs.androidx.compose.animation)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.foundation.layout)
    implementation(libs.androidx.ui.text)
    implementation(libs.core.ktx)
    implementation(libs.androidx.ui.test.junit4)
    implementation(libs.androidx.navigation.testing)
    implementation(libs.ui)
    implementation(libs.androidx.glance)
    implementation(libs.androidx.compose.remote.creation.compose)

    debugImplementation(platform(libs.androidx.compose.bom))
>>>>>>> Stashed changes
    debugImplementation(libs.androidx.ui.tooling)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter)
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // Navigation
    implementation(libs.androidx.navigation.compose)

    // Room (KSP)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    // Hilt (KSP)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    // DataStore
    implementation(libs.androidx.datastore)
}