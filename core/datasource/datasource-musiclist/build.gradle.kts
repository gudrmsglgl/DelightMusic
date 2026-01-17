import io.delight.convention.setNamespace

plugins {
    alias(libs.plugins.delight.android.library)
}

dependencies {
    implementation(projects.core.datasource.datasourceMusiclistApi)
    implementation(projects.core.model)
}

setNamespace("core.datasource.musiclist")