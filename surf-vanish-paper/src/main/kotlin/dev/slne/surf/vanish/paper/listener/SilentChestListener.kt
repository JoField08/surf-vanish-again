package dev.slne.surf.vanish.paper.listener

import dev.slne.surf.vanish.core.service.util.PluginMessageChannels

import org.bukkit.Bukkit
import org.bukkit.block.Container
import org.bukkit.entity.Player
import org.bukkit.plugin.messaging.PluginMessageListener

import java.io.ByteArrayInputStream
import java.io.DataInputStream
import java.util.UUID

class SilentChestListener : PluginMessageListener {
    override fun onPluginMessageReceived (
        channel: String,
        player: Player,
        message: ByteArray
    ) {
        when(channel) {
            PluginMessageChannels.SILENT_CHEST -> {
                val input = DataInputStream(ByteArrayInputStream(message))
                val uuid = UUID.fromString(input.readUTF())
                val x = input.readInt()
                val y = input.readInt()
                val z = input.readInt()

                val player = Bukkit.getPlayer(uuid) ?: return
                val block = player.world.getBlockAt(x, y, z)
                val container = block.state as? Container ?: return

                player.openInventory(container.inventory)
            }
        }
    }
}