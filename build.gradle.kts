import org.shipkit.changelog.GenerateChangelogTask
import org.shipkit.github.release.GithubReleaseTask

plugins {
    alias(alexrdclementPluginLibs.plugins.kotlin.multiplatform) apply false
    alias(alexrdclementPluginLibs.plugins.android.kotlin.multiplatform.library) apply false
    alias(alexrdclementPluginLibs.plugins.maven.publish) apply false

    alias(alexrdclementPluginLibs.plugins.shipkit.autoversion) apply true
    alias(alexrdclementPluginLibs.plugins.shipkit.changelog) apply true
    alias(alexrdclementPluginLibs.plugins.shipkit.githubrelease) apply true
}

tasks.named<GenerateChangelogTask>("generateChangelog") {
    previousRevision = project.extra["shipkit-auto-version.previous-tag"] as String
    githubToken = System.getenv("GITHUB_TOKEN")
    repository = "alexrdclement/gradle-plugins"
}

tasks.named<GithubReleaseTask>("githubRelease") {
    dependsOn(tasks.named("generateChangelog"))
    val isSnapshot = version.toString().endsWith("SNAPSHOT")
    enabled = !isSnapshot
    repository = "alexrdclement/gradle-plugins"
    changelog = tasks.named("generateChangelog").get().outputs.files.singleFile
    githubToken = System.getenv("GITHUB_TOKEN")
    newTagRevision = System.getenv("GITHUB_SHA")
}
