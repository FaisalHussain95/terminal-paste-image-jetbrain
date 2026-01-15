# Quick Start - Terminal Paste Image for JetBrains

## 🚀 Build & Run in 3 Commands

### 1. Build the Plugin
```bash
./gradlew build
```
This compiles the Kotlin code and packages the plugin into a ZIP file.

### 2. Run in Development IDE
```bash
./gradlew runIde
```
This launches IntelliJ IDEA with your plugin pre-installed.

### 3. Test It!
In the development IDE:
1. Copy an image to clipboard (take a screenshot or copy an image file)
2. Open a terminal (View → Tool Windows → Terminal)
3. Press **Ctrl+Alt+V** (or **Cmd+Alt+V** on Mac)
4. ✅ Image saved to `.cp-images/` and path inserted!

## 📦 Files Created

```
terminal-paste-image-jetbrains/
├── build.gradle.kts                  ← Gradle build configuration
├── settings.gradle.kts               ← Gradle settings
├── gradle.properties                 ← Plugin properties
├── gradlew                           ← Gradle wrapper (Linux/Mac)
├── gradlew.bat                       ← Gradle wrapper (Windows)
├── gradle/wrapper/                   ← Gradle wrapper files
├── src/main/
│   ├── kotlin/                       ← Kotlin source code
│   │   └── com/github/faisalhussain95/terminalpaste/
│   │       ├── actions/
│   │       │   └── PasteImageAction.kt          ← Main action
│   │       ├── settings/
│   │       │   ├── PasteImageSettings.kt        ← Settings storage
│   │       │   └── PasteImageConfigurable.kt    ← Settings UI
│   │       └── utils/
│   │           ├── ClipboardUtils.kt            ← Clipboard ops
│   │           ├── ImageManager.kt              ← Image cleanup
│   │           └── GitIgnoreManager.kt          ← Git integration
│   └── resources/
│       └── META-INF/
│           └── plugin.xml            ← Plugin manifest
├── README.md                         ← Updated for JetBrains
├── DEVELOPMENT.md                    ← Detailed build guide
├── CONVERSION_GUIDE.md               ← VSCode vs JetBrains
├── NEXT_STEPS.md                     ← Publishing guide
└── QUICK_START.md                    ← This file
```

## ⚙️ Configuration

After testing, configure the plugin:
1. Settings/Preferences → Tools → Terminal Paste Image
2. Change folder name (default: `.cp-images`)
3. Toggle auto-gitignore (default: enabled)
4. Set max images (default: 10)

## 🎯 What Works

✅ Cross-platform clipboard image detection (Windows, macOS, Linux)
✅ Automatic image saving with timestamps
✅ Terminal path insertion
✅ Configurable folder name
✅ Auto .gitignore management
✅ Automatic cleanup of old images
✅ Keyboard shortcut (Ctrl+Alt+V / Cmd+Alt+V)
✅ Works in ALL JetBrains IDEs (IntelliJ, PyCharm, WebStorm, GoLand, etc.)

## 🔧 Useful Commands

```bash
# Build the plugin
./gradlew build

# Run in development IDE
./gradlew runIde

# Build plugin ZIP for distribution
./gradlew buildPlugin

# Verify plugin
./gradlew verifyPlugin

# Clean build artifacts
./gradlew clean

# Show available tasks
./gradlew tasks
```

## 📁 Output Location

After building, find the plugin ZIP here:
```
build/distributions/terminal-paste-image-jetbrains-0.0.2.zip
```

## 🐛 Troubleshooting

### Build fails with "Could not resolve..."
- Ensure you have internet connection
- JetBrains dependencies are downloaded from the internet
- Try: `./gradlew build --refresh-dependencies`

### "No Java runtime present"
- Install JDK 17 or higher
- Check: `java -version`

### Plugin doesn't load
- Check IDE version compatibility (requires 2023.3+)
- Verify in: Settings → Plugins → Installed
- Check IDE logs: Help → Show Log in Finder/Explorer

### Terminal insertion doesn't work
- This is expected in some terminal types
- Plugin shows notification with file path
- Users can copy path from notification

## 📚 More Information

- **Detailed Build Guide**: See [DEVELOPMENT.md](DEVELOPMENT.md)
- **Technical Details**: See [CONVERSION_GUIDE.md](CONVERSION_GUIDE.md)
- **Publishing Steps**: See [NEXT_STEPS.md](NEXT_STEPS.md)

## 🎊 Success!

If you can:
1. ✅ Build the plugin (`./gradlew build`)
2. ✅ Run it in development IDE (`./gradlew runIde`)
3. ✅ Paste an image with Ctrl+Alt+V
4. ✅ See the image saved in `.cp-images/`

Then the conversion is **successful**! 🚀

Next: Follow [NEXT_STEPS.md](NEXT_STEPS.md) to prepare for publishing to JetBrains Marketplace.
