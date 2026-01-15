# Changelog

All notable changes to the "Terminal Paste Image for JetBrains IDEs" plugin will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [0.0.3] - 2026-01-15

### Fixed
- Images now save to the actual project directory instead of IDE cache folder
- Added VFS (Virtual File System) refresh to make images immediately visible in project explorer without manual refresh
- Corrected project path resolution to use `project.baseDir.path` instead of `project.basePath`

### Improved
- Better support for remote development environments (WSL, Remote SSH, Dev Containers)
- Images are now correctly saved in remote project directories

## [0.0.2] - 2026-01-15

### Changed
- Extended plugin compatibility to support builds up to 253.* (2025.3.x)
- Updated maximum IDE version from 243.* to 253.* to support newer JetBrains IDE versions including WebStorm 2025.3

### Technical
- Updated `pluginUntilBuild` from 243.* to 253.* in gradle.properties
- Updated compatibility documentation to reflect new version support range

## [0.0.1] - 2024-12-XX

### Added
- Initial release of Terminal Paste Image plugin for JetBrains IDEs
- Zero configuration cross-platform support (Windows, macOS, Linux, WSL/WSL2)
- Automatic clipboard image detection and saving
- Smart file management with configurable folder (default: `.cp-images/`)
- Keyboard shortcut support: `Ctrl+Alt+V` (Windows/Linux) or `Cmd+Alt+V` (macOS)
- Terminal focus awareness
- Multiple image format support (PNG and other common formats)
- Smart Git integration with automatic .gitignore management
- Configurable image folder name
- Automatic cleanup to keep only recent images (default: 10 images)
- Timestamp-based file naming
- Claude Code AI assistant optimized workflow
- GitHub Actions workflows for automated releases and CI builds

### Technical
- Built with Kotlin and JetBrains Plugin SDK
- Compatible with JetBrains IDE versions 2023.3 (Build 233) and higher
- Java 17 or higher requirement (bundled with JetBrains IDEs)
- Native Java AWT image handling for all platforms
