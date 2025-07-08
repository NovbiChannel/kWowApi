package ru.kWow.ui_kit.components

import ru.kWow.common.utils.Events
import ru.kWow.common.utils.LuaStringifier
import ru.kWow.common.utils.WowApiStringifier
import ru.kWow.common.utils.toLuaString
import ru.kWow.widgets.UIParent
import ru.kWow.widgets.script.ScriptType
import ru.kWow.widgets.text.TextTemplate
import ru.kWow.widgets.ui.*

/**
 * Experience Bar
 *
 * @see <img src="https://raw.githubusercontent.com/NovbiChannel/kWowApi/refs/heads/main/kWowUIKit/src/main/resources/screenshots/experience_bar.png">
 */
fun ExperienceBar(): String {
    UIParent.createFrame(type = FrameType.Frame, name = "expBar") {
        setSize(325, 25)
        setPoint(Alignment.BOTTOM, 0, 5)

        val playerLvl = text(name = "playerLvl", DrawLayer.ARTWORK, TextTemplate.GameFontNormalLarge) {
            setPoint(Alignment.LEFT)
            setText(WowApiStringifier.Unit.UnitLevel("player"))
        }

        text(name = "lvlLabel", DrawLayer.ARTWORK, TextTemplate.GameFontNormal) {
            setPoint(Alignment.TOPLEFT, 25, 0)
            setText("Уровень".toLuaString())
        }

        val unitExpInfo = text(name = "unitExpInfo", DrawLayer.ARTWORK, TextTemplate.GameFontNormal) {
            setPoint(Alignment.TOPRIGHT)
            setText("0/0 %%0%".toLuaString())
        }

        var statusBar = ""
        createFrame(FrameType.Frame, "progressBarContainer") {
            setPoint(Alignment.BOTTOMRIGHT)
            setSize(300, 10)

            setBackdrop(
                Backdrop(
                    bgFile = "Interface\\Tooltips\\UI-Tooltip-Background",
                    edgeFile = "Interface\\Tooltips\\UI-Tooltip-Border",
                    tileSize = 8,
                    edgeSize = 8,
                    insets = Insets(2, 2, 2, 2)
                )
            )

            setBackdropColor("#000000", 0.5f)
            setBackdropBorderColor("#969696", 0.7f)

            statusBar = createFrame(FrameType.StatusBar, "statusBar") {
                setPoint(Alignment.CENTER)
                setSize(296, 6)

                setStatusBarTexture("Interface\\\\TargetingFrame\\\\UI-StatusBar".toLuaString())
                setStatusBarColor("#40888F", 1f)
                setMinMaxValue(0, 1)
            }
        }

        registerEvent(Events.PLAYER_LOGIN)
        registerEvent(Events.PLAYER_XP_UPDATE)
        registerEvent(Events.PLAYER_LEVEL_UP)
        registerEvent(Events.UPDATE_EXHAUSTION)
        setScript(ScriptType.OnEvent) {
            function {
                val currentXP = setVariable("currentXP", WowApiStringifier.Unit.UnitXP("player"))
                val maxXP = setVariable("maxXP", WowApiStringifier.Unit.UnitXpMax("player"))
                val percent = setVariable("percent", LuaStringifier.Math.floor("($currentXP / $maxXP) * 100"))
                val expValue = setVariable("expValue", "$currentXP / $maxXP")

                statusBar.callExtension("SetValue", expValue)
                playerLvl.callExtension("SetText", WowApiStringifier.Unit.UnitLevel("player"))
                unitExpInfo.callExtension("SetText", """"..$currentXP.."/"..$maxXP.." "..$percent.."%""".toLuaString())

                val restedXP = setVariable("restedXP", WowApiStringifier.Player.GetXPExhaustion())
                ifThen(restedXP) {
                    statusBar.callExtension("SetMinMaxValues", 0, maxXP)
                    val restedExpValue = setVariable("restedExpValue", LuaStringifier.Math.min("$currentXP + $restedXP, $maxXP"))
                    statusBar.callExtension("SetValue", restedExpValue)
                }
            }
            build()
        }
    }
    return UIParent.buildComponent()
}