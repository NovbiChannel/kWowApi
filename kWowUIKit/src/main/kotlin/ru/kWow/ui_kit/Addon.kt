package ru.kWow.ui_kit

import ru.kWow.addon.AddonConfig
import ru.kWow.addon.AddonCreator
import ru.kWow.addon.Patch
import ru.kWow.ui_kit.components.ExperienceBar

private val config = AddonConfig(
    patch = Patch.FallOfTheLichKing,
    title = "kWowUIKit",
    notes = "This addon is generated using the kWow library.",
    author = "Novbi",
    version = "1.0",
    outputPath = "D:\\Games\\sirus\\World of Warcraft Sirus\\Interface\\AddOns\\"
)

fun main() {
    AddonCreator.create(config, listOf(
        "ExperienceBar" to ExperienceBar()
    ))
}