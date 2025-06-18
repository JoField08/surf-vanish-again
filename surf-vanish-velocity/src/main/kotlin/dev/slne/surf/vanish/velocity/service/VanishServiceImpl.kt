package dev.slne.surf.vanish.velocity.service

import dev.slne.surf.surfapi.core.api.util.mutableObjectSetOf
import dev.slne.surf.vanish.core.service.VanishService
import java.util.UUID


class VanishServiceImpl : VanishService {
    val vanishedPlayers = mutableObjectSetOf<UUID>()

    override fun setVanished(uuid: UUID, vanished: Boolean) {
        TODO("Not yet implemented")
    }

    override fun isVanished(uuid: UUID): Boolean {
        return vanishedPlayers.contains(uuid)
    }

    override fun canSee(uuid: UUID, other: UUID): Boolean {
        TODO("Not yet implemented")
    }
}