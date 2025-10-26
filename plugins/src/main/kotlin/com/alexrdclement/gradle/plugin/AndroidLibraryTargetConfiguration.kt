package com.alexrdclement.gradle.plugin

import com.android.build.api.dsl.KotlinMultiplatformAndroidDeviceTest
import com.android.build.api.dsl.KotlinMultiplatformAndroidHostTest

data class AndroidLibraryTargetConfiguration(
    val namespace: String,
    val hostTestConfiguration: (KotlinMultiplatformAndroidHostTest.() -> Unit)? = null,
    val instrumentedTestConfiguration: (KotlinMultiplatformAndroidDeviceTest.() -> Unit)? = null,
)
