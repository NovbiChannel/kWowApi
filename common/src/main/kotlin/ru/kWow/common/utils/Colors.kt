package ru.kWow.common.utils

import java.util.Locale

internal fun hexToRgba(hexColor: String, alpha: Float = 1f): List<String> {
    require(hexColor.isNotEmpty()) { "HEX строка не может быть пустой" }
    require(hexColor.matches(Regex("^#?([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$"))) {
        "Некорректный формат HEX цвета: $hexColor"
    }

    val cleanHex = hexColor.removePrefix("#").uppercase()
    val fullHex = when (cleanHex.length) {
        3 -> cleanHex.map { "$it$it" }.joinToString("")
        6 -> cleanHex
        else -> throw IllegalArgumentException("HEX должен содержать 3 или 6 символов")
    }

    val r = fullHex.substring(0, 2).toInt(16) / 255f
    val g = fullHex.substring(2, 4).toInt(16) / 255f
    val b = fullHex.substring(4, 6).toInt(16) / 255f
    val clampedAlpha = alpha.coerceIn(0f..1f)

    return listOf(r, g, b, clampedAlpha).map {
        "%.3f".format(Locale.US, it).removeSuffix(".000")
    }
}
