package ru.kWow.lua

fun Any.toLua(): String = LuaSerializer.serializeToLuaTable(this)