package dev.slne.surf.vanish.paper.listener

import dev.slne.surf.vanish.core.service.spectateModeService
import dev.slne.surf.vanish.core.service.vanishService
import org.bukkit.block.Container
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

class PlayerInteractListener : Listener {
    @EventHandler
    fun onContainerOpen(event: PlayerInteractEvent) {
        if(event.clickedBlock == null) {
            return
        }

        val block = event.clickedBlock ?: return
        val player = event.player

        if(block !is Container) {
            return
        }

        val container = block as Container

        if(!vanishService.isVanished(player.uniqueId) && !spectateModeService.isSpectating(player.uniqueId)) {
            return
        }

        event.isCancelled = true

        player.openInventory(container.inventory)
    }
}