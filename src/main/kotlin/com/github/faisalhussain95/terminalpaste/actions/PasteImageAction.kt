package com.github.faisalhussain95.terminalpaste.actions

import com.github.faisalhussain95.terminalpaste.settings.PasteImageSettings
import com.github.faisalhussain95.terminalpaste.utils.ClipboardUtils
import com.github.faisalhussain95.terminalpaste.utils.GitIgnoreManager
import com.github.faisalhussain95.terminalpaste.utils.ImageManager
import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindowManager
import java.io.File

class PasteImageAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: run {
            showNotification(null, "No project found", NotificationType.ERROR)
            return
        }

        val projectPath = project.basePath ?: run {
            showNotification(project, "No project path found", NotificationType.ERROR)
            return
        }

        try {
            pasteImageFromClipboard(project, projectPath)
        } catch (ex: Exception) {
            showNotification(
                project,
                "Failed to paste image: ${ex.message}",
                NotificationType.ERROR
            )
            ex.printStackTrace()
        }
    }

    override fun update(e: AnActionEvent) {
        // Enable action only when a project is available
        // Ideally, we'd check for terminal focus, but that's complex in IntelliJ
        // The action will work from anywhere and insert into the active terminal
        e.presentation.isEnabled = e.project != null
    }

    private fun pasteImageFromClipboard(project: Project, projectPath: String) {
        val settings = PasteImageSettings.instance
        val folderName = settings.folderName
        val autoGitIgnore = settings.autoGitIgnore
        val maxImages = settings.maxImages

        // Create images directory
        val imagesDir = File(projectPath, folderName)
        if (!imagesDir.exists()) {
            imagesDir.mkdirs()
        }

        // Check if there's an image in clipboard
        if (!ClipboardUtils.hasImageInClipboard()) {
            showNotification(project, "No image found in clipboard", NotificationType.WARNING)
            return
        }

        // Get image from clipboard
        val image = ClipboardUtils.getImageFromClipboard() ?: run {
            showNotification(project, "Failed to get image from clipboard", NotificationType.ERROR)
            return
        }

        // Generate filename and save image
        val imageName = ClipboardUtils.generateImageFileName()
        val imageFile = File(imagesDir, imageName)
        
        if (!ClipboardUtils.saveImage(image, imageFile)) {
            showNotification(project, "Failed to save image", NotificationType.ERROR)
            return
        }

        // Update .gitignore if enabled
        if (autoGitIgnore) {
            GitIgnoreManager.updateGitIgnore(File(projectPath), folderName)
        }

        // Clean up old images
        ImageManager.cleanupOldImages(imagesDir, maxImages)

        // Get relative path
        val relativePath = ImageManager.getRelativePath(projectPath, imageFile.absolutePath)

        // Insert path into terminal (or copy to clipboard)
        insertPathInTerminal(project, relativePath)
    }

    private fun insertPathInTerminal(project: Project, path: String) {
        try {
            // Get the terminal tool window
            val terminalWindow = ToolWindowManager.getInstance(project).getToolWindow("Terminal")
            
            if (terminalWindow == null) {
                showNotification(project, "Terminal tool window not found. Path: $path", NotificationType.WARNING)
                return
            }

            // For now, we'll use a simpler approach: copy to clipboard and show notification
            // The JetBrains Terminal API is complex and varies between versions
            // This approach is more reliable across different IDE versions
            val clipboard = java.awt.Toolkit.getDefaultToolkit().systemClipboard
            val stringSelection = java.awt.datatransfer.StringSelection(path)
            clipboard.setContents(stringSelection, null)
            
            showNotification(
                project,
                "Image saved! Path copied to clipboard: $path (Paste with Ctrl+V / Cmd+V)",
                NotificationType.INFORMATION
            )
        } catch (e: Exception) {
            // If clipboard copy fails, still show the path to user
            showNotification(
                project,
                "Image saved to: $path",
                NotificationType.INFORMATION
            )
            println("Clipboard copy error: ${e.message}")
            e.printStackTrace()
        }
    }

    private fun showNotification(project: Project?, message: String, type: NotificationType) {
        NotificationGroupManager.getInstance()
            .getNotificationGroup("Terminal Paste Image")
            .createNotification(message, type)
            .notify(project)
    }
}
