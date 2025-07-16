package ru.kWow.api.types

sealed class PowerType(val luaValue: String) {
    data object Mana: PowerType("0")
    data object Rage: PowerType("1")
    data object Focus: PowerType("2")
    data object Energy: PowerType("3")
    data object ComboPoints: PowerType("4")
    data object Runes: PowerType("5")
    data object RunicPower: PowerType("6")
    data object SoulShards: PowerType("7")
    data object HolyPower: PowerType("9")
}