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
            val bom = libs.findLibrary("compose-bom").get()

            add("implementation", platform(bom))
            add("implementation", libs.findLibrary("compose.foundation").get())
            add("implementation", libs.findLibrary("compose.runtime").get())
            add("implementation", libs.findLibrary("compose.ui.tooling.preview").get())

            add("androidTestImplementation", platform(bom))
            add("androidTestImplementation", libs.findLibrary("compose.ui.test.junit4").get())
            add("androidTestImplementation", libs.findLibrary("androidx.test.espresso.core").get())

            add("debugImplementation", libs.findLibrary("compose.ui.tooling").get())
        }
    }
}
