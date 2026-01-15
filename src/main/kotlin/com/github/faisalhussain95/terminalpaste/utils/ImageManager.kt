package com.github.faisalhussain95.terminalpaste.utils

import java.io.File
import java.nio.file.Files
import java.nio.file.attribute.BasicFileAttributes

object ImageManager {
    /**
     * Clean up old images in the directory, keeping only the most recent ones
     * @param imagesDir The directory containing images
     * @param maxImages Maximum number of images to keep
     */
    fun cleanupOldImages(imagesDir: File, maxImages: Int) {
        if (!imagesDir.exists() || !imagesDir.isDirectory) {
            return
        }

        try {
            // Get all image files matching the pattern
            val imageFiles = imagesDir.listFiles { file ->
                file.isFile && 
                file.name.startsWith("pasted-image-") && 
                file.name.endsWith(".png")
            } ?: return

            if (imageFiles.size <= maxImages) {
                return // No cleanup needed
            }

            // Sort by modification time (newest first)
            val sortedFiles = imageFiles.sortedByDescending { file ->
                try {
                    Files.readAttributes(file.toPath(), BasicFileAttributes::class.java)
                        .lastModifiedTime()
                        .toMillis()
                } catch (e: Exception) {
                    0L
                }
            }

            // Delete files beyond the max limit
            sortedFiles.drop(maxImages).forEach { file ->
                try {
                    file.delete()
                    println("Deleted old image: ${file.name}")
                } catch (e: Exception) {
                    println("Failed to delete ${file.name}: ${e.message}")
                }
            }

            if (sortedFiles.size > maxImages) {
                println("Cleaned up ${sortedFiles.size - maxImages} old images, kept $maxImages most recent")
            }
        } catch (e: Exception) {
            println("Error during image cleanup: ${e.message}")
        }
    }

    /**
     * Get relative path from project root
     */
    fun getRelativePath(projectPath: String, filePath: String): String {
        val projectFile = File(projectPath)
        val imageFile = File(filePath)
        
        return try {
            projectFile.toPath().relativize(imageFile.toPath()).toString()
                .replace('\\', '/') // Normalize path separators
        } catch (e: Exception) {
            filePath
        }
    }
}
