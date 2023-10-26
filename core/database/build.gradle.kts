plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.example.royaal.core.database"
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
    //DB
    ksp(libs.room.compiler)
    api(libs.room.runtime)
    implementation(libs.room.ktx)
    //DI
    implementation(libs.dagger.kt)
    ksp(libs.dagger.compiler)
    //Serialization
    implementation(libs.kotlinx.serialization.json)
    //Compose
    implementation(libs.androidx.compose.bom)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.runtime)
}