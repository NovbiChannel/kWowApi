package ru.kWow.lua

object LuaStringifier {
    object Math {
        fun floor(value: String): String {
            return "math.floor".generateMethodCall(listOf(value))
        }
        fun min(value: String): String {
            return "math.min".generateMethodCall(listOf(value))
        }
    }
}

private fun String.generateMethodCall(params: List<String>): String {
    return this + "(${params.joinToString(", ")})"
}