package ru.kWow.ui_kit

import ru.kWow.addon.AddonConfig
import ru.kWow.addon.AddonCreator
import ru.kWow.addon.Patch
import ru.kWow.lua.context.GlobalLuaContext
import ru.kWow.lua.context.fileBuilder
import ru.kWow.ui_kit.components.ExperienceBar

private val config = AddonConfig(
    patch = Patch.FallOfTheLichKing,
    title = "kWowUIKit",
    notes = "This addon is generated using the kWow library.",
    author = "Novbi",
    version = "1.0",
    outputPath = "D:\\Games\\"
)

fun main() {
    AddonCreator.create(config, listOf(
        GlobalLuaContext().fileBuilder("ExperienceBar") { ctx ->
            ExperienceBar(ctx)
        }
    ))
}