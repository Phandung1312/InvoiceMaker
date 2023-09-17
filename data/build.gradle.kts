@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("invoicemaker-android-library")
    id("invoicemaker-hilt")
    id("kotlin-kapt")
}

android {
    namespace = "com.bravo.data"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":app"))
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    implementation(libs.bundles.rxLibs)
    implementation(libs.bundles.coroutinesLibs)
    implementation (libs.timber)
    implementation(libs.bundles.roomLibs)
    annotationProcessor(libs.roomCompiler)
    kapt(libs.roomCompiler)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)

}