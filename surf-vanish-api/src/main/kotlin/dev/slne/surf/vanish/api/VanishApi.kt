package dev.slne.surf.vanish.api

import dev.slne.surf.surfapi.core.api.util.requiredService
import java.util.UUID

interface VanishApi {
    fun isVanished(uuid: UUID)
    fun setVanished(uuid: UUID, vanished: Boolean)

    fun canSee(uuid: UUID, canSee: UUID): Boolean

    companion object {
        val INSTANCE = requiredService<VanishApi>()
    }
}

val surfVanishApi get() = VanishApi.INSTANCE