import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.register
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension

class GenerateLibsPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val generateLibs = target.tasks.register<GenerateLibsTask>("generateLibs") {
            tomlFile.set(target.rootProject.file("gradle/libs.versions.toml"))
            outputFile.set(target.layout.buildDirectory.file("generated/kotlin/com/alexrdclement/gradle/plugin/Libs.kt"))
        }

        target.extensions.configure(KotlinProjectExtension::class.java) {
            sourceSets.named("main") {
                kotlin.srcDir(generateLibs.map { it.outputFile.get().asFile.parentFile.parentFile.parentFile })
            }
        }

        target.tasks.named("compileKotlin") {
            dependsOn(generateLibs)
        }
    }
}
