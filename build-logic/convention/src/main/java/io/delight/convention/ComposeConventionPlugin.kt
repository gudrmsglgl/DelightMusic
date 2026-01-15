import com.android.build.api.dsl.LibraryExtension
import io.delight.convention.configureJetpackCompose
import io.delight.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class ComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with (target) {
            val extension = extensions.getByType<LibraryExtension>()

            configureJetpackCompose(extension)
        }
    }
}