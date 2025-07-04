package dev.slne.surf.vanish.api

import dev.slne.surf.surfapi.core.api.util.requiredService
import it.unimi.dsi.fastutil.objects.ObjectSet
import java.util.UUID

interface VanishApi {
    fun isVanished(uuid: UUID): Boolean
    fun setVanished(uuid: UUID, vanished: Boolean)
    fun getVanishedPlayers(): ObjectSet<UUID>

    fun canSee(uuid: UUID, canSee: UUID): Boolean

    companion object {
        val INSTANCE = requiredService<VanishApi>()
    }
}

val surfVanishApi get() = VanishApi.INSTANCE