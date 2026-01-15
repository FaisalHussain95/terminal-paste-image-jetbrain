# GitHub Actions Workflows

This directory contains automated workflows for the Terminal Paste Image JetBrains plugin.

## Workflows

### 1. Release Workflow (`release.yml`)

**Trigger**: Pushes to `main` branch or version tags (`v*`)

**What it does**:
- Builds the plugin using Gradle
- Extracts version from `gradle.properties`
- Creates a GitHub release with the version tag
- Uploads the plugin ZIP file as a release asset
- Uploads build artifacts for 30 days retention

**Usage**:
When you merge a PR to `main`, this workflow will automatically:
1. Build the plugin
2. Create a release named `v{version}` (e.g., `v0.0.2`)
3. Attach the plugin ZIP file to the release
4. Make it available for download

**Manual Release**:
You can also trigger a release manually by creating and pushing a tag:
```bash
git tag v0.0.2
git push origin v0.0.2
```

### 2. Build and Test Workflow (`build.yml`)

**Trigger**: Pull requests to `main` branch and pushes to other branches

**What it does**:
- Builds the plugin to verify compilation
- Runs plugin verification checks
- Uploads build artifacts for 7 days retention

**Usage**:
This workflow runs automatically on every PR to ensure:
- The code compiles successfully
- The plugin structure is valid
- Build artifacts are available for review

## Release Process

1. **Update Version**: Edit `gradle.properties` and update `pluginVersion`
   ```properties
   pluginVersion = 0.0.2
   ```

2. **Merge to Main**: Create and merge a PR to `main` branch
   - The build workflow will run on the PR
   - After merge, the release workflow will trigger

3. **Automatic Release**: The workflow will:
   - Build the plugin
   - Create release `v0.0.2`
   - Upload `terminal-paste-image-jetbrains-0.0.2.zip`

4. **Download**: Users can download from:
   - GitHub Releases page
   - Direct link: `https://github.com/{owner}/{repo}/releases/latest`

## Artifacts

Both workflows upload artifacts:

- **Release workflow**: Plugin ZIP available for 30 days as workflow artifact + permanent release asset
- **Build workflow**: Plugin ZIP and reports available for 7 days

Access artifacts from:
- Actions tab → Workflow run → Artifacts section (bottom of page)
- Releases tab → Specific release → Assets section

## Requirements

- JDK 17 (automatically set up by workflow)
- Gradle wrapper (`gradlew`) must be executable
- Valid `gradle.properties` with `pluginVersion` and `pluginName`

## Troubleshooting

### Workflow fails on "Build plugin"
- Check that `build.gradle.kts` is valid
- Verify dependencies are accessible
- Review build logs in Actions tab

### Release creation fails
- Ensure version tag doesn't already exist
- Check GITHUB_TOKEN has `contents: write` permission
- Verify `gradle.properties` format is correct

### ZIP file not found
- Check that `buildPlugin` task outputs to `build/distributions/`
- Verify plugin name and version match expected filename
- Review build logs for errors

## Next Steps

After setting up these workflows:
1. Update plugin version in `gradle.properties`
2. Merge changes to `main`
3. Check the Actions tab to see the workflow run
4. Download the release from Releases tab
5. Test the plugin in a JetBrains IDE
6. Publish to JetBrains Marketplace when ready

For manual publishing to JetBrains Marketplace, see [DEVELOPMENT.md](../../DEVELOPMENT.md).
