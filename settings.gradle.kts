pluginManagement {
    repositories {
        google()
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

rootProject.name = "My Game Collection"
include(":app")
include(":core:common")
include(":core:network")
include(":core:database")
include(":feature_home:domain")
include(":feature_home:data")
include(":feature_home:presentation")
include(":core:commonui")
include(":core:data")
include(":core:datastore")
include(":feature_settings")
include(":feature_game_details:presentation")
include(":feature_game_details:domain")
include(":feature_game_details:data")
include(":feature_home:api")
include(":feature_game_details:api")
include(":feature_favourite:domain")
include(":feature_favourite:data")
include(":feature_favourite:presentation")
include(":feature_favourite:api")
include(":feature_explore:domain")
include(":feature_explore:api")
include(":feature_explore:data")
include(":feature_explore:presentation")
include(":core:common_android")
include(":core:sharedprefs")
include(":feature_platform_details:api")
include(":feature_platform_details:data")
include(":feature_platform_details:presentation")
include(":feature_platform_details:domain")
include(":feautre_developers_details:api")
include(":feautre_developers_details:data")
include(":feautre_developers_details:presentation")
include(":feautre_developers_details:domain")
