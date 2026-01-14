import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

dependencies {
    compileOnly(libs.plgn.android)
    compileOnly(libs.plgn.kotlin)
    compileOnly(libs.plgn.ksp)

    compileOnly(libs.plgn.kotlin.compose.compiler)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "delight.android.application"
            implementationClass = "io.delight.convention.AndroidApplicationConventionPlugin"
        }
    }
}