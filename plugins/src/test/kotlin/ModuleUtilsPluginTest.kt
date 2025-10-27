import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.File

class ModuleUtilsPluginTest {

    @get:Rule
    val testProjectDir = TemporaryFolder()

    private lateinit var settingsFile: File
    private lateinit var buildFile: File
    private lateinit var gradlePropertiesFile: File

    private val testNamespace = "com.example.test"

    @Before
    fun setup() {
        settingsFile = testProjectDir.newFile("settings.gradle.kts")
        buildFile = testProjectDir.newFile("build.gradle.kts")
        gradlePropertiesFile = testProjectDir.newFile("gradle.properties")

        val gradleDir = File(testProjectDir.root, "gradle")
        gradleDir.mkdirs()
        val libsFile = File(gradleDir, "libs.versions.toml")
        libsFile.writeText("""
            [versions]
            kotlin = "2.2.20"

            [plugins]
            alexrdclement-kotlin-multiplatform-library = { id = "com.alexrdclement.gradle.plugin.kotlin.multiplatform.library", version = "0.0.1" }
            alexrdclement-compose-multiplatform = { id = "com.alexrdclement.gradle.plugin.compose.multiplatform", version = "0.0.1" }
        """.trimIndent())

        settingsFile.writeText("""
            rootProject.name = "test-project"

            enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
        """.trimIndent())

        buildFile.writeText("""
            plugins {
                id("com.alexrdclement.gradle.plugin.module.utils")
            }
        """.trimIndent())

        gradlePropertiesFile.writeText("""
            namespace=$testNamespace
        """.trimIndent())
    }

    @Test
    fun `creates module with correct structure and source sets`() {
        val result = GradleRunner.create()
            .withProjectDir(testProjectDir.root)
            .withArguments("createLibraryModule", "--name=auth")
            .withPluginClasspath()
            .build()

        assertEquals(TaskOutcome.SUCCESS, result.task(":createLibraryModule")?.outcome)

        val moduleDir = File(testProjectDir.root, "auth")
        assertTrue("Module directory should exist", moduleDir.exists())

        val buildFile = File(moduleDir, "build.gradle.kts")
        assertTrue("build.gradle.kts should exist", buildFile.exists())

        val sourceSets = listOf(
            "commonMain",
            "commonTest",
            "androidMain",
            "androidTest",
            "iosMain",
            "iosTest",
            "jvmMain"
        )
        for (sourceSet in sourceSets) {
            val sourceSetDir = File(moduleDir, "src/$sourceSet/kotlin/com/example/test/auth")
            assertTrue("Source set $sourceSet should exist", sourceSetDir.exists())
        }

        val settingsContent = settingsFile.readText()
        assertTrue("Settings should include module", settingsContent.contains("include(\":auth\")"))

        val buildContent = buildFile.readText()
        assertTrue("Build file should have namespace", buildContent.contains("com.example.test.auth"))
    }

    @Test
    fun `creates nested module with colon-separated name`() {
        val result = GradleRunner.create()
            .withProjectDir(testProjectDir.root)
            .withArguments("createLibraryModule", "--name=:feature:auth")
            .withPluginClasspath()
            .build()

        assertEquals(TaskOutcome.SUCCESS, result.task(":createLibraryModule")?.outcome)

        val moduleDir = File(testProjectDir.root, "feature/auth")
        assertTrue("Nested module directory should exist", moduleDir.exists())

        val buildFile = File(moduleDir, "build.gradle.kts")
        assertTrue("build.gradle.kts should exist in nested location", buildFile.exists())

        val sourceSets = listOf(
            "commonMain",
            "commonTest",
            "androidMain",
            "androidTest",
            "iosMain",
            "iosTest",
            "jvmMain"
        )
        for (sourceSet in sourceSets) {
            val sourceSetDir = File(moduleDir, "src/$sourceSet/kotlin/com/example/test/feature/auth")
            assertTrue("Source set $sourceSet should exist", sourceSetDir.exists())
        }

        val settingsContent = settingsFile.readText()
        assertTrue("Settings should include nested module", settingsContent.contains("include(\":feature:auth\")"))
    }

    @Test
    fun `settings file entries are sorted alphabetically`() {
        // Add in unsorted order
        var settingsContent = settingsFile.readText()
        settingsContent += "\ninclude(\":zeta\")\ninclude(\":beta\")"
        settingsFile.writeText(settingsContent)

        File(testProjectDir.root, "zeta").mkdirs()
        File(testProjectDir.root, "beta").mkdirs()

        val result = GradleRunner.create()
            .withProjectDir(testProjectDir.root)
            .withArguments("createLibraryModule", "--name=alpha")
            .withPluginClasspath()
            .build()
        assertEquals(TaskOutcome.SUCCESS, result.task(":createLibraryModule")?.outcome)

        settingsContent = settingsFile.readText()
        val includeStatements = settingsContent.lines()
            .filter { it.contains("include(") }
            .map { it.trim() }
            .filter { it.isNotEmpty() }

        assertEquals(listOf(
            "include(\":alpha\")",
            "include(\":beta\")",
            "include(\":zeta\")"
        ), includeStatements)
    }
}
