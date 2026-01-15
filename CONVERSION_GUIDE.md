# VSCode Extension → JetBrains Plugin: What Changed

This document shows the direct comparison between the original VSCode extension and the new JetBrains plugin.

## Side-by-Side Comparison

### Project Structure

| VSCode Extension | JetBrains Plugin |
|-----------------|------------------|
| `package.json` | `build.gradle.kts` + `plugin.xml` |
| `tsconfig.json` | Kotlin/Java configuration in Gradle |
| `src/extension.ts` | `src/main/kotlin/**/*.kt` (7 files) |
| `node_modules/` | Gradle dependencies (auto-downloaded) |
| Build with `npm` | Build with `./gradlew` |

### Core Functionality Mapping

#### 1. Extension Entry Point

**VSCode (`src/extension.ts`)**:
```typescript
export function activate(context: vscode.ExtensionContext) {
    const disposable = vscode.commands.registerCommand(
        'terminal-paste-image.pasteImage', 
        async () => {
            await pasteImageFromClipboard();
        }
    );
    context.subscriptions.push(disposable);
}
```

**JetBrains (`PasteImageAction.kt`)**:
```kotlin
class PasteImageAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val projectPath = project.basePath ?: return
        pasteImageFromClipboard(project, projectPath)
    }
}
```

#### 2. Clipboard Detection

**VSCode (Platform-specific)**:
```typescript
// Windows/WSL
command = 'powershell -command "Get-Clipboard -Format Image"';

// macOS
command = 'osascript -e "clipboard info" | grep -q "«class PNGf»"';

// Linux
command = 'xclip -selection clipboard -t TARGETS -o | grep -i image';
```

**JetBrains (Cross-platform Java AWT)**:
```kotlin
fun hasImageInClipboard(): Boolean {
    val clipboard = Toolkit.getDefaultToolkit().systemClipboard
    return clipboard.isDataFlavorAvailable(DataFlavor.imageFlavor)
}

fun getImageFromClipboard(): BufferedImage? {
    val clipboard = Toolkit.getDefaultToolkit().systemClipboard
    val transferable = clipboard.getContents(null)
    return transferable?.getTransferData(DataFlavor.imageFlavor) as? BufferedImage
}
```

**Advantage**: No external dependencies (xclip, pngpaste, PowerShell), works natively on all platforms.

#### 3. Image Saving

**VSCode**:
```typescript
// Different commands per platform
case 'win32':
    command = `powershell -command "...$img.Save('${imagePath}')"`;
case 'darwin':
    command = `pngpaste "${imagePath}"`;
case 'linux':
    command = `xclip -selection clipboard -t image/png -o > "${imagePath}"`;
```

**JetBrains**:
```kotlin
fun saveImage(image: BufferedImage, file: File): Boolean {
    file.parentFile?.mkdirs()
    return ImageIO.write(image, "png", file)
}
```

**Advantage**: Unified API, no platform detection needed.

#### 4. Settings/Configuration

**VSCode (`package.json`)**:
```json
"configuration": {
  "properties": {
    "terminalPasteImage.folderName": {
      "type": "string",
      "default": ".cp-images"
    }
  }
}
```

Access in code:
```typescript
const config = vscode.workspace.getConfiguration('terminalPasteImage');
const folderName = config.get<string>('folderName', '.cp-images');
```

**JetBrains (`PasteImageSettings.kt` + `PasteImageConfigurable.kt`)**:
```kotlin
@State(name = "...", storages = [Storage("TerminalPasteImagePlugin.xml")])
class PasteImageSettings : PersistentStateComponent<PasteImageSettings> {
    var folderName: String = ".cp-images"
    var autoGitIgnore: Boolean = true
    var maxImages: Int = 10
    
    companion object {
        val instance: PasteImageSettings
            get() = ApplicationManager.getApplication()
                .getService(PasteImageSettings::class.java)
    }
}
```

Access in code:
```kotlin
val settings = PasteImageSettings.instance
val folderName = settings.folderName
```

**Settings UI**:
- VSCode: JSON schema → automatic UI
- JetBrains: Custom Swing UI (more control, better UX)

#### 5. Terminal Integration

**VSCode**:
```typescript
const activeTerminal = vscode.window.activeTerminal;
activeTerminal.sendText(imagePath, false);
```

**JetBrains**:
```kotlin
val terminalWindow = ToolWindowManager.getInstance(project)
    .getToolWindow("Terminal")
val content = terminalWindow?.contentManager?.selectedContent
val terminalWidget = terminalManager.getTerminalWidget(content)
terminalWidget?.terminalTextBuffer?.typeString(path)
```

**Note**: JetBrains terminal API is more complex but provides finer control.

