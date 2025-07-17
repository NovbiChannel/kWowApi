package ru.kWow.lua

class ScriptHandlerBuilder(private val context: LuaContext) {
    private val buffer = StringBuilder()

    fun function(vararg params: String, block: ScriptContext.() -> Unit): String {
        val args = mutableListOf<String>()
        params.forEach { args.add(it) }
        buffer.append("function(${args.joinToString(", ")})\n")
        val sc = ScriptContext(context).apply(block)
        buffer.append(sc.build())
        buffer.append("\nend")
        return buffer.toString()
    }
}

class ScriptContext(private val luaContext: LuaContext) {
    fun setVariable(name: String, value: String): String {
        luaContext.append("local $name = $value")
        return name
    }

/*    fun ifThen(condition: String, block: ScriptContext.() -> Unit) {
        statements.add("if $condition then")
        val innerContext = ScriptContext().apply(block)
        statements.addAll(innerContext.statements.map { "    $it" })
        statements.add("end")
    }*/

    fun call(method: String, vararg args: String) {
        luaContext.append("$method(${args.joinToString(", ")})")
    }

    fun print(message: String) {
        luaContext.append("print(${message.toLua()})")
    }
    fun build(): String {
        return luaContext.build()
    }
}