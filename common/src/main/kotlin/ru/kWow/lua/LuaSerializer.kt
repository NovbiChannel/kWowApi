package ru.kWow.lua

import kotlin.reflect.full.declaredMemberProperties

object LuaSerializer {

    fun serializeToLuaTable(obj: Any): String {
        return when (obj) {
            is Map<*, *> -> serializeMap(obj)
            is Collection<*> -> serializeCollection(obj)
            is Array<*> -> serializeCollection(obj.toList())
            is Boolean -> if (obj) "true" else "false"
            is Number -> obj.toString()
            is String -> "\"${escapeString(obj)}\""
            else -> serializeDataClass(obj)
        }
    }

    private fun serializeDataClass(obj: Any): String {
        val properties = obj::class.declaredMemberProperties
        if (properties.isEmpty()) return "{}"

        val entries = properties.map { prop ->
            val value = prop.getter.call(obj)
            "${prop.name} = ${serializeValue(value)}"
        }

        return "{ ${entries.joinToString(", ")} }"
    }

    private fun serializeMap(map: Map<*, *>): String {
        if (map.isEmpty()) return "{}"

        val entries = map.map { (key, value) ->
            when (key) {
                is String -> "'${escapeString(key)}' = ${serializeValue(value)}"
                is Number -> "[${key}] = ${serializeValue(value)}"
                else -> "[${serializeValue(key)}] = ${serializeValue(value)}"
            }
        }

        return "{ ${entries.joinToString(", ")} }"
    }

    private fun serializeCollection(collection: Collection<*>): String {
        if (collection.isEmpty()) return "{}"

        val entries = collection.mapIndexed { index, value ->
            "[${index + 1}] = ${serializeValue(value)}"
        }

        return "{ ${entries.joinToString(", ")} }"
    }

    private fun serializeValue(value: Any?): String {
        return when (value) {
            null -> "nil"
            is Map<*, *> -> serializeMap(value)
            is Collection<*> -> serializeCollection(value)
            is Array<*> -> serializeCollection(value.toList())
            is Boolean -> if (value) "true" else "false"
            is Number -> value.toString()
            is String -> "\"${escapeString(value)}\""
            else -> serializeDataClass(value)
        }
    }

    private fun escapeString(str: String): String {
        return str.replace("\\", "\\\\")
            .replace("\"", "\\\"")
            .replace("\n", "\\n")
            .replace("\r", "\\r")
    }
}
