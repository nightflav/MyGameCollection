import java.util.Properties

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
}

val properties = Properties()
properties.load(project.rootProject.file("secrets.properties").inputStream())

android {
    namespace = "com.example.royaal.core.network"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        debug {
            buildConfigField("String", "API_URL", "\"https://api.rawg.io/api/\"")
            buildConfigField("String", "API_KEY", properties.getProperty("apikey"))
            buildConfigField("String", "TWITCH_AUTH_URL", "\"https://id.twitch.tv/\"")
            buildConfigField("String", "TWITCH_API_URL", "\"https://api.igdb.com/v4/\"")
            buildConfigField(
                "String",
                "TWITCH_CLIENT_ID",
                properties.getProperty("twitch_client_id")
            )
            buildConfigField("String", "TWITCH_SECRET_KEY", properties.getProperty("twitch_secret"))
        }
        release {
            buildConfigField("String", "API_URL", "\"https://api.rawg.io/api/\"")
            buildConfigField("String", "API_KEY", properties.getProperty("apikey"))
            buildConfigField("String", "TWITCH_AUTH_URL", "\"https://id.twitch.tv/\"")
            buildConfigField("String", "TWITCH_API_URL", "\"https://api.igdb.com/v4/\"")
            buildConfigField(
                "String",
                "TWITCH_CLIENT_ID",
                properties.getProperty("twitch_client_id")
            )
            buildConfigField("String", "TWITCH_SECRET_KEY", properties.getProperty("twitch_secret"))
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
    packaging {
        resources {
            pickFirsts += "protobuf.meta"
        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidxComposeCompiler.get()
    }
}

dependencies {
    //Modules
    implementation(project(":core:data"))
    implementation(project(":core:sharedprefs"))
    //Retrofit + OkHttp
    implementation(libs.retrofit.core)
    api(libs.retrofit.kotlin.serialization)
    implementation(libs.okhttp.logging)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit.scalar)
    //DI
    implementation(libs.dagger.kt)
    ksp(libs.dagger.compiler)
    //Compose
    implementation(libs.androidx.compose.bom)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.runtime)
    //Wrapper
    api(libs.twitch)
}