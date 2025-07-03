package dev.slne.surf.vanish.velocity.listener

import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.connection.DisconnectEvent
import dev.slne.surf.vanish.core.service.spectateModeService

class PlayerConnectionListener {
    @Subscribe
    fun onDisconnect(event: DisconnectEvent) {
        val player = event.player

        spectateModeService.setSpectating(player.uniqueId, false)
    }
}