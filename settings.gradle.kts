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
include(":feature:account")
include(":feature:send_money")
include(":feature:transaction")
include(":feature:mento_club")
include(":feature:success")
include(":feature:finance")
include(":feature:exchange")
include(":network")
include(":feature:tips")
include(":data")
include(":data:transfer")
include(":data:login")
include(":data:home")
include(":data:finance")