#### 6. Image Cleanup

**Both implementations are similar**:
- Read files in directory
- Sort by modification time
- Keep N most recent
- Delete older files

The logic is nearly identical, just ported from TypeScript to Kotlin.

#### 7. GitIgnore Management

**Both implementations are similar**:
- Check if `.gitignore` exists
- Read content
- Check if folder is already ignored
- Append if not present

The logic is nearly identical, just ported from TypeScript to Kotlin.

### Keyboard Shortcuts

**VSCode (`package.json`)**:
```json
"keybindings": [{
  "command": "terminal-paste-image.pasteImage",
  "key": "ctrl+alt+v",
  "mac": "cmd+alt+v",
  "when": "terminalFocus"
}]
```

**JetBrains (`plugin.xml`)**:
```xml
<action id="com.github.faisalhussain95.terminalpaste.PasteImage"
        class="...PasteImageAction">
  <keyboard-shortcut keymap="$default" first-keystroke="ctrl alt V"/>
  <keyboard-shortcut keymap="Mac OS X" first-keystroke="meta alt V"/>
</action>
```

### Dependencies

**VSCode**:
- `@types/vscode`: VSCode API types
- `@types/node`: Node.js API types
- `typescript`: TypeScript compiler
- `clipboardy`: Cross-platform clipboard library (npm package)

**JetBrains**:
- IntelliJ Platform SDK (provided by Gradle plugin)
- Kotlin standard library
- Java AWT (built-in, no external deps)

**Advantage**: JetBrains plugin has ZERO external dependencies for clipboard operations.

### File Size Comparison

| Metric | VSCode | JetBrains |
|--------|---------|-----------|
| Source Files | 1 TS file | 7 Kotlin files |
| Lines of Code | ~300 lines | ~500 lines (more modular) |
| Dependencies | npm packages | None (built-in APIs) |
| Build Output | JavaScript bundle | Java bytecode (.jar) |

### Installation

**VSCode**:
1. Download `.vsix` file
2. Install via Extensions panel
3. Reload VS Code

**JetBrains**:
1. Download `.zip` file
2. Install via Plugins → Install from Disk
3. Restart IDE

### Compatibility

**VSCode**:
- Requires: VS Code 1.74.0+
- Platform deps: PowerShell (Win), xclip (Linux), pngpaste (macOS)

**JetBrains**:
- Requires: Any JetBrains IDE 2023.3+
- Platform deps: None (Java AWT is cross-platform)
- Works in: IntelliJ IDEA, PyCharm, WebStorm, GoLand, PhpStorm, CLion, etc.

## Key Improvements in JetBrains Version

✅ **No External Dependencies**: Uses Java AWT for clipboard operations
✅ **Broader IDE Support**: Works across all JetBrains IDEs
✅ **Better Modularity**: Code split into 7 well-organized files
✅ **Type Safety**: Kotlin's null-safety prevents runtime errors
✅ **Native UI**: Custom Swing settings panel with better UX
✅ **Stronger Typing**: Compile-time type checking
✅ **Better Error Handling**: JetBrains notification system

## What Stayed the Same

✓ Core functionality (paste, save, cleanup)
✓ Configuration options
✓ Keyboard shortcuts
✓ Default folder name (`.cp-images`)
✓ Image file naming format
✓ GitIgnore auto-update logic
✓ Max images limit (default 10)

## Migration Path for Users

If you were using the VSCode extension:
1. Settings are separate (need to reconfigure in JetBrains IDE)
2. Images in `.cp-images/` work the same way
3. Keyboard shortcuts are identical
4. No data migration needed

## Developer Notes

### Why Kotlin Instead of Java?
- More concise syntax
- Null-safety built-in
- Better interop with Java libraries
- JetBrains' preferred language
- Modern language features

### Why No External Clipboard Library?
- Java AWT's `Toolkit.getDefaultToolkit().systemClipboard` is cross-platform
- No need for clipboardy, xclip, pngpaste, PowerShell
- Reduces plugin size
- No version conflicts
- Native platform integration

### Why Gradle Instead of Maven?
- Modern build tool
- Better IDE integration
- Kotlin DSL is more readable
- Official JetBrains recommendation
- Faster builds with caching

## Next Steps

To publish to JetBrains Marketplace:
1. Test thoroughly on Windows, macOS, Linux
2. Test in multiple JetBrains IDEs
3. Add icon (256x256 PNG)
4. Update vendor information
5. Build plugin: `./gradlew buildPlugin`
6. Upload to https://plugins.jetbrains.com/
7. Submit for review

## Questions?

See `DEVELOPMENT.md` for detailed build and test instructions.
