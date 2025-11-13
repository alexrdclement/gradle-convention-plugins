package com.alexrdclement.gradle.plugin

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        buildFeatures {
            compose = true
        }

        dependencies {
            add("implementation", platform(Libs.composeBom))
            add("implementation", Libs.composeFoundation)
            add("implementation", Libs.composeRuntime)
            add("implementation", Libs.composeUiToolingPreview)

            add("androidTestImplementation", platform(Libs.composeBom))
            add("androidTestImplementation", Libs.composeUiTestJunit4)
            add("androidTestImplementation", Libs.androidxTestEspressoCore)

            add("debugImplementation", Libs.composeUiTooling)
        }
    }
}
