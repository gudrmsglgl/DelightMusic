import com.android.build.gradle.LibraryExtension
import io.delight.convention.configureCoroutineAndroid
import io.delight.convention.configureKotlinAndroid
import io.delight.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply(libs.findPlugin("android-library").get().get().pluginId)

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                configureCoroutineAndroid()

                defaultConfig.targetSdk = 35
                defaultConfig.consumerProguardFiles("consumer-rules.pro")

                buildFeatures {
                    buildConfig = true
                }

                buildTypes {
                    release {
                        isMinifyEnabled = false
                        proguardFiles(
                            getDefaultProguardFile("proguard-android-optimize.txt"),
                            "proguard-rules.pro"
                        )
                    }
                }

            }
        }
    }
}