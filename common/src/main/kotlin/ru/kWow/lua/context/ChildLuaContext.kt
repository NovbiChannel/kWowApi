package ru.kWow.lua.context

import ru.kWow.lua.LuaContext

class ChildLuaContext(
    private val parent: LuaContext
): LuaContext {
    private val buffer = StringBuilder()

    override fun append(code: String) {
        println("${parent.javaClass.name}")
        println("Append code: $code")
        buffer.append(code)
    }

    override fun appendLine(code: String) {
        println("${parent.javaClass.name}")
        println("Append line code: $code")
        buffer.appendLine(code)
    }

    override fun build(): String {
        println("${parent.javaClass.name}")
        println("build code: $buffer")
        return buffer.toString()
    }

    override fun createChildContext(): LuaContext {
        return ChildLuaContext(this)
    }

    fun flushToParent() {
        parent.append(build())
    }
}