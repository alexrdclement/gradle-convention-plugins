import org.gradle.api.Plugin
import org.gradle.api.Project

class MavenPublishConventionPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.pluginManager.apply("com.vanniktech.maven.publish")
    }
}
