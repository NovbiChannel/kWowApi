package ru.kWow.lua

class ScriptHandlerBuilder {
    private val luaCode = StringBuilder()

    fun function(vararg params: String, block: ScriptContext.() -> Unit) {
        val args = mutableListOf<String>()
        params.forEach { args.add(it) }
        luaCode.append("function(${args.joinToString(", ")})\n")
        val context = ScriptContext().apply(block)
        luaCode.append(context.build())
        luaCode.append("\nend")
    }

    fun build(): String = luaCode.toString()
}

class ScriptContext {
    private val statements = mutableListOf<String>()

    fun setVariable(name: String, value: String): String {
        statements.add("local $name = $value")
        return name
    }

    fun ifThen(condition: String, block: ScriptContext.() -> Unit) {
        statements.add("if $condition then")
        val innerContext = ScriptContext().apply(block)
        statements.addAll(innerContext.statements.map { "    $it" })
        statements.add("end")
    }

    fun call(method: String, vararg args: String) {
        statements.add("$method(${args.joinToString(", ")})")
    }

    fun String.callExtension(method: String, vararg args: Any) {
        val listArgs = mutableListOf<String>()
        args.forEach { listArgs.add(it.toString()) }

        statements.add("$this:$method(${listArgs.joinToString(", ")})")
    }

    fun print(message: String) {
        statements.add("print(${message.toLua()})")
    }

    fun build(): String = statements.joinToString("\n")
}