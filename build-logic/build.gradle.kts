plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("InvoiceMakerApplicationConventionPlugin") {
            id = "invoicemaker-android-application"
            implementationClass = "InvoiceMakerApplicationConventionPlugin"
        }
        register("InvoiceMakerLibraryConventionPlugin") {
            id = "invoicemaker-android-library"
            implementationClass = "InvoiceMakerLibraryConventionPlugin"
        }
        register("InvoiceMakerHiltConventionPlugin") {
            id = "invoicemaker-hilt"
            implementationClass = "InvoiceMakerHiltConventionPlugin"
        }
    }
}