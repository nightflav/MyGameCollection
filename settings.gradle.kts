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
