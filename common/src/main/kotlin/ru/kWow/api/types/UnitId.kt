package ru.kWow.api.types

import ru.kWow.lua.toLua

/**
 * A `unitId` identifies a unit by their relationship to the player as a target, party member, pet, or other such role. Several API functions accept unitId as an argument for whom the action applies, though some functions only accept a subset of possible values.
 */
sealed class UnitId(val luaValue: String) {
    /**
     * The current player's focus target as selected by the /focus command.
     */
    data object FOCUS: UnitId("focus".toLua())

    /**
     * The unit which the mouse is currently (or was most recently) hovering over.
     */
    data object MOUSEOVER: UnitId("mouseover".toLua())

    /**
     * A valid unit token that always refers to no unit. UnitName will return "Unknown, nil" for this UnitID.
     */
    data object NONE: UnitId("none".toLua())

    /**
     * The current player's pet.
     */
    data object PET: UnitId("pet".toLua())

    /**
     * The current player.
     */
    data object PLAYER: UnitId("player".toLua())

    /**
     * The currently targeted unit. May be overridden in macros by unit specified as a value of respective Secure Button attribute.
     */
    data object TARGET: UnitId("target".toLua())

    /**
     * The current player's vehicle.
     */
    data object VEHICLE: UnitId("vehicle".toLua())

    /**
     * Opposing arena member with index N (1,2,3,4 or 5).
     */
    data class ArenaN(val index: Int): UnitId("arena$index".toLua()) {
        init {
            require(index in 1..5) { "Arena index must be between 1 and 5, got $index" }
        }
    }

    /**
     * The active bosses of the current encounter if available N (1,2,3...,8).
     */
    data class BossN(val index: Int): UnitId("boss$index".toLua()) {
        init {
            require(index in 1..8) { "Boss index must be between 1 and 8, got $index" }
        }
    }

    /**
     * The Nth party member excluding the player (1,2,3 or 4).
     */
    data class PartyN(val index: Int): UnitId("party$index".toLua()) {
        init {
            require(index in 1..4) { "Party index must be between 1 and 4, got $index" }
        }
    }

    /**
     * The pet of the Nth party member (N is 1,2,3, or 4).
     */
    data class PartyPetN(val index: Int): UnitId("partypet$index".toLua()) {
        init {
            require(index in 1..4) { "PartyPet index must be between 1 and 4, got $index" }
        }
    }

    /**
     * The raid member with raidIndex N (1,2,3,...,40).
     */
    data class RaidN(val index: Int): UnitId("raid$index".toLua()) {
        init {
            require(index in 1..40) { "Raid index must be between 1 and 40, got $index" }
        }
    }

    /**
     * The pet of the raid member with raidIndex N (1,2,3,...,40)
     */
    data class RaidPetN(val index: Int): UnitId("raidpet$index".toLua()) {
        init {
            require(index in 1..40) { "RaidPet index must be between 1 and 40, got $index" }
        }
    }
}