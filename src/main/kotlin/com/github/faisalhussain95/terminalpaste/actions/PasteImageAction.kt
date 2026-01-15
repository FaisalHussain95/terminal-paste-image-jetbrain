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
import org.jetbrains.plugins.terminal.TerminalToolWindowManager
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

        // Insert path into terminal
        insertPathInTerminal(project, relativePath)

        // Show success notification
        showNotification(
            project,
            "Image saved and path inserted: $relativePath",
            NotificationType.INFORMATION
        )
    }

    private fun insertPathInTerminal(project: Project, path: String) {
        try {
            // Get the terminal tool window
            val terminalWindow = ToolWindowManager.getInstance(project).getToolWindow("Terminal")
            
            if (terminalWindow == null) {
                showNotification(project, "Terminal tool window not found", NotificationType.WARNING)
                return
            }

            // Get the terminal manager
            val terminalManager = TerminalToolWindowManager.getInstance(project)
            
            // Get the currently selected content (active terminal)
            val content = terminalWindow.contentManager.selectedContent
            if (content != null) {
                // Try to execute the command in the terminal
                // This will type the path into the active terminal session
                val terminalWidget = terminalManager.getTerminalWidget(content)
                if (terminalWidget != null) {
                    // Type the path without executing (no newline)
                    terminalWidget.terminalTextBuffer.typeString(path)
                } else {
                    // Fallback: just show the path
                    showNotification(
                        project,
                        "Could not insert into terminal. Path: $path",
                        NotificationType.INFORMATION
                    )
                }
            } else {
                showNotification(
                    project,
                    "No active terminal session. Path: $path",
                    NotificationType.INFORMATION
                )
            }
        } catch (e: Exception) {
            // If terminal insertion fails, still provide the path to user
            showNotification(
                project,
                "Terminal insertion failed. Path copied: $path",
                NotificationType.WARNING
            )
            println("Terminal insertion error: ${e.message}")
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
