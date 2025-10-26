package com.alexrdclement.gradle.plugin

import org.jetbrains.kotlin.gradle.plugin.mpp.Framework

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
