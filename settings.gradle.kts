pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_PROJECT)
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    versionCatalogs {
        create("alexrdclementPluginLibs") {
            from(files("gradle/libs.versions.toml"))
        }
    }
}

rootProject.name = "gradle-plugins"

include(":plugins")
