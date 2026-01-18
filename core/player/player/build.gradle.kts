import io.delight.convention.setNamespace

plugins {
    alias(libs.plugins.delight.android.library)
}

dependencies {
    implementation(projects.core.player.playerApi)
    implementation(libs.bundles.media3)
}

setNamespace("core.player.player")