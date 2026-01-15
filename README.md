# Terminal Paste Image for JetBrains IDEs

![Demo](assets/demo.gif)

A JetBrains plugin that seamlessly pastes clipboard images directly into your terminal, automatically saving them to your project and inserting the file path. Perfect for Claude Code users and any workflow that requires quick image sharing in terminal environments.

**Compatible with IntelliJ IDEA, PyCharm, WebStorm, GoLand, and all JetBrains IDEs**

**⭐ If this plugin saves you time and makes your workflow smoother, please consider starring the repository! Your support means the world to us and helps the project grow. ⭐**

## ☕ Support

If this extension helps you, consider supporting the development:

[![Buy Me A Coffee](https://img.shields.io/badge/Buy%20Me%20A%20Coffee-☕-orange.svg?style=flat-square)](https://buymeacoffee.com/doonfrs)

**Your support helps maintain and improve this extension!**

## 🚀 Features

- **Zero Configuration Required** - Works out of the box across all platforms
- **Cross-Platform Support** - Windows, macOS, Linux, and WSL/WSL2 compatible
- **Smart Clipboard Detection** - Automatically detects when images are in your clipboard
- **Automatic File Management** - Saves images to configurable folder (default: `.cp-images/`) with timestamps
- **Instant Path Insertion** - Immediately inserts the relative path into your active terminal
- **Claude Code Optimized** - Perfect for sharing images with Claude Code AI assistant
- **WSL Integration** - Seamless PowerShell integration for WSL environments
- **Keyboard Shortcut** - Quick `Ctrl+Alt+V` (Windows/Linux) or `Cmd+Alt+V` (macOS)
- **Terminal Focus Aware** - Only activates when terminal is in focus
- **Multiple Image Formats** - Supports PNG and other common clipboard image formats
- **Smart Git Integration** - Automatically adds images folder to .gitignore (configurable)
- **Customizable Folder Name** - Configure where images are saved (default: `.cp-images/`)
- **Automatic Cleanup** - Keeps only recent images (default: 10) to prevent folder bloat

## 📋 Prerequisites

- JetBrains IDE (IntelliJ IDEA, PyCharm, WebStorm, GoLand, etc.) version 2023.3 or higher
- Java 17 or higher (bundled with JetBrains IDEs)
- For macOS: Images are handled natively by Java AWT
- For Linux: Images are handled natively by Java AWT  
- For Windows/WSL: Images are handled natively by Java AWT

## 🌟 Show Your Support

If you find this extension useful:

- ⭐ **Star this repository** on GitHub
- 📝 **Leave a review** on the VS Code Marketplace
- 🐛 **Report issues** or suggest features
- 💬 **Share** with other developers

## 🎯 Use Cases

### Claude Code Integration
Perfect for sharing screenshots, diagrams, or images with Claude Code AI assistant:
1. Copy any image to clipboard (screenshot, design, diagram)
2. Press `Ctrl+Alt+V` in terminal
3. Image path is automatically inserted for Claude Code to reference

### General Development
- Share UI mockups or designs with team members
- Include error screenshots in bug reports
- Add visual context to code discussions
- Document visual issues or features

### Documentation
- Quickly include screenshots in documentation workflows
- Save and reference images during technical writing
- Create visual guides and tutorials


## ⚙️ Installation

### From JetBrains Marketplace
1. Open your JetBrains IDE (IntelliJ IDEA, PyCharm, etc.)
2. Go to Settings/Preferences → Plugins
3. Search for "Terminal Paste Image"
4. Click "Install"
5. Restart the IDE

### Manual Installation
1. Download the latest plugin `.zip` file from releases
2. Open Settings/Preferences → Plugins
3. Click the gear icon → Install Plugin from Disk
4. Select the downloaded file
5. Restart the IDE


## 🔧 Usage

### Basic Usage
1. Copy an image to your clipboard (screenshot, image file, etc.)
2. Focus on any terminal in your JetBrains IDE
3. Press `Ctrl+Alt+V` (Windows/Linux) or `Cmd+Alt+V` (macOS)
4. The image is saved to your configured folder and path is inserted in terminal

### Action Menu
- Open Find Action (`Ctrl+Shift+A` or `Cmd+Shift+A`)
- Type "Paste Image to Terminal"
- Execute the action


## 🛠️ Platform-Specific Setup

### All Platforms
- Works out of the box with Java AWT (bundled with JetBrains IDEs)
- No additional setup required
- Cross-platform clipboard handling is automatic


## 📁 File Organization

Images are automatically saved to:

```text
your-project/
├── .cp-images/
│   ├── pasted-image-2024-01-15T10-30-45.png
│   ├── pasted-image-2024-01-15T10-31-02.png
│   └── ...
```

Path format inserted in terminal:

```text
.cp-images/pasted-image-2024-01-15T10-30-45.png
```


## ⌨️ Keyboard Shortcuts

| Platform | Shortcut |
|----------|----------|
| Windows/Linux | `Ctrl+Alt+V` |
| macOS | `Cmd+Alt+V` |

**Note**: Shortcuts only work when terminal is focused to avoid conflicts with normal paste operations.


## ⚙️ Configuration

The plugin provides several configuration options that can be customized in your IDE settings:

### Settings

| Setting | Type | Default | Description |
|---------|------|---------|-------------|
| `Folder Name` | string | `.cp-images` | Name of the folder where pasted images will be saved |
| `Automatically add folder to .gitignore` | boolean | `true` | Automatically add the images folder to .gitignore if not present |
| `Maximum Images` | number | `10` | Maximum number of images to keep (older images are automatically deleted) |

### Accessing Settings

1. Open Settings/Preferences (`Ctrl+Alt+S` or `Cmd+,`)
2. Navigate to Tools → Terminal Paste Image
3. Modify the settings as needed

### Auto .gitignore Management

By default, the plugin will:
- Check if a `.gitignore` file exists in your project
- Verify if your images folder is already ignored
- Automatically add the folder to `.gitignore` if not present
- Add a helpful comment indicating it's for Terminal Paste Image

This behavior can be disabled in the plugin settings.

### Custom Folder Configuration

You can change the default folder name from `.cp-images` to any folder name you prefer in the plugin settings (Tools → Terminal Paste Image).

The folder will be created relative to your project root.

### Image Management

By default, the plugin keeps only the 10 most recent images to prevent folder bloat. You can adjust this in the plugin settings:

- When you paste a new image, older images beyond the limit are automatically deleted
- Images are sorted by modification time (newest kept, oldest deleted)
- Only images matching the `pasted-image-*.png` pattern are managed
- Set to a higher number if you need to keep more images
- Minimum value is 1, maximum is 100

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request. For major changes, please open an issue first to discuss what you would like to change.

### Development Setup
1. Clone this repository
2. Open in IntelliJ IDEA
3. The project uses Gradle, dependencies will be downloaded automatically
4. Run the plugin: Use "Run Plugin" from the Gradle tasks or press Shift+F10
5. Test your changes in the IDE development instance

### Building
```bash
./gradlew build
```

### Running
```bash
./gradlew runIde
```


## 📝 Changelog

### v0.0.1
- Initial release
- Cross-platform clipboard image detection
- Automatic image saving with timestamps
- Terminal path insertion
- WSL/WSL2 support
- Keyboard shortcut integration
- Configurable folder name (default: `.cp-images/`)
- Auto .gitignore management
- Smart git integration settings
- Automatic image cleanup (keeps last 10 images by default)
- Configurable maximum image count (1-100)


## 🐛 Known Issues

- Terminal text insertion may vary based on IDE version and terminal type
- Some terminal emulators may require manual path entry

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 💬 Connect

- 🐙 **GitHub**: [@FaisalHussain95](https://github.com/FaisalHussain95)
- 📦 **Original VSCode Extension**: [@doonfrs](https://github.com/doonfrs)

**Made with ❤️ for the JetBrains and Claude Code community**
