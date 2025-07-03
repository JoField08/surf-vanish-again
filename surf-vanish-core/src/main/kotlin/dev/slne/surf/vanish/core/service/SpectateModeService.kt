package dev.slne.surf.vanish.core.service

import dev.slne.surf.surfapi.core.api.util.requiredService
import it.unimi.dsi.fastutil.objects.ObjectSet
import java.util.UUID

interface SpectateModeService {
    fun startJob()

    fun isSpectating(uuid: UUID): Boolean
    fun setSpectating(uuid: UUID, spectating: Boolean)
    fun getSpectators(): ObjectSet<UUID>

    fun nextPlayer(uuid: UUID): UUID?
    fun previousPlayer(uuid: UUID): UUID?

    fun viewInventory(uuid: UUID, target: UUID)

    companion object {
        val INSTANCE = requiredService<SpectateModeService>()
    }
}

val spectateModeService get() = SpectateModeService.INSTANCE