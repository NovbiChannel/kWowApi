package ru.kWow.widgets.frame

import ru.kWow.api.Events
import ru.kWow.api.types.ScriptType
import ru.kWow.lua.ScriptHandlerBuilder
import ru.kWow.widgets.text.Text
import ru.kWow.widgets.text.TextTemplate
import ru.kWow.widgets.ui.*

interface Frame {
    val name: String

    fun createFrame(type: FrameType, name: String, init: Frame.() -> Unit): Frame
    fun text(name: String, drawLayer: DrawLayer, template: TextTemplate, init: Text.() -> Unit): Text

    fun setScript(scriptType: ScriptType, handler: ScriptHandlerBuilder.() -> String)
    fun setPoint(alignment: Alignment, x: Int? = null, y: Int? = null)
    fun setSize(width: Int, height: Int)
    fun setBackdrop(backdrop: Backdrop)
    fun setBackdropColor(color: Color)
    fun setBackdropBorderColor(color: Color)
    fun setStatusBarTexture(path: String)
    fun setStatusBarColor(color: Color)
    fun setMinMaxValue(min: Int = 0, max: Int = 1)
    fun setValue(value: String)

    fun registerEvent(event: Events)
}