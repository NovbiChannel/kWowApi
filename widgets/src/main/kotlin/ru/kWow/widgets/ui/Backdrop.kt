package ru.kWow.widgets.ui

data class Backdrop(
    val bgFile: String = "",
    val edgeFile: String = "",
    val tile: Boolean = false,
    val tileEdge: Boolean = false,
    val tileSize: Int = 0,
    val edgeSize: Int = 0,
    val insets: Insets = Insets()
)
