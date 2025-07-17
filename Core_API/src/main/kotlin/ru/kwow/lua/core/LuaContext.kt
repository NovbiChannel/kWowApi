package ru.kwow.lua.core

@DslMarker
annotation class LuaDSL

interface LuaContext {
    fun emit(code: String)
    fun build(): String
}

class RootLuaContext : LuaContext {
    private val buffer = StringBuilder()

    override fun emit(code: String) {
        buffer.appendLine(code)
    }

    override fun build(): String = buffer.toString()
}