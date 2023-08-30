@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("invoicemaker-android-library")
    id("invoicemaker-hilt")
    id("kotlin-kapt")
    id("kotlin-parcelize")
}

android {
    namespace = "com.bravo.basic"
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {

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

    implementation(libs.sdp)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}