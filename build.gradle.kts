plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.9.21"
    id("org.jetbrains.intellij") version "1.17.0"
}

group = "com.github.faisalhussain95"
version = "0.0.1"

repositories {
    mavenCentral()
}

// Configure Gradle IntelliJ Plugin
intellij {
    version.set("2023.3")
    type.set("IC") // Target IDE Platform: IntelliJ Community
    plugins.set(listOf("terminal"))
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }

    patchPluginXml {
        sinceBuild.set("233")
        untilBuild.set("243.*")
        version.set(project.version.toString())
        changeNotes.set("""
            <h3>0.0.1</h3>
            <ul>
                <li>Initial release</li>
                <li>Cross-platform clipboard image detection</li>
                <li>Automatic image saving with timestamps</li>
                <li>Terminal path insertion</li>
                <li>WSL/WSL2 support</li>
                <li>Keyboard shortcut integration (Ctrl+Alt+V / Cmd+Alt+V)</li>
                <li>Configurable folder name (default: .cp-images/)</li>
                <li>Auto .gitignore management</li>
                <li>Automatic image cleanup (keeps last 10 images by default)</li>
            </ul>
        """.trimIndent())
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }
}
