import io.delight.convention.setNamespace

plugins {
    alias(libs.plugins.delight.feature.library)
}

dependencies {
    implementation(projects.core.domain.domainMusiclistApi)
    implementation(projects.core.model)
    implementation(projects.core.player.playerApi)
}

setNamespace("core.feature.musiclist")