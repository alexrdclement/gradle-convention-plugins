import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.compose.ComposePlugin
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class DesktopApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("org.jetbrains.kotlin.multiplatform")
        }
    }
}

fun Project.desktopAppTarget(mainClass: String) {
    extensions.configure<KotlinMultiplatformExtension> {
        jvm {
            mainRun {
                this.mainClass.set(mainClass)
            }
        }

        this@configure.sourceSets {
            jvmMain {
                dependencies {
                    implementation(ComposePlugin.DesktopDependencies.currentOs)
                }
            }
        }
    }
}
