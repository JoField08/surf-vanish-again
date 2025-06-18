package dev.slne.surf.vanish.velocity.util

import com.github.retrooper.packetevents.PacketEvents
import com.github.retrooper.packetevents.wrapper.PacketWrapper
import com.velocitypowered.api.proxy.Player

fun Player.sendPacket(packet: PacketWrapper<*>) {
    PacketEvents.getAPI().playerManager.sendPacket(this, packet)
}
