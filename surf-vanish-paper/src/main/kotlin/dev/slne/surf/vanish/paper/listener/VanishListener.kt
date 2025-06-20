package dev.slne.surf.vanish.paper.listener

import com.github.retrooper.packetevents.PacketEvents
import com.github.retrooper.packetevents.protocol.chat.RemoteChatSession
import com.github.retrooper.packetevents.protocol.entity.data.EntityData
import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes
import com.github.retrooper.packetevents.protocol.entity.type.EntityTypes
import com.github.retrooper.packetevents.protocol.player.UserProfile
import com.github.retrooper.packetevents.protocol.world.Location
import com.github.retrooper.packetevents.util.Vector3d
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerDestroyEntities
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityMetadata
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerPlayerInfoRemove
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerPlayerInfoUpdate
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSpawnEntity
import dev.slne.surf.surfapi.bukkit.api.util.forEachPlayer
import dev.slne.surf.vanish.core.service.util.VanishPermissionRegistry
import dev.slne.surf.vanish.paper.util.toPacketEvents
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.messaging.PluginMessageListener
import java.io.ByteArrayInputStream
import java.io.DataInputStream
import java.util.UUID

class VanishListener : PluginMessageListener {
    override fun onPluginMessageReceived (
        channel: String,
        player: Player,
        message: ByteArray
    ) {
        if(channel != "surf-vanish:vanish-updates") {
            return
        }

        val input = DataInputStream(ByteArrayInputStream(message))
        val uuid = UUID.fromString(input.readUTF())
        val vanish = input.readBoolean()

        val player = Bukkit.getPlayer(uuid) ?: return

        if(vanish) {
            val destroyPacket = WrapperPlayServerDestroyEntities(
                player.entityId
            )
            val removePacket = WrapperPlayServerPlayerInfoRemove(
                player.uniqueId
            )

            forEachPlayer {
                if(it != player && !player.hasPermission(VanishPermissionRegistry.VANISH_BYPASS)) {
                    PacketEvents.getAPI().playerManager.sendPacket(it, destroyPacket)
                    PacketEvents.getAPI().playerManager.sendPacket(it, removePacket)
                }
            }
        } else {
            val infoPacket = WrapperPlayServerPlayerInfoUpdate(
                WrapperPlayServerPlayerInfoUpdate.Action.ADD_PLAYER,
                WrapperPlayServerPlayerInfoUpdate.PlayerInfo(
                    UserProfile(
                        player.uniqueId, player.name
                    ),
                    true,
                    player.ping,
                    player.gameMode.toPacketEvents(),
                    player.displayName(),
                    null
                )
            )
            val metaDataPacket = WrapperPlayServerEntityMetadata(
                player.entityId,
                listOf(
                    EntityData(17, EntityDataTypes.BYTE, 0x7F.toByte()),
                    EntityData(0, EntityDataTypes.BYTE, 0x02.toByte()),
                )
            )
            val spawnPacket = WrapperPlayServerSpawnEntity(
                player.entityId,
                player.uniqueId,
                EntityTypes.PLAYER,
                Location(
                    Vector3d(player.x, player.y, player.z),
                    player.yaw,
                    player.pitch
                ),
                player.yaw,
                0,
                Vector3d(
                    player.velocity.x,
                    player.velocity.y,
                    player.velocity.z
                )
            )

            forEachPlayer {
                if(it != player) {
                    PacketEvents.getAPI().playerManager.sendPacket(it, infoPacket)
                    PacketEvents.getAPI().playerManager.sendPacket(it, metaDataPacket)
                    PacketEvents.getAPI().playerManager.sendPacket(it, spawnPacket)
                }
            }
        }
    }
}