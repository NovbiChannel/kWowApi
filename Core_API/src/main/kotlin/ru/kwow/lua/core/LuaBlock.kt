package ru.kwow.lua.core

class LuaBlock : LuaElement {
    private val elements = mutableListOf<LuaElement>()

    fun emit(code: String) = add(RawLuaCode(code))
    fun comment(text: String) = add(RawLuaCode.comment(text))
    fun blankLine() = add(RawLuaCode.blankLine())

    fun add(element: LuaElement) = apply { elements.add(element) }

    override fun render(indentLevel: Int): String {
        return elements.joinToString("\n") { element ->
            "    ".repeat(indentLevel) + element.render(indentLevel)
        }
    }
}