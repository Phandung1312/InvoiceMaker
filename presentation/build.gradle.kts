@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("invoicemaker-android-application")
    id("invoicemaker-hilt")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.com.google.gms.google.services)
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
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":cropper"))
    implementation(project(":swiper"))
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
    implementation(libs.lottie)
    implementation(libs.bundles.dimen)
    implementation(libs.bundles.mapsLibs)
    implementation(libs.itext)
    implementation(libs.moshi)
    implementation(libs.bundles.roomLibs)
    implementation(libs.gson)
    implementation(libs.sweetalert)
    annotationProcessor(libs.roomCompiler)
    kapt(libs.roomCompiler)
    implementation(libs.zoomage)
    implementation(libs.dexter)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}