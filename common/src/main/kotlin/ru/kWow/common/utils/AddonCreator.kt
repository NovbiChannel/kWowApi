package ru.kWow.common.utils

import java.io.File

object AddonCreator {
    fun create(config: AddonConfig, luaScripts: List<Pair<String, String>>) {
        val outputPath = config.outputPath + "${config.title}\\"
        val addonDir = File(outputPath)
        if (!addonDir.exists()) {
            addonDir.mkdir()
        }
        val generatedFiles = mutableListOf<String>()

        luaScripts.forEach { script ->
            val (fileName, content) = script
            val currentFileName = "$fileName.lua"
            File(addonDir, currentFileName).apply { writeText(content) }
            generatedFiles.add(currentFileName)
        }
        val tocFile = File(addonDir, "${config.title}.toc")
        tocFile.writeText(generateTocFileContent(config, generatedFiles))

        println("The addon has been successfully build, the final path: $outputPath")
    }

    private fun generateTocFileContent(config: AddonConfig, files: List<String>): String {
        return """
            ## Interface: ${config.patch.value}
            ## Title: ${config.title}
            ## Notes: ${config.notes}
            ## Author: ${config.author}
            ## Version: ${config.version}
            
            ${files.joinToString("\n")}
        """.trimIndent()
    }
}