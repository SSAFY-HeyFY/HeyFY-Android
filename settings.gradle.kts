pluginManagement {
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

rootProject.name = "heyfy"
include(":app")
include(":common")
include(":navigation")
include(":feature")
include(":feature:home")
include(":feature:id")
include(":feature:card")
include(":feature:splash")
include(":feature:login")
include(":feature:sign_up")
include(":feature:account")
include(":feature:send_money")
include(":feature:transaction")
include(":feature:mento_club")
