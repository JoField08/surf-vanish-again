package dev.slne.surf.vanish.velocity.util

import com.github.retrooper.packetevents.PacketEvents
import com.github.retrooper.packetevents.wrapper.PacketWrapper
import com.velocitypowered.api.proxy.Player
import dev.slne.surf.vanish.api.player.VanishPlayer

fun Player.sendPacket(packet: PacketWrapper<*>) {
    PacketEvents.getAPI().playerManager.sendPacket(this, packet)
}

fun VanishPlayer.sendPacket(packet: PacketWrapper<*>) {
    PacketEvents.getAPI().playerManager.sendPacket(this, packet)
}