import io.delight.convention.setNamespace

plugins {
    alias(libs.plugins.delight.android.library)
}

dependencies {
    implementation(libs.androidx.media3.common)
}

setNamespace("core.player.player_api")