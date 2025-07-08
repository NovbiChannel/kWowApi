package ru.kWow.widgets.frame

import ru.kWow.common.utils.Events
import ru.kWow.common.utils.ScriptHandlerBuilder
import ru.kWow.widgets.script.ScriptType
import ru.kWow.widgets.text.Text
import ru.kWow.widgets.text.TextTemplate
import ru.kWow.widgets.ui.Alignment
import ru.kWow.widgets.ui.Backdrop
import ru.kWow.widgets.ui.DrawLayer
import ru.kWow.widgets.ui.FrameType

interface Frame {
    val name: String
    fun createFrame(type: FrameType, name: String, init: Frame.() -> Unit): String
    fun text(name: String, drawLayer: DrawLayer, template: TextTemplate, init: Text.() -> Unit): String

    fun setScript(scriptType: ScriptType, handler: ScriptHandlerBuilder.() -> String)
    fun setPoint(alignment: Alignment, x: Int? = null, y: Int? = null)
    fun setSize(width: Int, height: Int)
    fun setBackdrop(backdrop: Backdrop)
    fun setBackdropColor(hexColor: String, alpha: Float = 0f)
    fun setBackdropBorderColor(hexColor: String, alpha: Float = 0f)
    fun setStatusBarTexture(path: String)
    fun setStatusBarColor(hexColor: String, alpha: Float = 0f)
    fun setMinMaxValue(min: Int = 0, max: Int = 1)

    fun registerEvent(event: Events)
}