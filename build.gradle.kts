plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.9.21"
    id("org.jetbrains.intellij") version "1.17.0"
}

group = "com.github.faisalhussain95"
version = "0.0.3"

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
        untilBuild.set("253.*")
        version.set(project.version.toString())
        changeNotes.set("""
            <h3>0.0.3</h3>
            <ul>
                <li>Fixed: Images now save to the actual project directory instead of IDE cache folder</li>
                <li>Fixed: Added VFS refresh to make images immediately visible in project explorer</li>
                <li>Improved: Better support for remote development environments (WSL, Remote SSH, Dev Containers)</li>
            </ul>
            <h3>0.0.2</h3>
            <ul>
                <li>Extended plugin compatibility to support builds up to 253.* (2025.3.x)</li>
                <li>Updated maximum IDE version to support newer JetBrains IDE versions including WebStorm 2025.3</li>
            </ul>
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
