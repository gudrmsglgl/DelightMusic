package io.delight.convention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension


val Project.libs
    get() = this.extensions.getByType<VersionCatalogsExtension>().named("libs")

internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension<*, *, *, *, *, *>
) {
    commonExtension.apply {
        compileSdk = property("APP_TARGET_SDK_VERSION").toString().toInt()

        defaultConfig {
            minSdk = property("APP_MIN_SDK_VERSION").toString().toInt()
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            vectorDrawables.useSupportLibrary = true
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }

        buildFeatures.buildConfig = true

        dependencies {

        }

    }

    extensions.configure<KotlinAndroidProjectExtension> {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
}

fun Project.configureJetpackCompose(
    commonExtension: CommonExtension<*, *, *, *, *, *>
) {
    commonExtension.apply {
        buildFeatures {
            compose = true
        }

        dependencies {
            "implementation"(platform(libs.findLibrary("compose-bom").get()))

            "implementation"(libs.findBundle("compose").get())
            "implementation"(libs.findLibrary("kotlinx-collections-immutable").get())

            "debugImplementation"(libs.findLibrary("compose-ui-tooling").get())
            "debugImplementation"(libs.findLibrary("compose-ui-test-manifest").get())
        }
    }

    extensions.configure<ComposeCompilerGradlePluginExtension> {
        this.includeSourceInformation.set(true)
    }

}