package dev.slne.surf.vanish.core.service.player

import dev.slne.surf.vanish.api.player.VanishPlayer
import java.util.UUID

class VanishPlayerImpl(
    override val uuid: UUID,
    override val name: String,
    override var vanished: Boolean
) : VanishPlayer {
    override fun canSee(other: UUID): Boolean {
        TODO("Not yet implemented")
    }

    override fun vanish() {
        TODO("Not yet implemented")
    }

    override fun reAppear() {
        TODO("Not yet implemented")
    }
}