package ru.kWow.widgets.frame

import ru.kWow.common.utils.Events
import ru.kWow.common.utils.LuaSerializer
import ru.kWow.common.utils.ScriptHandlerBuilder
import ru.kWow.common.utils.WowApiStringifier
import ru.kWow.common.utils.toLuaString
import ru.kWow.widgets.script.ScriptType
import ru.kWow.widgets.text.Text
import ru.kWow.widgets.text.TextTemplate
import ru.kWow.widgets.text.UiText
import ru.kWow.widgets.ui.Alignment
import ru.kWow.widgets.ui.Backdrop
import ru.kWow.widgets.ui.DrawLayer
import ru.kWow.widgets.ui.FrameType

open class UIFrame(
    override val name: String
): Frame {
    private val components = mutableListOf<String>()
    private val scripts = mutableListOf<String>()
    override fun createFrame(
        type: FrameType,
        name: String,
        init: Frame.() -> Unit
    ): String {
        val params = mutableListOf<String>()
        params.add(type.name.toLuaString())
        params.add(name.toLuaString())
        params.add(this.name)

        val frame = UIFrame(name)
        components.add(WowApiStringifier.Frame.CreateFrame(name, params))
        frame.init()
        components.add(frame.buildComponent())
        return name
    }

    override fun text(
        name: String,
        drawLayer: DrawLayer,
        template: TextTemplate,
        init: Text.() -> Unit
    ): String {
        val textComponent = UiText(name, this, drawLayer, template)
        textComponent.init()
        components.add(textComponent.buildComponent())
        return name
    }

    override fun setScript(scriptType: ScriptType, handler: ScriptHandlerBuilder.() -> String) {
        scripts.add(WowApiStringifier.Frame.SetScript(name, scriptType.name, handler(ScriptHandlerBuilder())))
    }

    override fun setPoint(alignment: Alignment, x: Int?, y: Int?) {
        val params = mutableListOf<String>()
        params.add(alignment.name.toLuaString())
        x?.let { params.add(it.toString()) }
        y?.let { params.add(it.toString()) }

        components.add(WowApiStringifier.Frame.SetPoint(name, params))
    }

    override fun setSize(width: Int, height: Int) {
        components.add(WowApiStringifier.Frame.SetSize(name, width, height))
    }

    override fun setBackdrop(backdrop: Backdrop) {
        val backdropTable = LuaSerializer.serializeToLuaTable(backdrop)
        components.add(WowApiStringifier.Frame.SetBackdrop(name, backdropTable))
    }

    override fun setBackdropColor(hexColor: String, alpha: Float) {
        components.add(WowApiStringifier.Frame.SetBackdropColor(name, hexColor, alpha))
    }

    override fun setBackdropBorderColor(hexColor: String, alpha: Float) {
        components.add(WowApiStringifier.Frame.SetBackdropBorderColor(name, hexColor, alpha))
    }

    override fun setStatusBarTexture(path: String) {
        components.add(WowApiStringifier.StatusBar.SetStatusBarTexture(name, listOf(path)))
    }

    override fun setStatusBarColor(hexColor: String, alpha: Float) {
        components.add(WowApiStringifier.StatusBar.SetStatusBarColor(name, hexColor, alpha))
    }

    override fun setMinMaxValue(min: Int, max: Int) {
        components.add(WowApiStringifier.StatusBar.SetMinMaxValues(name, min, max))
    }

    override fun registerEvent(event: Events) {
        components.add(WowApiStringifier.Frame.RegisterEvent(name, event.name))
    }

    fun buildComponent(): String {
        return components.joinToString("\n") + "\n" + scripts.joinToString("\n")
    }
}