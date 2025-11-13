rootProject.name = "build-logic"

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
    versionCatalogs {
        create("alexrdclementPluginLibs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}
