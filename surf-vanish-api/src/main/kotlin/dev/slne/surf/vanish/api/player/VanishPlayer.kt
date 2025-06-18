package dev.slne.surf.vanish.api.player

import java.util.UUID

interface VanishPlayer {
    val uuid: UUID
    val name: String

    var vanished: Boolean
    fun canSee(other: UUID): Boolean
}