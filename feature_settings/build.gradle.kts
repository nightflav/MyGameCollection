@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.royaal.feature_settings"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        kotlinCompilerExtensionVersion = "1.5.3"
    }
}

dependencies {
    //Android + Compose + Material
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.lifecycle.viewModelCompose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.iconsExtended)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.ui.util)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.foundation.layout)
    implementation(libs.androidx.navigation.compose)
    //Modules
    implementation(project(mapOf("path" to ":core:datastore")))
    implementation(project(mapOf("path" to ":core:common")))
    implementation(project(mapOf("path" to ":core:data")))
    //DI
    implementation(libs.dagger.kt)
    implementation(project(mapOf("path" to ":core:database")))
    ksp(libs.dagger.compiler)
}