package dev.slne.surf.vanish.velocity.listener

import com.github.retrooper.packetevents.event.PacketListener
import com.github.retrooper.packetevents.event.PacketReceiveEvent
import com.github.retrooper.packetevents.event.PacketSendEvent
import com.github.retrooper.packetevents.protocol.packettype.PacketType
import com.github.retrooper.packetevents.protocol.player.DiggingAction
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientEntityAction
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerDigging
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerInput
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityEffect
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerPlayerInfoUpdate
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSpawnEntity

import com.velocitypowered.api.proxy.Player
import dev.slne.surf.surfapi.core.api.extensions.packetEvents
import dev.slne.surf.surfapi.core.api.util.mutableObject2ObjectMapOf
import dev.slne.surf.vanish.core.service.spectateModeService
import dev.slne.surf.vanish.core.service.util.VanishPermissionRegistry
import dev.slne.surf.vanish.core.service.vanishService
import net.kyori.adventure.text.Component
import java.util.UUID
import kotlin.jvm.optionals.getOrNull

class PlayerPacketListener : PacketListener {
    val sneakCache = mutableObject2ObjectMapOf<UUID, Long>()

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
        }
    }

    override fun onPacketReceive(event: PacketReceiveEvent) {
        val user = event.user
        val player = event.getPlayer<Player>()

        when(event.packetType) {
            PacketType.Play.Client.PLAYER_INPUT -> {
                val packet = WrapperPlayClientPlayerInput(event)

                when {
                    packet.isShift -> {
                        sneakCache[player.uniqueId] = System.currentTimeMillis()
                    }
                }
            }

            PacketType.Play.Client.PLAYER_DIGGING -> {
                val packet = WrapperPlayClientPlayerDigging(event)

                if(packet.action == DiggingAction.SWAP_ITEM_WITH_OFFHAND) {
                    if(!spectateModeService.isSpectating(player.uniqueId)) {
                        return
                    }

                    val sneakCacheResult = sneakCache[player.uniqueId]

                    if(sneakCacheResult != null && System.currentTimeMillis() - sneakCacheResult < 1000) {
                        spectateModeService.previousPlayer(player.uniqueId)
                        return
                    }

                    spectateModeService.nextPlayer(player.uniqueId)
                }
            }
        }
    }
}