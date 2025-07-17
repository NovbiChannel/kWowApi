package ru.kWow.widgets.frame

import ru.kWow.api.Events
import ru.kWow.api.WowApiStringifier
import ru.kWow.api.types.ScriptType
import ru.kWow.lua.LuaContext
import ru.kWow.lua.LuaSerializer
import ru.kWow.lua.ScriptHandlerBuilder
import ru.kWow.lua.toLua
import ru.kWow.widgets.text.Text
import ru.kWow.widgets.text.TextTemplate
import ru.kWow.widgets.text.UiText
import ru.kWow.widgets.ui.*

open class UIFrame(
    override val name: String,
    private val context: LuaContext
): Frame {
    override fun createFrame(type: FrameType, name: String, init: Frame.() -> Unit): Frame {
        val params = mutableListOf<String>()
        params.add(type.name.toLua())
        params.add(name.toLua())
        params.add(this.name)

        val frame = UIFrame(name, context.createChildContext())
        context.append(WowApiStringifier.Frame.CreateFrame(name, params))
        frame.init()
        return frame
    }

    override fun text(name: String, drawLayer: DrawLayer, template: TextTemplate, init: Text.() -> Unit): Text {
        val textComponent = UiText(name, this, drawLayer, template, context.createChildContext())
        textComponent.init()
        return textComponent
    }

    override fun setScript(scriptType: ScriptType, handler: ScriptHandlerBuilder.() -> String) {
        context.append(WowApiStringifier.Frame.SetScript(name, scriptType.luaValue, handler(ScriptHandlerBuilder(context.createChildContext()))))
    }

    override fun setPoint(alignment: Alignment, x: Int?, y: Int?) {
        val params = mutableListOf<String>()
        params.add(alignment.name.toLua())
        x?.let { params.add(it.toString()) }
        y?.let { params.add(it.toString()) }

        context.append(WowApiStringifier.Frame.SetPoint(name, params))
    }

    override fun setSize(width: Int, height: Int) {
        context.append(WowApiStringifier.Frame.SetSize(name, width, height))
    }

    override fun setBackdrop(backdrop: Backdrop) {
        val backdropTable = LuaSerializer.serializeToLuaTable(backdrop)
        context.append(WowApiStringifier.Frame.SetBackdrop(name, backdropTable))
    }

    override fun setBackdropColor(color: Color) {
        val normalized = color.toNormalizedList()
        context.append(WowApiStringifier.Frame.SetBackdropColor(name, normalized))
    }

    override fun setBackdropBorderColor(color: Color) {
        val normalized = color.toNormalizedList()
        context.append(WowApiStringifier.Frame.SetBackdropBorderColor(name, normalized))
    }

    override fun setStatusBarTexture(path: String) {
        context.append(WowApiStringifier.StatusBar.SetStatusBarTexture(name, listOf(path.toLua())))
    }

    override fun setStatusBarColor(color: Color) {
        val normalized = color.toNormalizedList()
        context.append(WowApiStringifier.StatusBar.SetStatusBarColor(name, normalized))
    }

    override fun setMinMaxValue(min: Int, max: Int) {
        context.append(WowApiStringifier.StatusBar.SetMinMaxValues(name, min, max))
    }

    override fun registerEvent(event: Events) {
        context.append(WowApiStringifier.Frame.RegisterEvent(name, event.name))
    }

    override fun setValue(value: String) {
        context.append(WowApiStringifier.StatusBar.SetValue(name, value))
    }
}