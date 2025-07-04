package dev.slne.surf.vanish.paper.listener

import com.github.retrooper.packetevents.event.PacketListener
import com.github.retrooper.packetevents.event.PacketSendEvent
import com.github.retrooper.packetevents.protocol.entity.data.EntityData
import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes
import com.github.retrooper.packetevents.protocol.packettype.PacketType
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityMetadata
import dev.slne.surf.vanish.core.service.spectateModeService
import dev.slne.surf.vanish.paper.plugin
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import kotlin.experimental.or

class GlowingListener : PacketListener {
    override fun onPacketSend(event: PacketSendEvent) {
        if (event.packetType != PacketType.Play.Server.ENTITY_METADATA) {
            return
        }

        val packet = WrapperPlayServerEntityMetadata(event)
        val player = event.getPlayer<Player>()

        Bukkit.getScheduler().runTask(plugin, Runnable {
            val entity = player.getNearbyEntities(player.x, player.y, player.z)
                .find { it.entityId == packet.entityId } ?: return@Runnable

            if(!spectateModeService.hasSpectator(entity.uniqueId)) {
                return@Runnable
            }

            var foundFlags = false
            for (metaData in packet.entityMetadata) {
                if (metaData.index == 0) {
                    val flags = metaData.value as? Byte ?: 0x00

                    if ((flags.toInt() and 0x40) == 0) {
                        metaData.value = flags or 0x40.toByte()
                    }
                    foundFlags = true
                    break
                }
            }

            if (!foundFlags) {
                packet.entityMetadata.add(
                    EntityData(0, EntityDataTypes.BYTE, 0x40.toByte())
                )
            }
        })
    }

}