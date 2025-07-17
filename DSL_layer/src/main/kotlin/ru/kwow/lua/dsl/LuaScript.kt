package ru.kwow.lua.dsl

fun luaScript(block: LuaScriptBuilder.() -> Unit): String {
    return LuaScriptBuilder().apply(block).build()
}