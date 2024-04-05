@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.royaal.mygamecollection"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.royaal.mygamecollection"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        kotlinCompilerExtensionVersion = libs.versions.androidxComposeCompiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
        resources {
            pickFirsts += "protobuf.meta"
        }
    }
}

dependencies {
    //Android + Compose
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.coil.kt)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.core.splash)
    implementation(libs.androidx.dataStore)
    implementation(libs.androidx.dataStore.core)
    implementation(libs.androidx.compose.ui.tooling.preview)
    //DI
    implementation(libs.dagger.kt)
    implementation(project(":feautre_developers_details:api"))
    implementation(project(":feature_platform_details:api"))
    ksp(libs.dagger.compiler)
    //Serialization
    implementation(libs.retrofit.kotlin.serialization)
    //Modules
    implementation(project(":core:common"))
    implementation(project(":core:common_android"))
    implementation(project(":core:commonui"))
    implementation(project(":core:network"))
    implementation(project(":core:database"))
    implementation(project(":core:datastore"))
    implementation(project(":core:data"))
    implementation(project(":core:sharedprefs"))
    implementation(project(":feature_home:domain"))
    implementation(project(":feature_home:api"))
    implementation(project(":feature_home:data"))
    implementation(project(":feature_home:presentation"))
    implementation(project(":feature_settings"))
    implementation(project(":feature_game_details:domain"))
    implementation(project(":feature_game_details:api"))
    implementation(project(":feature_game_details:data"))
    implementation(project(":feature_game_details:presentation"))
    implementation(project(":feature_favourite:domain"))
    implementation(project(":feature_favourite:api"))
    implementation(project(":feature_favourite:data"))
    implementation(project(":feature_favourite:presentation"))
    implementation(project(":feature_explore:domain"))
    implementation(project(":feature_explore:api"))
    implementation(project(":feature_explore:data"))
    implementation(project(":feature_explore:presentation"))
}