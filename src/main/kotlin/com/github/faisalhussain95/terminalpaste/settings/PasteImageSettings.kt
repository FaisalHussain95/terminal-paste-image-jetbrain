package com.github.faisalhussain95.terminalpaste.settings

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil

@State(
    name = "com.github.faisalhussain95.terminalpaste.settings.PasteImageSettings",
    storages = [Storage("TerminalPasteImagePlugin.xml")]
)
class PasteImageSettings : PersistentStateComponent<PasteImageSettings> {
    var folderName: String = ".cp-images"
    var autoGitIgnore: Boolean = true
    var maxImages: Int = 10

    override fun getState(): PasteImageSettings = this

    override fun loadState(state: PasteImageSettings) {
        XmlSerializerUtil.copyBean(state, this)
    }

    companion object {
        val instance: PasteImageSettings
            get() = ApplicationManager.getApplication().getService(PasteImageSettings::class.java)
    }
}
