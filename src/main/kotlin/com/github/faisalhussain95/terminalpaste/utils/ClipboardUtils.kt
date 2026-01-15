package com.github.faisalhussain95.terminalpaste.utils

import java.awt.Toolkit
import java.awt.datatransfer.DataFlavor
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

object ClipboardUtils {
    /**
     * Check if there is an image in the clipboard
     */
    fun hasImageInClipboard(): Boolean {
        return try {
            val clipboard = Toolkit.getDefaultToolkit().systemClipboard
            clipboard.isDataFlavorAvailable(DataFlavor.imageFlavor)
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Get image from clipboard
     */
    fun getImageFromClipboard(): BufferedImage? {
        return try {
            val clipboard = Toolkit.getDefaultToolkit().systemClipboard
            val transferable = clipboard.getContents(null)
            
            if (transferable?.isDataFlavorSupported(DataFlavor.imageFlavor) == true) {
                transferable.getTransferData(DataFlavor.imageFlavor) as? BufferedImage
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Save image to file
     */
    fun saveImage(image: BufferedImage, file: File): Boolean {
        return try {
            // Ensure parent directory exists
            file.parentFile?.mkdirs()
            
            // Save as PNG
            ImageIO.write(image, "png", file)
            true
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Generate timestamp-based filename
     */
    fun generateImageFileName(): String {
        val timestamp = java.time.LocalDateTime.now()
            .toString()
            .replace(":", "-")
            .replace(".", "-")
            .substringBefore('.')
        return "pasted-image-$timestamp.png"
    }
}
