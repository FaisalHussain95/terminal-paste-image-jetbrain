package com.github.faisalhussain95.terminalpaste.utils

import java.io.File

object GitIgnoreManager {
    /**
     * Update .gitignore to include the images folder
     * @param projectRoot The root directory of the project
     * @param folderName The name of the folder to add to .gitignore
     */
    fun updateGitIgnore(projectRoot: File, folderName: String) {
        val gitignoreFile = File(projectRoot, ".gitignore")

        try {
            // Check if .gitignore exists
            if (!gitignoreFile.exists()) {
                println("No .gitignore file found, skipping auto-update")
                return
            }

            // Read .gitignore content
            val gitignoreContent = gitignoreFile.readText()

            // Check if the folder is already in .gitignore
            val folderPattern = Regex.escape(folderName)
            val patterns = listOf(
                Regex("^$folderPattern/?$", RegexOption.MULTILINE),      // Exact match
                Regex("^$folderPattern/\\*$", RegexOption.MULTILINE),     // With wildcard
                Regex("^\\*\\*/$folderPattern/?$", RegexOption.MULTILINE) // Recursive match
            )

            val alreadyIgnored = patterns.any { it.containsMatchIn(gitignoreContent) }

            if (!alreadyIgnored) {
                // Add the folder to .gitignore
                val newLine = if (gitignoreContent.endsWith("\n")) "" else "\n"
                val updatedContent = "$gitignoreContent$newLine\n# Terminal Paste Image folder\n/$folderName\n"

                gitignoreFile.writeText(updatedContent)
                println("Added $folderName/ to .gitignore")
            } else {
                println("$folderName is already in .gitignore")
            }
        } catch (e: Exception) {
            println("Error updating .gitignore: ${e.message}")
            // Don't show error to user as this is a convenience feature
        }
    }
}
