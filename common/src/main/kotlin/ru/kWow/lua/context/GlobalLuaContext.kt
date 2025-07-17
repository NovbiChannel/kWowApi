package ru.kWow.lua.context

import ru.kWow.lua.LuaContext

class GlobalLuaContext: LuaContext {
    private val buffer = StringBuilder()
    private val children = mutableListOf<LuaContext>()

    override fun append(code: String) {
        println("Append code: $code")
        buffer.append(code)
    }

    override fun appendLine(code: String) {
        println("Append line code: $code")
        buffer.appendLine(code)
    }

    override fun build(): String {
        children.forEach { child ->
            buffer.appendLine(child.build())
        }
        println("build code: $buffer")
        return buffer.toString()
    }

    override fun createChildContext(): LuaContext {
        return ChildLuaContext(this)
    }
}