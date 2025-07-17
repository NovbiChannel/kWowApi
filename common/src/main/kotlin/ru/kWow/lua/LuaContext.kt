package ru.kWow.lua

interface LuaContext {
    fun append(code: String)
    fun appendLine(code: String = "\n")
    fun build(): String
    fun createChildContext(): LuaContext
}