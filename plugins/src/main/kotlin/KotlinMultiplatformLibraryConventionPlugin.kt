import com.alexrdclement.gradle.plugin.configureKotlin
import com.alexrdclement.gradle.plugin.configureKotlinMultiplatformAndroidLibrary
import com.android.build.api.dsl.androidLibrary
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KotlinMultiplatformLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("org.jetbrains.kotlin.multiplatform")
            apply("com.android.kotlin.multiplatform.library")
        }
        extensions.configure<KotlinMultiplatformExtension> {
            applyDefaultHierarchyTemplate()
            androidLibrary {
                configureKotlinMultiplatformAndroidLibrary(this)
            }
            jvm()
            iosX64()
            iosArm64()
            iosSimulatorArm64()
            @OptIn(ExperimentalWasmDsl::class)
            wasmJs {
                browser()
                binaries.executable()
            }
        }
        configureKotlin()
    }
}

fun Project.kotlinMultiplatform(action: KotlinMultiplatformExtension.() -> Unit) {
    extensions.configure<KotlinMultiplatformExtension>(action)
}

val Project.kotlinMultiplatform: KotlinMultiplatformExtension
    get() = extensions.getByType<KotlinMultiplatformExtension>()
