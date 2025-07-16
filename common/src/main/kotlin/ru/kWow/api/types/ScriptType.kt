package ru.kWow.api.types


sealed class ScriptType(val luaValue: String) {
    object Frame {
        /**
         * Invoked whenever an event fires for which the frame is registered.
         */
        data object OnEvent: ScriptType("OnEvent")
    }
    object Button {
        /**
         * Invoked when clicking a button.
         */
        data object OnClick: ScriptType("OnClick")

        /**
         * Invoked when double-clicking a button.
         */
        data object OnDoubleClick: ScriptType("OnDoubleClick")

        /**
         * Invoked immediately after `OnClick`.
         */
        data object PostClick: ScriptType("PostClick")

        /**
         * Invoked immediately before `OnClick`
         */
        data object PreClick: ScriptType("PreClick")
    }
}