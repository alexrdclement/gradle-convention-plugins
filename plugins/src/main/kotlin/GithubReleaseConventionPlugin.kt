import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.plugin.extraProperties

open class GithubReleaseExtension {
    var githubToken: String? = null
    var repository: String? = null
    var newTagRevision: String? = null
    var enabled: Boolean = true
}

class GithubReleaseConventionPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.pluginManager.apply("org.shipkit.shipkit-auto-version")
        project.pluginManager.apply("org.shipkit.shipkit-changelog")
        project.pluginManager.apply("org.shipkit.shipkit-github-release")

        val extension = project.extensions.create("githubRelease", GithubReleaseExtension::class.java)
        project.afterEvaluate {
            val changelogTask = project.tasks.findByName("generateChangelog")
            val previousRevision = project.extraProperties["shipkit-auto-version.previous-tag"] as String
            changelogTask?.let { task ->
                task.setProperty("previousRevision", previousRevision)
                extension.githubToken?.let { task.setProperty("githubToken", it) }
                extension.repository?.let { task.setProperty("repository", it) }
            } ?: return@afterEvaluate

            val githubReleaseTask = project.tasks.findByName("githubRelease")
            githubReleaseTask?.let { task ->
                task.dependsOn(changelogTask)
                task.setProperty("enabled", extension.enabled)
                extension.repository?.let { task.setProperty("repository", it) }
                // changelog property is set automatically by Shipkit
                extension.githubToken?.let { task.setProperty("githubToken", it) }
                extension.newTagRevision?.let { task.setProperty("newTagRevision", it) }
            }
        }
    }
}
