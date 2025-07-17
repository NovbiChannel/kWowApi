package ru.kwow.lua.core

import ru.kwow.lua.types.LuaTypeRepresentation

data class LuaParam(
    val name: String,
    val type: LuaTypeRepresentation<*>
) : LuaElement {
    override fun render(indentLevel: Int): String = "$name: ${type.luaType}"
}