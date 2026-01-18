import io.delight.convention.setNamespace

plugins {
    alias(libs.plugins.delight.feature.library)
}

dependencies {
    implementation(projects.core.designsystem)
    implementation(projects.core.model)
    implementation(projects.core.player.playerApi)
}

setNamespace("core.feature.musicdetail")