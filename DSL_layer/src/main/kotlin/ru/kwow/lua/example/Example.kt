package ru.kwow.lua.example

import ru.kwow.lua.core.BooleanExpression
import ru.kwow.lua.core.NumericExpression
import ru.kwow.lua.core.StringExpression
import ru.kwow.lua.dsl.luaScript

fun main() {
    println("-------------------DSL---------------------")
    println(dslExample())
}

private fun dslExample(): String {
    return luaScript {
        val health = variable("health", NumericExpression(200))
        val maxHealth = variable("maxHealth", NumericExpression(200))

        function("checkStatus") {
            comment("Очень важный комментарий")
            ifThen(BooleanExpression(health == maxHealth)) {
                comment("Это строковая переменная")
                val stringValue = variable("stringValue", StringExpression("Hello luak"))
                print(stringValue)
            }
        }
        function("main") {
            emit("checkStatus()")
        }
    }
}