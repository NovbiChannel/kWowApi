package ru.kWow.ui_kit.components

import ru.kWow.api.types.ScriptType
import ru.kWow.api.types.UnitId
import ru.kWow.widgets.ui.Color
import ru.kWow.api.Events
import ru.kWow.lua.LuaStringifier
import ru.kWow.api.WowApiStringifier
import ru.kWow.lua.toLua
import ru.kWow.widgets.UIParent
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
            setText(WowApiStringifier.Unit.UnitLevel(UnitId.PLAYER))
        }

        text(name = "lvlLabel", DrawLayer.ARTWORK, TextTemplate.GameFontNormal) {
            setPoint(Alignment.TOPLEFT, 25, 0)
            setText("Уровень".toLua())
        }

        val unitExpInfo = text(name = "unitExpInfo", DrawLayer.ARTWORK, TextTemplate.GameFontNormal) {
            setPoint(Alignment.TOPRIGHT)
            setText("0/0 %%0%".toLua())
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

            setBackdropColor(Color.fromHex("#000000", 0.5f))
            setBackdropBorderColor(Color.fromHex("#969696", 0.7f))

            statusBar = createFrame(FrameType.StatusBar, "statusBar") {
                setPoint(Alignment.CENTER)
                setSize(296, 6)

                setStatusBarTexture("Interface\\TargetingFrame\\UI-StatusBar".toLua())
                setStatusBarColor(Color.fromHex("#40888F"))
                setMinMaxValue()
            }
        }

        registerEvent(Events.PLAYER_LOGIN)
        registerEvent(Events.PLAYER_XP_UPDATE)
        registerEvent(Events.PLAYER_LEVEL_UP)
        registerEvent(Events.UPDATE_EXHAUSTION)
        setScript(ScriptType.Frame.OnEvent) {
            function {
                val currentXP = setVariable("currentXP", WowApiStringifier.Player.CurrentXp())
                val maxXP = setVariable("maxXP", WowApiStringifier.Player.XpMax())
                val percent = setVariable("percent", LuaStringifier.Math.floor("($currentXP / $maxXP) * 100"))
                val expValue = setVariable("expValue", "$currentXP / $maxXP")

                statusBar.callExtension("SetValue", expValue)
                playerLvl.callExtension("SetText", WowApiStringifier.Unit.UnitLevel(UnitId.PLAYER))
                unitExpInfo.callExtension("SetText", """"..$currentXP.."/"..$maxXP.." "..$percent.."%""".toLua())
            }
            build()
        }
    }
    return UIParent.buildComponent()
}