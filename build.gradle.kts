import org.shipkit.changelog.GenerateChangelogTask
import org.shipkit.github.release.GithubReleaseTask

plugins {
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.android.kotlin.multiplatform.library) apply false
    alias(libs.plugins.maven.publish) apply false

    alias(libs.plugins.shipkit.autoversion) apply true
    alias(libs.plugins.shipkit.changelog) apply true
    alias(libs.plugins.shipkit.githubrelease) apply true
}

tasks.named<GenerateChangelogTask>("generateChangelog") {
    previousRevision = project.extra["shipkit-auto-version.previous-tag"] as String
    githubToken = System.getenv("GITHUB_TOKEN")
    repository = "alexrdclement/gradle-convention-plugins"
}

tasks.named<GithubReleaseTask>("githubRelease") {
    dependsOn(tasks.named("generateChangelog"))
    val isSnapshot = version.toString().endsWith("SNAPSHOT")
    enabled = !isSnapshot
    repository = "alexrdclement/gradle-convention-plugins"
    changelog = tasks.named("generateChangelog").get().outputs.files.singleFile
    githubToken = System.getenv("GITHUB_TOKEN")
    newTagRevision = System.getenv("GITHUB_SHA")
}
