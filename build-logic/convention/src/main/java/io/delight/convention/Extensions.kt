package io.delight.convention

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
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

internal val Project.applicationExtension: CommonExtension<*, *, *, *, *, *>
    get() = extensions.getByType<ApplicationExtension>()

internal val Project.libraryExtension: CommonExtension<*, *, *, *, *, *>
    get() = extensions.getByType<LibraryExtension>()

internal val Project.androidExtension: CommonExtension<*, *, *, *, *, *>
    get() = runCatching { libraryExtension }
        .recoverCatching { applicationExtension }
        .onFailure { println("Could not find Library or Application extension from this project") }
        .getOrThrow()

fun Project.setNamespace(name: String) {
    androidExtension.apply {
        namespace = "io.delight.$name"
    }
}

internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension<*, *, *, *, *, *>
) {
    pluginManager.apply(libs.findPlugin("kotlin-android").get().get().pluginId)

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

internal fun Project.configureCoroutineKotlin() {
    dependencies {
        "implementation"(libs.findLibrary("coroutines-core").get())
        "testImplementation"(libs.findLibrary("coroutines-test").get())
    }
}

internal fun Project.configureCoroutineAndroid() {
    configureCoroutineKotlin()
    dependencies {
        "implementation"(libs.findLibrary("coroutines-android").get())
    }
}

fun Project.configureHiltAndroid() {
    pluginManager.apply(libs.findPlugin("plugin-hilt").get().get().pluginId)
    pluginManager.apply(libs.findPlugin("plugin-ksp").get().get().pluginId)

    dependencies {
        "implementation"(libs.findLibrary("hilt-android").get())
        "ksp"(libs.findLibrary("hilt-android.compiler").get())
    }
}

internal fun Project.configureJetpackCompose(
    commonExtension: CommonExtension<*, *, *, *, *, *>
) {
    pluginManager.apply(libs.findPlugin("kotlin-compose").get().get().pluginId)

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

fun Project.filterMultiModule(
    implementsCallback: (target: Project) -> Unit
) {
    rootProject
        .subprojects
        .forEach { project ->
            if (project.name != "app" && project.buildFile.isFile) {
                println("!!!!!!!!!!!!!!!!project Info: ${project.name}")
                implementsCallback(project)
            }
        }
}