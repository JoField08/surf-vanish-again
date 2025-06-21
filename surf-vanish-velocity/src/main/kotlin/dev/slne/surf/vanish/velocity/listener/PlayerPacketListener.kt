package dev.slne.surf.vanish.velocity.listener

import com.github.retrooper.packetevents.event.PacketListener
import com.github.retrooper.packetevents.event.PacketSendEvent
import com.github.retrooper.packetevents.protocol.packettype.PacketType
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerPlayerInfoUpdate
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSpawnEntity

import com.velocitypowered.api.proxy.Player
import dev.slne.surf.surfapi.core.api.extensions.packetEvents
import dev.slne.surf.vanish.core.service.util.VanishPermissionRegistry
import dev.slne.surf.vanish.core.service.vanishService
import net.kyori.adventure.text.Component
import kotlin.jvm.optionals.getOrNull

class PlayerPacketListener : PacketListener {
    override fun onPacketSend(event: PacketSendEvent) {
        val user = event.user
        val player = event.getPlayer<Player>()

        when(event.packetType) {
            PacketType.Play.Server.PLAYER_INFO_UPDATE -> {
                val packet = WrapperPlayServerPlayerInfoUpdate(event)

                if(player.hasPermission(VanishPermissionRegistry.VANISH_BYPASS)) {
                    return
                }

                packet.entries.forEach {
                    if(vanishService.isVanished(it.profileId)) {
                        event.isCancelled = true
                    }
                }
            }

            PacketType.Play.Server.SPAWN_ENTITY -> {
                val packet = WrapperPlayServerSpawnEntity(event)
                val uuid = packet.uuid.getOrNull() ?: return

                if(player.hasPermission(VanishPermissionRegistry.VANISH_BYPASS)) {
                    return
                }

                if(vanishService.isVanished(uuid)) {
                    event.isCancelled = true
                }
            }

            else ->  {//DEBUG
                if(event.packetType == PacketType.Play.Server.SYSTEM_CHAT_MESSAGE) {
                    return
                }

                player.sendMessage(Component.text(event.packetType.toString()))
            }
        }
    }
}