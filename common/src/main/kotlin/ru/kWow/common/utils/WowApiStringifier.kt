package ru.kWow.common.utils

object WowApiStringifier {
    object Player {
        fun GetXPExhaustion(): String {
            return "GetXPExhaustion".generateMethodCall(listOf())
        }
    }
    object Unit {
        fun UnitLevel(name: String): String {
            return "UnitLevel".generateMethodCall(listOf(name.toLuaString()))
        }
        fun UnitXP(name: String): String {
            return "UnitXP".generateMethodCall(listOf(name.toLuaString()))
        }
        fun UnitXpMax(name: String): String {
            return "UnitXPMax".generateMethodCall(listOf(name.toLuaString()))
        }
        fun UnitName(name: String): String {
            return "UnitName".generateMethodCall(listOf(name.toLuaString()))
        }
        fun UnitHealth(name: String): String {
            return "UnitHealth".generateMethodCall(listOf(name.toLuaString()))
        }
        fun UnitHealthMax(name: String): String {
            return "UnitHealthMax".generateMethodCall(listOf(name.toLuaString()))
        }
        fun UnitPower(name: String): String {
            return "UnitPower".generateMethodCall(listOf(name.toLuaString()))
        }
        fun UnitPowerMax(name: String): String {
            return "UnitPowerMax".generateMethodCall(listOf(name.toLuaString()))
        }
    }
    object Frame {
        fun CreateFrame(parent: String, params: List<String>): String {
            return "CreateFrame".generateVariable(parent, params)
        }
        fun SetPoint(parent: String, params: List<String>): String {
            return "SetPoint".generateCall(parent, params)
        }
        fun ClearAllPoints(parent: String): String {
            return "ClearAllPoints".generateCall(parent, listOf())
        }
        fun SetSize(parent: String, width: Int, height: Int): String {
            return "SetSize".generateCall(parent, listOf(width.toString(), height.toString()))
        }
        fun SetBackdrop(parent: String, table: String): String {
            return "SetBackdrop".generateCall(parent, listOf(table))
        }
        fun SetBackdropColor(parent: String, hexColor: String, alpha: Float = 0f): String {
            val colorArgs = hexToRgba(hexColor, alpha)
            return "SetBackdropColor".generateCall(parent, colorArgs)
        }
        fun SetBackdropBorderColor(parent: String, hexColor: String, alpha: Float =  0f): String {
            val colorArgs = hexToRgba(hexColor, alpha)
            return "SetBackdropBorderColor".generateCall(parent, colorArgs)
        }
        fun RegisterEvent(parent: String, event: String): String {
            return "RegisterEvent".generateCall(parent, listOf(event.toLuaString()))
        }
        fun SetScript(parent: String, scriptType: String, block: String): String {
            return "SetScript".generateCall(parent, listOf(scriptType.toLuaString(), block))
        }
    }

    object StatusBar {
        fun SetStatusBarTexture(parent: String, params: List<String>): String {
            return "SetStatusBarTexture".generateCall(parent, params)
        }
        fun SetStatusBarColor(parent: String, hexColor: String, alpha: Float = 0f): String {
            val colorArgs = hexToRgba(hexColor, alpha)
            return "SetStatusBarColor".generateCall(parent, colorArgs)
        }
        fun SetMinMaxValues(parent: String, min: Int, max: Int): String {
            return "SetMinMaxValues".generateCall(parent, listOf(min.toString(), max.toString()))
        }
        fun SetValue(parent: String, value: Double): String {
            return "SetValue".generateCall(parent, listOf(value.toString()))
        }
    }

    object Text {
        fun CreateFontString(name: String, parent: String, params: List<String>): String {
            return "CreateFontString".generateParentCall(parent, name, params)
        }
        fun SetText(parent: String, text: String): String {
            return "SetText".generateCall(parent, listOf(text))
        }
        fun SetPoint(parent: String, params: List<String>): String {
            return "SetPoint".generateCall(parent, params)
        }
    }

    private fun String.generateCall(parent: String, params: List<String>): String {
        return "$parent:$this(${params.joinToString(", ")})"
    }

    private fun String.generateVariable(name: String, params: List<String>): String {
        return "local $name = $this(${params.joinToString(", ")})"
    }
    private fun String.generateParentCall(parent: String, name: String, params: List<String>): String {
        return "local $name = $parent:$this(${params.joinToString(", ")})"
    }

    private fun String.generateMethodCall(params: List<String>): String {
        return this + "(${params.joinToString(", ")})"
    }
}