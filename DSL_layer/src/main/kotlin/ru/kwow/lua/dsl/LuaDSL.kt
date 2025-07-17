package ru.kwow.lua.dsl

import ru.kwow.lua.construct.LuaFunction
import ru.kwow.lua.construct.LuaIfStatement
import ru.kwow.lua.construct.LuaPrintStatement
import ru.kwow.lua.construct.LuaVariable
import ru.kwow.lua.core.*
import ru.kwow.lua.types.LuaBoolean

@LuaDSL
class LuaScriptBuilder() {
    private val context: LuaContext = RootLuaContext()

    fun variable(name: String, init: LuaExpression<*>): LuaVariable<*> {
        val variable = LuaVariable(name, init.type, init)
        context.emit(variable.render(0))
        return variable
    }

    fun function(name: String, params: List<LuaParam> = emptyList(), block: LuaBlock.() -> Unit) {
        val body = LuaBlock().apply(block)
        val func = LuaFunction(name, params, body)
        context.emit(func.render(0))
    }

    fun LuaBlock.ifThen(condition: LuaExpression<LuaBoolean>, block: LuaBlock.() -> Unit) {
        val thenBlock = LuaBlock().apply(block)
        val ifStmt = LuaIfStatement(condition, thenBlock)
        add(ifStmt)
    }

    fun LuaBlock.variable(name: String, init: LuaExpression<*>): LuaVariable<*> {
        val variable = LuaVariable(name, init.type, init)
        add(variable)
        return variable
    }

    fun LuaBlock.print(variable: LuaVariable<*>) {
        val stmt = LuaPrintStatement(variable)
        add(stmt)
    }

    fun build(): String {
        return context.build()
    }
}