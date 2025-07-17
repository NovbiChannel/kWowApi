package ru.kwow.lua.types

interface LuaType {
    val luaType: String
}

interface LuaTypeRepresentation<T : LuaType> {
    val luaType: String
    fun validate(value: Any): Boolean
}

object LuaNumber : LuaType, LuaTypeRepresentation<LuaNumber> {
    override val luaType = "number"
    override fun validate(value: Any) = value is Number
}

object LuaString : LuaType, LuaTypeRepresentation<LuaString> {
    override val luaType = "string"
    override fun validate(value: Any) = value is String
}

object LuaBoolean : LuaType, LuaTypeRepresentation<LuaBoolean> {
    override val luaType = "boolean"
    override fun validate(value: Any) = value is Boolean
}