package ru.kWow.widgets.frame

import ru.kWow.api.Events
import ru.kWow.api.WowApiStringifier
import ru.kWow.api.types.ScriptType
import ru.kWow.lua.LuaSerializer
import ru.kWow.lua.ScriptHandlerBuilder
import ru.kWow.lua.toLua
import ru.kWow.widgets.text.Text
import ru.kWow.widgets.text.TextTemplate
import ru.kWow.widgets.text.UiText
import ru.kWow.widgets.ui.*

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
        params.add(type.name.toLua())
        params.add(name.toLua())
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
        scripts.add(WowApiStringifier.Frame.SetScript(name, scriptType.luaValue, handler(ScriptHandlerBuilder())))
    }

    override fun setPoint(alignment: Alignment, x: Int?, y: Int?) {
        val params = mutableListOf<String>()
        params.add(alignment.name.toLua())
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

    override fun setBackdropColor(color: Color) {
        val normalized = color.toNormalizedList()
        components.add(WowApiStringifier.Frame.SetBackdropColor(name, normalized))
    }

    override fun setBackdropBorderColor(color: Color) {
        val normalized = color.toNormalizedList()
        components.add(WowApiStringifier.Frame.SetBackdropBorderColor(name, normalized))
    }

    override fun setStatusBarTexture(path: String) {
        components.add(WowApiStringifier.StatusBar.SetStatusBarTexture(name, listOf(path)))
    }

    override fun setStatusBarColor(color: Color) {
        val normalized = color.toNormalizedList()
        components.add(WowApiStringifier.StatusBar.SetStatusBarColor(name, normalized))
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