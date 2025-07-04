package dev.slne.surf.vanish.paper.listener

import com.github.retrooper.packetevents.PacketEvents
import com.github.retrooper.packetevents.protocol.entity.data.EntityData
import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityMetadata
import dev.slne.surf.vanish.core.service.util.PluginMessageChannels
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.messaging.PluginMessageListener
import java.io.ByteArrayInputStream
import java.io.DataInputStream
import java.util.UUID

class SpectateModeListener : PluginMessageListener {
    override fun onPluginMessageReceived (
        channel: String,
        player: Player,
        message: ByteArray
    ) {
        when(channel) {
            PluginMessageChannels.SPECTATE_MODE_TELEPORTS -> {
                val input = DataInputStream(ByteArrayInputStream(message))
                val uuid = UUID.fromString(input.readUTF())
                val target = UUID.fromString(input.readUTF())

                val player = Bukkit.getPlayer(uuid) ?: return
                val targetPlayer = Bukkit.getPlayer(target) ?: return

                player.teleportAsync(targetPlayer.location.clone().addRotation(0f, 0f))
            }

            PluginMessageChannels.SPECTATE_MODE_GLOW -> {
                val input = DataInputStream(ByteArrayInputStream(message))

                val uuid = UUID.fromString(input.readUTF())
                val target = UUID.fromString(input.readUTF())
                val glow = input.readBoolean()

                val player = Bukkit.getPlayer(uuid) ?: return
                val targetPlayer = Bukkit.getPlayer(target) ?: return

                PacketEvents.getAPI().playerManager.sendPacket(player,
                    WrapperPlayServerEntityMetadata(
                        targetPlayer.entityId,
                        listOf(
                            EntityData(17, EntityDataTypes.BYTE, 0x7F.toByte()),
                            EntityData(0, EntityDataTypes.BYTE,  if (glow) 0x40.toByte() else 0x00.toByte()),
                        )
                    ))
            }
        }
    }
}