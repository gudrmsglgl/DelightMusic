package io.delight.convention

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.findPlugin("android-application").get().get().pluginId)
            }

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                configureJetpackCompose(this)
                configureHiltAndroid()
                
                namespace = "io.delight.delightmusic"

                defaultConfig {
                    applicationId = "io.delight.delightmusic"
                    versionCode = property("APP_VERSION_CODE").toString().toInt()
                    versionName = property("APP_VERSION_NAME").toString()
                    targetSdk = property("APP_TARGET_SDK_VERSION").toString().toInt()
                }

                buildTypes {
                    getByName("release") {
                        isMinifyEnabled = false
                        proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
                    }
                }
            }
        }
    }
}