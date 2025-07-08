package ru.kWow.widgets.text

import ru.kWow.common.utils.WowApiStringifier
import ru.kWow.common.utils.toLuaString
import ru.kWow.widgets.frame.Frame
import ru.kWow.widgets.ui.Alignment
import ru.kWow.widgets.ui.DrawLayer

class UiText(
    private val name: String,
    parent: Frame,
    drawLayer: DrawLayer,
    template: TextTemplate
): Text {
    private val statements = mutableListOf<String>()

    init {
        val localParams = listOf(name, drawLayer.name, template.name).map { it.toLuaString() }
        statements.add(WowApiStringifier.Text.CreateFontString(name, parent.name, localParams))
    }

    override fun setPoint(alignment: Alignment, x: Int?, y: Int?) {
        val params = mutableListOf<String>()
        params.add(alignment.name.toLuaString())
        x?.let { params.add(it.toString()) }
        y?.let { params.add(it.toString()) }

        statements.add(WowApiStringifier.Text.SetPoint(name, params))
    }

    override fun setText(text: String) {
        statements.add(WowApiStringifier.Text.SetText(name, text))
    }

    fun buildComponent() = statements.joinToString("\n")
}

interface Text {
    fun setPoint(alignment: Alignment, x: Int? = null, y: Int? = null)
    fun setText(text: String)
}