package dev.slne.surf.vanish.core.service

import dev.slne.surf.surfapi.core.api.util.requiredService
import java.util.UUID

interface VanishService {
    fun setVanished(uuid: UUID, vanished: Boolean)
    fun isVanished(uuid: UUID): Boolean
    fun canSee(uuid: UUID, other: UUID): Boolean

    companion object {
        val INSTANCE = requiredService<VanishService>()
    }
}

val vanishService get() = VanishService.INSTANCE