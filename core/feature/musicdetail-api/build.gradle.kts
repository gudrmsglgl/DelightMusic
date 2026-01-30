import io.delight.convention.configureKotlinSerialization

plugins {
    alias(libs.plugins.delight.kotlin.library)
}

configureKotlinSerialization()

dependencies {
    implementation(projects.core.router.routerApi)
}