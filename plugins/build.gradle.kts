import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
    alias(libs.plugins.maven.publish)
}

group = "com.alexrdclement.gradle.plugin"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

dependencies {
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(libs.ksp.gradle.plugin)
    compileOnly(libs.room.gradle.plugin)
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        register("androidApplicationCompose") {
            id = "com.alexrdclement.gradle.plugin.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidApplication") {
            id = "com.alexrdclement.gradle.plugin.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "com.alexrdclement.gradle.plugin.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidLibrary") {
            id = "com.alexrdclement.gradle.plugin.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidLibraryTestFixtures") {
            id = "com.alexrdclement.gradle.plugin.android.library.test.fixtures"
            implementationClass = "AndroidLibraryTestFixturesConventionPlugin"
        }
        register("androidFeature") {
            id = "com.alexrdclement.gradle.plugin.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }
        register("androidTest") {
            id = "com.alexrdclement.gradle.plugin.android.test"
            implementationClass = "AndroidTestConventionPlugin"
        }
        register("androidHilt") {
            id = "com.alexrdclement.gradle.plugin.android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }
        register("androidHiltTestFixtures") {
            id = "com.alexrdclement.gradle.plugin.android.hilt.test.fixtures"
            implementationClass = "AndroidHiltTestFixturesConventionPlugin"
        }
        register("androidRoom") {
            id = "com.alexrdclement.gradle.plugin.android.room"
            implementationClass = "AndroidRoomConventionPlugin"
        }
        register("jvmLibrary") {
            id = "com.alexrdclement.gradle.plugin.jvm.library"
            implementationClass = "JvmLibraryConventionPlugin"
        }
    }
}
