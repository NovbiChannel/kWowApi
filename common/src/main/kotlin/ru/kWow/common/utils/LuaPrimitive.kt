package ru.kWow.common.utils

val nil = "nil"

fun String.toLuaString(): String = "\"$this\""

fun String.luaLocal(value: String): String = "local $this = $value"