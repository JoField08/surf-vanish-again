package dev.slne.surf.vanish.core.service

import dev.slne.surf.surfapi.core.api.util.requiredService
import it.unimi.dsi.fastutil.objects.ObjectSet
import java.util.UUID

interface VanishService {
    fun setVanished(uuid: UUID, vanished: Boolean)
    fun isVanished(uuid: UUID): Boolean
    fun canSeeVanished(uuid: UUID): Boolean
    fun getVanishedPlayers(): ObjectSet<UUID>

    companion object {
        val INSTANCE = requiredService<VanishService>()
    }
}

val vanishService get() = VanishService.INSTANCE