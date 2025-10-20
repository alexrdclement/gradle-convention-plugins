import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
    alias(libs.plugins.maven.publish)
}

group = "com.alexrdclement.gradle"

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
    implementation(libs.android.gradle.plugin)
    implementation(libs.android.tools.common)
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.compose.compiler.plugin)
    implementation(libs.hilt.gradle.plugin)
    implementation(libs.ksp.gradle.plugin)
    implementation(libs.room.gradle.plugin)
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
