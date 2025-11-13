plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(gradleApi())
    implementation(alexrdclementPluginLibs.kotlin.gradle.plugin)
}

kotlin {
    jvmToolchain(17)
}

gradlePlugin {
    plugins {
        create("generateLibs") {
            id = "generate-libs"
            implementationClass = "GenerateLibsPlugin"
        }
    }
}
