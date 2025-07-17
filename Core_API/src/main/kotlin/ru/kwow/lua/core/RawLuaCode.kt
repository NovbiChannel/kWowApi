package ru.kwow.lua.core

class RawLuaCode(private val code: String) : LuaElement {
    override fun render(indentLevel: Int): String = code

    companion object {
        // Вспомогательные методы для безопасной генерации
        fun statement(statement: String) = RawLuaCode("$statement;")
        fun comment(text: String) = RawLuaCode("-- $text")
        fun blankLine() = RawLuaCode("")
    }
}