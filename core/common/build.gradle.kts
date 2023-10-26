plugins {
    id("java-library")
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ksp)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    //DI
    implementation(libs.dagger.kt)
    ksp(libs.dagger.compiler)
    //Coroutines
    api(libs.coroutines)
}
