package ru.kWow.common.utils

data class AddonConfig(
    val patch: Patch = Patch.FallOfTheLichKing,
    val title: String = "kWowExample",
    val notes: String = "kWow - a new framework created for developing addons for WoW using a declarative style of writing code.",
    val author: String = "kWow",
    val version: String = "1.0",
    val outputPath: String = ""
)

sealed class Patch(val value: Int) {
    data object FallOfTheLichKing: Patch(30300)
}
