package ru.kWow.widgets.text

import ru.kWow.api.WowApiStringifier
import ru.kWow.lua.LuaContext
import ru.kWow.lua.toLua
import ru.kWow.widgets.frame.Frame
import ru.kWow.widgets.ui.Alignment
import ru.kWow.widgets.ui.DrawLayer

class UiText(
    private val name: String,
    parent: Frame,
    drawLayer: DrawLayer,
    template: TextTemplate,
    private val context: LuaContext
): Text {

    init {
        val localParams = listOf(name, drawLayer.name, template.name).map { it.toLua() }
        context.append(WowApiStringifier.Text.CreateFontString(name, parent.name, localParams))
    }

    override fun setPoint(alignment: Alignment, x: Int?, y: Int?) {
        val params = mutableListOf<String>()
        params.add(alignment.name.toLua())
        x?.let { params.add(it.toString()) }
        y?.let { params.add(it.toString()) }

        context.append(WowApiStringifier.Text.SetPoint(name, params))
    }

    override fun setText(text: String) {
        context.append(WowApiStringifier.Text.SetText(name, text))
    }
}

interface Text {
    fun setPoint(alignment: Alignment, x: Int? = null, y: Int? = null)
    fun setText(text: String)
}