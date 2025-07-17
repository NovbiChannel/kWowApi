package ru.kWow.api

import ru.kWow.api.types.PowerType
import ru.kWow.api.types.UnitId
import ru.kWow.lua.toLua

object WowApiStringifier {
    object Player {
        /**
         * Returns the amount of current rested XP for the character.
         */
        fun GetXPExhaustion(): String {
            return "GetXPExhaustion".generateMethodCall(listOf())
        }

        /**
         * Returns the current XP of the unit; only works on the player.
         */
        fun CurrentXp(): String {
            return "UnitXP".generateMethodCall(listOf(UnitId.PLAYER.luaValue))
        }

        /**
         * Returns the maximum XP of the unit; only works on the player.
         */
        fun XpMax(): String {
            return "UnitXPMax".generateMethodCall(listOf(UnitId.PLAYER.luaValue))
        }
    }
    object Unit {
        /**
         * Returns the level of the unit.
         */
        fun UnitLevel(unitId: UnitId): String {
            return "UnitLevel".generateMethodCall(listOf(unitId.luaValue))
        }
        /**
         * Returns the name and realm of the unit.
         */
        fun UnitName(unitId: UnitId): String {
            return "UnitName".generateMethodCall(listOf(unitId.luaValue))
        }
        /**
         * Returns the current health of the unit.
         */
        fun UnitHealth(unitId: UnitId): String {
            return "UnitHealth".generateMethodCall(listOf(unitId.luaValue))
        }
        /**
         * Returns the max value health of the unit.
         */
        fun UnitHealthMax(unitId: UnitId): String {
            return "UnitHealthMax".generateMethodCall(listOf(unitId.luaValue))
        }

        /**
         * Returns the current power resource of the unit.
         */
        fun UnitPower(unitId: UnitId, powerType: PowerType? = null): String {
            return "UnitPower".generateMethodCall(listOfNotNull(unitId.luaValue, powerType?.luaValue))
        }

        /**
         * Returns the max value power resource of the unit.
         */
        fun UnitPowerMax(unitId: UnitId, powerType: PowerType? = null): String {
            return "UnitPowerMax".generateMethodCall(listOfNotNull(unitId.luaValue, powerType?.luaValue))
        }
    }
    object Frame {
        /**
         * Creates a Frame object.
         */
        fun CreateFrame(parent: String, params: List<String>): String {
            return "CreateFrame".generateVariable(parent, params)
        }

        /**
         * Sets an anchor point for the region.
         */
        fun SetPoint(parent: String, params: List<String>): String {
            return "SetPoint".generateCall(parent, params)
        }

        /**
         * Removes all anchor points from the region.
         */
        fun ClearAllPoints(parent: String): String {
            return "ClearAllPoints".generateCall(parent, listOf())
        }

        /**
         * Sets the width and height of the region.
         */
        fun SetSize(parent: String, width: Int, height: Int): String {
            return "SetSize".generateCall(parent, listOf(width.toString(), height.toString()))
        }

        /**
         * Sets the backdrop texture of a frame.
         */
        fun SetBackdrop(parent: String, table: String): String {
            return "SetBackdrop".generateCall(parent, listOf(table))
        }
        fun SetBackdropColor(parent: String, normalizedColor: List<String>): String {
            return "SetBackdropColor".generateCall(parent, normalizedColor)
        }
        fun SetBackdropBorderColor(parent: String, normalizedColor: List<String>): String {
            return "SetBackdropBorderColor".generateCall(parent, normalizedColor)
        }
        fun RegisterEvent(parent: String, event: String): String {
            return "RegisterEvent".generateCall(parent, listOf(event.toLua()))
        }
        fun SetScript(parent: String, scriptType: String, block: String): String {
            return "SetScript".generateCall(parent, listOf(scriptType.toLua(), block))
        }
    }

    object StatusBar {
        fun SetStatusBarTexture(parent: String, params: List<String>): String {
            return "SetStatusBarTexture".generateCall(parent, params)
        }
        fun SetStatusBarColor(parent: String, normalizedColor: List<String>): String {
            return "SetStatusBarColor".generateCall(parent, normalizedColor)
        }
        fun SetMinMaxValues(parent: String, min: Int, max: Int): String {
            return "SetMinMaxValues".generateCall(parent, listOf(min.toString(), max.toString()))
        }
        fun SetValue(parent: String, value: String): String {
            return "SetValue".generateCall(parent, listOf(value))
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