package ru.kwow.lua.core

import ru.kwow.lua.types.LuaBoolean
import ru.kwow.lua.types.LuaNumber
import ru.kwow.lua.types.LuaString
import ru.kwow.lua.types.LuaType
import ru.kwow.lua.types.LuaTypeRepresentation

sealed class LuaExpression<T : LuaType> : LuaElement {
    abstract val type: LuaTypeRepresentation<T>
}

// Числовые выражения
class NumericExpression(
    val value: Number
) : LuaExpression<LuaNumber>() {
    override val type = LuaNumber
    override fun render(indentLevel: Int): String = value.toString()
}

// Строковые выражения
class StringExpression(
    val value: String
) : LuaExpression<LuaString>() {
    override val type = LuaString
    override fun render(indentLevel: Int): String = "\"${value.escapeLua()}\""

    private fun String.escapeLua() = replace("\\", "\\\\")
        .replace("\"", "\\\"")
        .replace("\n", "\\n")
}

// Булевы выражения
class BooleanExpression(
    val value: Boolean
) : LuaExpression<LuaBoolean>() {
    override val type = LuaBoolean
    override fun render(indentLevel: Int): String = if (value) "true" else "false"
}

// Бинарные операции
class BinaryOperation<T : LuaType>(
    val left: LuaExpression<*>,
    val operator: String,
    val right: LuaExpression<*>,
    override val type: LuaTypeRepresentation<T>
) : LuaExpression<T>() {
    override fun render(indentLevel: Int): String = "(${left.render()} $operator ${right.render()})"
}

class RawLuaExpression<T : LuaType>(
    private val code: String,
    override val type: LuaTypeRepresentation<T>
) : LuaExpression<T>() {
    override fun render(indentLevel: Int): String = code
}