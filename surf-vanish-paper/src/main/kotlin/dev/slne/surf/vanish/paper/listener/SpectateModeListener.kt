package dev.slne.surf.vanish.paper.listener

import com.github.retrooper.packetevents.PacketEvents
import com.github.retrooper.packetevents.event.PacketListener
import com.github.retrooper.packetevents.event.PacketSendEvent
import com.github.retrooper.packetevents.protocol.entity.data.EntityData
import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes
import com.github.retrooper.packetevents.protocol.packettype.PacketType
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityMetadata
import dev.slne.surf.vanish.core.service.spectateModeService
import dev.slne.surf.vanish.core.service.util.PluginMessageChannels
import dev.slne.surf.vanish.paper.plugin
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.messaging.PluginMessageListener
import java.io.ByteArrayInputStream
import java.io.DataInputStream
import java.util.UUID
import kotlin.experimental.or

class SpectateModeListener : PluginMessageListener, PacketListener {
    override fun onPacketSend(event: PacketSendEvent) {
        when(event.packetType) {
            PacketType.Play.Server.ENTITY_METADATA -> {
                val packet = WrapperPlayServerEntityMetadata(event)


                val entity = Bukkit.getWorlds()
                    .flatMap { it.entities }
                    .find { it.entityId == packet.entityId } ?: return

                for (metaData in packet.entityMetadata) {
                    if(metaData.index != 0) {
                        continue
                    }

                    if (spectateModeService.hasSpectator(entity.uniqueId)) {
                        val currentValue = (metaData.value as? Byte) ?: 0x00.toByte()
                        metaData.value = (currentValue or 0x40.toByte())
                    }
                }
            }
        }
    }

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
                            EntityData(0, EntityDataTypes.BYTE,  if (glow) 0x40.toByte() else 0x00.toByte()),
                        )
                    ))
            }
        }
    }
}