package ru.kwow.lua.construct

import ru.kwow.lua.core.LuaBlock
import ru.kwow.lua.core.LuaElement
import ru.kwow.lua.core.LuaExpression
import ru.kwow.lua.core.LuaParam
import ru.kwow.lua.types.LuaBoolean
import ru.kwow.lua.types.LuaType
import ru.kwow.lua.types.LuaTypeRepresentation

class LuaVariable<T : LuaType>(
    val name: String,
    val type: LuaTypeRepresentation<T>,
    val initializer: LuaExpression<*>? = null
) : LuaElement {
    override fun render(indentLevel: Int): String {
        return initializer?.let {
            "local $name: ${type.luaType} = ${it.render()}"
        } ?: "local $name: ${type.luaType}"
    }
}

class LuaFunction(
    val name: String,
    val params: List<LuaParam>,
    val body: LuaBlock
) : LuaElement {
    override fun render(indentLevel: Int): String {
        return """
            |${"    ".repeat(indentLevel)}function $name(${params.joinToString(", ") { it.render() }})
            |${body.render(indentLevel + 1)}
            |${"    ".repeat(indentLevel)}end
        """.trimMargin()
    }
}

class LuaIfStatement(
    val condition: LuaExpression<LuaBoolean>,
    val thenBlock: LuaBlock
) : LuaElement {
    override fun render(indentLevel: Int): String {
        return """
            |if ${condition.render()} then
            |${thenBlock.render(indentLevel + 1)}
            |${"    ".repeat(indentLevel)}end
        """.trimMargin()
    }
}

class LuaPrintStatement(
    val variable: LuaVariable<*>
): LuaElement {
    override fun render(indentLevel: Int): String {
        return "print(${variable.initializer?.render(indentLevel + 1)})"
    }
}