package dev.slne.surf.vanish.velocity.service

import dev.slne.surf.surfapi.core.api.util.mutableObjectSetOf
import dev.slne.surf.vanish.api.player.VanishPlayer
import dev.slne.surf.vanish.core.service.VanishService

import org.gradle.internal.impldep.jcifs.dcerpc.UUID

class VanishServiceImpl : VanishService {
    val vanishedPlayers = mutableObjectSetOf<VanishPlayer>()

    override fun setVanished(uuid: UUID, vanished: Boolean) {
        TODO("Not yet implemented")
    }

    override fun isVanished(uuid: UUID): Boolean {
        TODO("Not yet implemented")
    }

    override fun canSee(uuid: UUID, other: UUID): Boolean {
        TODO("Not yet implemented")
    }
}