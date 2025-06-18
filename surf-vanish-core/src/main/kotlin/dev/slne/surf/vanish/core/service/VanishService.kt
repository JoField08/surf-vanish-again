package dev.slne.surf.vanish.core.service

import dev.slne.surf.surfapi.core.api.util.requiredService

interface VanishService {
    fun setVanished(uuid: String, vanished: Boolean)
    fun isVanished(uuid: String): Boolean
    fun canSee(uuid: String, canSee: String): Boolean

    companion object {
        val INSTANCE = requiredService<VanishService>()
    }
}

val vanishService get() = VanishService.INSTANCE