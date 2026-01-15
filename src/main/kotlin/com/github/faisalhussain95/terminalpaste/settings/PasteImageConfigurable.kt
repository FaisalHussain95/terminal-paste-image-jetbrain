package com.github.faisalhussain95.terminalpaste.settings

import com.intellij.openapi.options.Configurable
import javax.swing.*
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Insets

class PasteImageConfigurable : Configurable {
    private var folderNameField: JTextField? = null
    private var autoGitIgnoreCheckbox: JCheckBox? = null
    private var maxImagesSpinner: JSpinner? = null

    override fun getDisplayName(): String = "Terminal Paste Image"

    override fun createComponent(): JComponent {
        val panel = JPanel(GridBagLayout())
        val gbc = GridBagConstraints()
        gbc.insets = Insets(5, 5, 5, 5)
        gbc.anchor = GridBagConstraints.WEST
        gbc.fill = GridBagConstraints.HORIZONTAL

        // Folder name
        gbc.gridx = 0
        gbc.gridy = 0
        gbc.weightx = 0.0
        panel.add(JLabel("Folder Name:"), gbc)

        gbc.gridx = 1
        gbc.weightx = 1.0
        folderNameField = JTextField(20)
        panel.add(folderNameField, gbc)

        // Auto gitignore
        gbc.gridx = 0
        gbc.gridy = 1
        gbc.gridwidth = 2
        autoGitIgnoreCheckbox = JCheckBox("Automatically add folder to .gitignore")
        panel.add(autoGitIgnoreCheckbox, gbc)

        // Max images
        gbc.gridx = 0
        gbc.gridy = 2
        gbc.gridwidth = 1
        gbc.weightx = 0.0
        panel.add(JLabel("Maximum Images:"), gbc)

        gbc.gridx = 1
        gbc.weightx = 1.0
        val spinnerModel = SpinnerNumberModel(10, 1, 100, 1)
        maxImagesSpinner = JSpinner(spinnerModel)
        panel.add(maxImagesSpinner, gbc)

        // Description
        gbc.gridx = 0
        gbc.gridy = 3
        gbc.gridwidth = 2
        gbc.insets = Insets(15, 5, 5, 5)
        val descriptionText = """
            <html>
            <body style='width: 400px'>
            <p><b>Usage:</b></p>
            <ul>
                <li>Copy an image to clipboard</li>
                <li>Focus on terminal</li>
                <li>Press Ctrl+Alt+V (Cmd+Alt+V on macOS)</li>
            </ul>
            <p>Images will be saved to the specified folder and the path will be inserted into the terminal.</p>
            </body>
            </html>
        """.trimIndent()
        panel.add(JLabel(descriptionText), gbc)

        reset()
        return panel
    }

    override fun isModified(): Boolean {
        val settings = PasteImageSettings.instance
        return folderNameField?.text != settings.folderName ||
                autoGitIgnoreCheckbox?.isSelected != settings.autoGitIgnore ||
                maxImagesSpinner?.value != settings.maxImages
    }

    override fun apply() {
        val settings = PasteImageSettings.instance
        settings.folderName = folderNameField?.text ?: ".cp-images"
        settings.autoGitIgnore = autoGitIgnoreCheckbox?.isSelected ?: true
        settings.maxImages = maxImagesSpinner?.value as? Int ?: 10
    }

    override fun reset() {
        val settings = PasteImageSettings.instance
        folderNameField?.text = settings.folderName
        autoGitIgnoreCheckbox?.isSelected = settings.autoGitIgnore
        maxImagesSpinner?.value = settings.maxImages
    }
}
