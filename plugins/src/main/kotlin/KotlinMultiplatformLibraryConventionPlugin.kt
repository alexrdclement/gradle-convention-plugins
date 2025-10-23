import com.alexrdclement.gradle.plugin.configureKotlin
import com.alexrdclement.gradle.plugin.configureKotlinMultiplatformAndroidLibrary
import com.android.build.api.dsl.androidLibrary
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.Framework

class KotlinMultiplatformLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("org.jetbrains.kotlin.multiplatform")
            apply("com.android.kotlin.multiplatform.library")
        }

        configureKotlin()
    }
}

fun Project.libraryTargets(
    androidNamespace: String,
    iosFrameworkBaseName: String,
    iosFrameworkIsStatic: Boolean = true
) {
    libraryTargets(
        android = AndroidLibraryTargetConfiguration(
            namespace = androidNamespace
        ),
        ios = IosLibraryTargetConfiguration(
            framework = IosFrameworkConfiguration(
                baseName = iosFrameworkBaseName,
                isStatic = iosFrameworkIsStatic
            )
        )
    )
}

fun Project.libraryTargets(
    android: AndroidLibraryTargetConfiguration,
    ios: IosLibraryTargetConfiguration
) {
    extensions.configure<KotlinMultiplatformExtension> {
        applyDefaultHierarchyTemplate()

        androidLibrary {
            namespace = android.namespace
            configureKotlinMultiplatformAndroidLibrary(this)
        }

        jvm()

        val iosFrameworkConfiguration = ios.framework
        iosArm64 {
            binaries.framework {
                configure(iosFrameworkConfiguration)
            }
        }
        iosSimulatorArm64 {
            binaries.framework {
                configure(iosFrameworkConfiguration)
            }
        }

        @OptIn(ExperimentalWasmDsl::class)
        wasmJs {
            browser()
            binaries.executable()
        }
    }
}

data class AndroidLibraryTargetConfiguration(
    val namespace: String
)

data class IosLibraryTargetConfiguration(
    val framework: IosFrameworkConfiguration,
)

data class IosFrameworkConfiguration(
    val baseName: String,
    val isStatic: Boolean = true,
)

fun Framework.configure(configuration: IosFrameworkConfiguration) {
    baseName = configuration.baseName
    isStatic = configuration.isStatic
}
