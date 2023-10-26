plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.royaal.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 26
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
}

dependencies {
    //Modules
    implementation(project(":core:common"))
    implementation(project(":feature_game_details:domain"))
    implementation(project(":core:network"))
    implementation(project(":core:database"))
    //DI
    implementation(libs.dagger.kt)
    ksp(libs.dagger.compiler)
    //Compose
    implementation(libs.androidx.compose.bom)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.runtime)}