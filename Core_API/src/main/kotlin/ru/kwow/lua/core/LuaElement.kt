package ru.kwow.lua.core

interface LuaElement {
    fun render(indentLevel: Int = 0): String
}