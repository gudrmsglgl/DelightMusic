import io.delight.convention.setNamespace

plugins {
    alias(libs.plugins.delight.android.library)
}

setNamespace("core.domain.musiclist")

dependencies {
    implementation(project(":core:domain:domain-musiclist-api"))
    implementation(project(":core:datasource:datasource-musiclist-api"))
    implementation(project(":core:model"))
}