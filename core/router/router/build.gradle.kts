import io.delight.convention.setNamespace

plugins {
    alias(libs.plugins.delight.android.library)
    alias(libs.plugins.delight.android.compose)
}

dependencies {
    implementation(projects.core.router.routerApi)
}
setNamespace("io.delight.core.router")