# JetBrains Plugin Conversion - Developer Guide

This document explains the conversion from VSCode extension to JetBrains plugin and how to build and test it.

## What Was Converted

### From VSCode Extension to JetBrains Plugin

**VSCode Extension (TypeScript)**:
- `package.json` - Extension manifest
- `src/extension.ts` - Main extension code
- `tsconfig.json` - TypeScript configuration
- Uses VSCode API and Node.js libraries

**JetBrains Plugin (Kotlin/Java)**:
- `build.gradle.kts` - Gradle build configuration
- `settings.gradle.kts` - Gradle settings
- `gradle.properties` - Plugin properties
- `src/main/resources/META-INF/plugin.xml` - Plugin manifest
- `src/main/kotlin/**/*.kt` - Kotlin source code
- Uses IntelliJ Platform API and Java AWT

## Key Changes

### 1. Build System
- **Before**: npm/package.json
- **After**: Gradle with Kotlin DSL

### 2. Language
- **Before**: TypeScript
- **After**: Kotlin (runs on JVM)

### 3. Clipboard Handling
- **Before**: Platform-specific commands (PowerShell, xclip, pngpaste)
- **After**: Java AWT Toolkit (cross-platform, built-in)

### 4. Settings
- **Before**: JSON configuration in package.json
- **After**: PersistentStateComponent with Swing UI

### 5. Actions
- **Before**: Command registration in package.json
- **After**: AnAction classes registered in plugin.xml

## Project Structure

```
terminal-paste-image-jetbrains/
├── build.gradle.kts                     # Gradle build script
├── settings.gradle.kts                  # Gradle settings
├── gradle.properties                    # Plugin properties
├── gradlew                              # Gradle wrapper script
├── gradle/
│   └── wrapper/
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── src/
│   └── main/
│       ├── kotlin/com/github/faisalhussain95/terminalpaste/
│       │   ├── actions/
│       │   │   └── PasteImageAction.kt         # Main action
│       │   ├── settings/
│       │   │   ├── PasteImageSettings.kt       # Settings storage
│       │   │   └── PasteImageConfigurable.kt   # Settings UI
│       │   └── utils/
│       │       ├── ClipboardUtils.kt           # Clipboard operations
│       │       ├── ImageManager.kt             # Image cleanup
│       │       └── GitIgnoreManager.kt         # Git integration
│       └── resources/
│           └── META-INF/
│               └── plugin.xml                  # Plugin manifest
└── README.md
```

## How to Build

### Prerequisites
- JDK 17 or higher
- Internet connection (for downloading dependencies)

### Building the Plugin

```bash
# On Linux/macOS
./gradlew build

# On Windows
gradlew.bat build
```

The built plugin will be in `build/distributions/*.zip`

### Running in Development Mode

```bash
# On Linux/macOS
./gradlew runIde

# On Windows
gradlew.bat runIde
```

This will launch a development instance of IntelliJ IDEA with your plugin installed.

## How to Install

### Method 1: From Disk
1. Build the plugin (see above)
2. Open IntelliJ IDEA, PyCharm, or any JetBrains IDE
3. Go to Settings/Preferences → Plugins
4. Click the gear icon → Install Plugin from Disk
5. Select the `.zip` file from `build/distributions/`
6. Restart the IDE

### Method 2: From Marketplace (once published)
1. Open Settings/Preferences → Plugins
2. Search for "Terminal Paste Image"
3. Click Install
4. Restart the IDE

## How to Use

1. Copy an image to your clipboard (screenshot, image file, etc.)
2. Open a terminal in your JetBrains IDE
3. Press `Ctrl+Alt+V` (Windows/Linux) or `Cmd+Alt+V` (macOS)
4. The image is saved to `.cp-images/` and the path is typed into the terminal

## Configuration

Access settings via: Settings/Preferences → Tools → Terminal Paste Image

Options:
- **Folder Name**: Where to save images (default: `.cp-images`)
- **Auto .gitignore**: Automatically add folder to .gitignore (default: enabled)
- **Maximum Images**: How many recent images to keep (default: 10)

## Key Features

✅ **Cross-Platform**: Works on Windows, macOS, and Linux
✅ **Pure Java**: No external dependencies for clipboard (uses Java AWT)
✅ **Smart Cleanup**: Automatically removes old images
✅ **Git Integration**: Auto-updates .gitignore
✅ **Configurable**: Fully customizable via settings UI
✅ **Keyboard Shortcut**: Ctrl+Alt+V / Cmd+Alt+V

## Compatibility

- **Minimum IDE Version**: 2023.3 (Build 233)
- **Maximum IDE Version**: 243.* (2024.3.x)
- **Supported IDEs**: All JetBrains IDEs (IntelliJ IDEA, PyCharm, WebStorm, GoLand, etc.)

## Publishing to JetBrains Marketplace

To publish your plugin:

1. Sign up at https://plugins.jetbrains.com/
2. Build the plugin: `./gradlew buildPlugin`
3. Upload the ZIP from `build/distributions/`
4. Fill in plugin details and submit for review

Or use Gradle task (requires token):
```bash
export PUBLISH_TOKEN="your-token-here"
./gradlew publishPlugin
```

## Troubleshooting

### Build Fails with Network Error
The build requires internet access to download IntelliJ Platform SDK. Ensure you have a stable connection.

### Plugin Not Loading
Check IDE compatibility in `build.gradle.kts`:
- `sinceBuild = "233"` (minimum version)
- `untilBuild = "243.*"` (maximum version)

### Terminal Insertion Not Working
The terminal integration API varies between IDE versions. If insertion fails, the plugin will show a notification with the file path.

## Contributing

Contributions welcome! The codebase is now in Kotlin and follows JetBrains plugin development best practices.

## License

MIT License - Same as the original VSCode extension
