package ru.kWow.lua.context

import ru.kWow.lua.LuaContext

fun LuaContext.fileBuilder(fileName: String, content: (LuaContext) -> String): Pair<String, String> {
    return fileName to content(this)
}