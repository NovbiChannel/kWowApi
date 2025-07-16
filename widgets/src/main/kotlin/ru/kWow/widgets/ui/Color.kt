package ru.kWow.widgets.ui

import java.util.Locale

class Color private constructor(
    private val red: Float,
    private val green: Float,
    private val blue: Float,
    private val alpha: Float
){
    companion object {
        fun fromHex(hexColor: String, alpha: Float = 1f): Color {
            require(hexColor.isNotEmpty()) { "The hex string cannot be empty." }
            require(hexColor.matches(Regex("^#?([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$"))) {
                "Incorrect hex color format: $hexColor"
            }
            require(alpha in 0f..1f) { "Alpha should be in 0..1 range, got $alpha" }

            val cleanHex = hexColor.removePrefix("#").uppercase()
            val fullHex = when (cleanHex.length) {
                3 -> cleanHex.map { "$it$it" }.joinToString("")
                6 -> cleanHex
                else -> throw IllegalArgumentException("HEX must be 3 or 6 characters long")
            }

            val r = fullHex.substring(0, 2).toInt(16) / 255f
            val g = fullHex.substring(2, 4).toInt(16) / 255f
            val b = fullHex.substring(4, 6).toInt(16) / 255f
            val clampedAlpha = alpha.coerceIn(0f..1f)

            return Color(r, g, b, clampedAlpha)
        }

        fun fromRgba(red: Int, green: Int, blue: Int, alpha: Int = 255): Color {
            require(red in 0..255) { "Red must be 0..255, got $red" }
            require(green in 0..255) { "Green must be 0..255, got $green" }
            require(blue in 0..255) { "Blue must be 0..255, got $blue" }
            require(alpha in 0..255) { "Alpha must be 0..255, got $alpha" }

            return Color(
                red / 255f,
                green / 255f,
                blue / 255f,
                alpha / 255f
            )
        }

        operator fun invoke(argb: Int): Color {
            val a = (argb shr 24) and 0xFF
            val r = (argb shr 16) and 0xFF
            val g = (argb shr 8) and 0xFF
            val b = argb and 0xFF
            return fromRgba(r, g, b, a)
        }
    }

    fun toNormalizedList(): List<String> {
        return listOf(red, green, blue, alpha).map {
            "%.3f".format(Locale.US, it).removeSuffix(".000")
        }
    }
}