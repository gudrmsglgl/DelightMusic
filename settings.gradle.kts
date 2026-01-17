pluginManagement {
    includeBuild("build-logic")

    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")


rootProject.name = "DelightMusic"
include(":app")
include(":core:model")
include(":core:datasource:datasource-musiclist-api")
include(":core:datasource:datasource-musiclist")
include(":core:designsystem")
include(":core:domain:domain-musiclist-api")
include(":core:domain:domain-musiclist")
