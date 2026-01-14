import io.delight.convention.configureJetpackCompose

plugins {
    alias(libs.plugins.delight.android.application)
    alias(libs.plugins.kotlin.compose)
}

android {
    buildFeatures {
        compose = true
    }
    configureJetpackCompose(this)
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
