import io.delight.convention.filterMultiModule

plugins {
    alias(libs.plugins.delight.android.application)
}

dependencies {
    filterMultiModule {
        implementation(it)
    }
}
