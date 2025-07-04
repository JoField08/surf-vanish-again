package dev.slne.surf.vanish.paper.listener

import com.github.retrooper.packetevents.PacketEvents
import com.github.retrooper.packetevents.protocol.entity.data.EntityData
import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes
import com.github.retrooper.packetevents.protocol.entity.type.EntityTypes
import com.github.retrooper.packetevents.protocol.player.TextureProperty
import com.github.retrooper.packetevents.protocol.player.UserProfile
import com.github.retrooper.packetevents.protocol.world.Location
import com.github.retrooper.packetevents.util.Vector3d
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerDestroyEntities
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityMetadata
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityTeleport
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerPlayerInfoRemove
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerPlayerInfoUpdate
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSpawnEntity
import dev.slne.surf.surfapi.bukkit.api.hook.papi.expansion.PapiExpansion
import dev.slne.surf.surfapi.bukkit.api.hook.papi.expansion.PapiPlaceholder
import dev.slne.surf.surfapi.bukkit.api.util.forEachPlayer
import dev.slne.surf.surfapi.core.api.messages.adventure.sendText
import dev.slne.surf.surfapi.core.api.util.mutableObjectListOf
import dev.slne.surf.vanish.core.service.util.PluginMessageChannels
import dev.slne.surf.vanish.core.service.util.VanishPermissionRegistry
import dev.slne.surf.vanish.paper.plugin
import dev.slne.surf.vanish.paper.util.skinDataDefault
import dev.slne.surf.vanish.paper.util.toPacketEvents
import dev.slne.surf.vanish.paper.util.toTextureProperty
import io.github.retrooper.packetevents.util.SpigotConversionUtil
import me.clip.placeholderapi.PlaceholderAPI
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.messaging.PluginMessageListener
import java.io.ByteArrayInputStream
import java.io.DataInputStream
import java.util.EnumSet
import java.util.Optional
import java.util.UUID

class VanishListener : PluginMessageListener {
    override fun onPluginMessageReceived (
        channel: String,
        player: Player,
        message: ByteArray
    ) {
        if(channel != PluginMessageChannels.VANISH_UPDATES) {
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

            player.isSleepingIgnored = true

            forEachPlayer {
                if(it != player && !it.hasPermission(VanishPermissionRegistry.VANISH_BYPASS)) {
                    PacketEvents.getAPI().playerManager.sendPacket(it, destroyPacket)
                    PacketEvents.getAPI().playerManager.sendPacket(it, removePacket)

                    if(announceConnectionChange) {
                        if(plugin.placeholderApi) {
                            player.sendMessage(MiniMessage.miniMessage().deserialize(PlaceholderAPI.setPlaceholders(it, vanishFormat)))
                        } else {
                            player.sendMessage(MiniMessage.miniMessage().deserialize(vanishFormat.replace("%player_name%", player.name)))
                        }
                    }
                }
            }
        } else {
            val profile = UserProfile(
                player.uniqueId, player.name
            )

            profile.textureProperties.addAll(player.playerProfile.properties.map { it.toTextureProperty() })

            val addPacket = WrapperPlayServerPlayerInfoUpdate(
                WrapperPlayServerPlayerInfoUpdate.Action.ADD_PLAYER,
                WrapperPlayServerPlayerInfoUpdate.PlayerInfo(
                    profile,
                    true,
                    player.ping,
                    player.gameMode.toPacketEvents(),
                    player.displayName(),
                    null
                )
            )

            val spawnPacket = WrapperPlayServerSpawnEntity(
                player.entityId,
                Optional.of(player.uniqueId),
                EntityTypes.PLAYER,
                Vector3d(player.location.x, player.location.y, player.location.z),
                player.pitch,
                player.yaw,
                player.yaw,
                0,
                Optional.of(Vector3d(player.velocity.x, player.velocity.y, player.velocity.z))
            )
            val teleportPacket = WrapperPlayServerEntityTeleport(
                player.entityId,
                Vector3d(player.x, player.y, player.z),
                player.yaw,
                player.pitch,
                false
            )
            val metaDataPacket = WrapperPlayServerEntityMetadata(
                player.entityId,
                listOf(
                    EntityData(17, EntityDataTypes.BYTE, 0x7F.toByte()),
                    EntityData(0, EntityDataTypes.BYTE, 0x02.toByte()),
                )
            )

            forEachPlayer {
                if(it != player && !it.hasPermission(VanishPermissionRegistry.VANISH_BYPASS)) {
                    PacketEvents.getAPI().playerManager.sendPacket(it, addPacket)
                    PacketEvents.getAPI().playerManager.sendPacket(it, spawnPacket)
                    PacketEvents.getAPI().playerManager.sendPacket(it, metaDataPacket)
                    PacketEvents.getAPI().playerManager.sendPacket(it, teleportPacket)


                    if(announceConnectionChange) {
                        if(plugin.placeholderApi) {
                            player.sendMessage(MiniMessage.miniMessage().deserialize(PlaceholderAPI.setPlaceholders(it, reappearFormat)))
                        } else {
                            player.sendMessage(MiniMessage.miniMessage().deserialize(reappearFormat.replace("%player_name%", player.name)))
                        }
                    }
                }
            }
        }
    }

    companion object {
        var announceConnectionChange: Boolean = true
        var vanishFormat: String = ""
        var reappearFormat: String = ""

        fun load() {
            announceConnectionChange = plugin.config.getBoolean("announce-connection-change", true)
            vanishFormat = plugin.config.getString("vanish-format") ?: ""
            reappearFormat = plugin.config.getString("reappear-format") ?: ""
        }
    }
}