plugins {
    id("org.jetbrains.kotlin.android")
    id("com.android.library")
    id("org.jetbrains.kotlin.plugin.parcelize")
}

android {
    namespace = "com.canhub.cropper"

    compileSdk = 33

    defaultConfig {
        minSdk = 21
    }

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(libs.bundles.viewKtx)
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.androidx.exifinterface)
    implementation(libs.bundles.coroutinesLibs)
}


