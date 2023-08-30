@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("invoicemaker-android-application")
    id("invoicemaker-hilt")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    alias(libs.plugins.org.jetbrains.kotlin.android)
}

android {
    namespace = "com.bravo.invoice"

    defaultConfig {
        applicationId = "com.bravo.invoice"
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {
    implementation(project(":basic"))
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.animRecycler)
    implementation(libs.bundles.autodisposeLibs)
    implementation(libs.bundles.viewKtx)
    implementation(libs.bundles.rxLibs)
    implementation(libs.bundles.coroutinesLibs)
    implementation (libs.timber)

    implementation(libs.bundles.dimen)
    implementation(project(mapOf("path" to ":basic")))
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}