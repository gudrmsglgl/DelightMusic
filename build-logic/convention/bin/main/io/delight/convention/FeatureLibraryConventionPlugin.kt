package io.delight.convention

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class FeatureLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with (target) {
            pluginManager.apply(libs.findPlugin("delight-android-library").get().get().pluginId)
            pluginManager.apply(libs.findPlugin("delight-android-compose").get().get().pluginId)

            dependencies {
                "implementation"(project(":core:designsystem"))
            }
        }
    }
}