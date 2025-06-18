package dev.slne.surf.vanish.paper.util

import org.bukkit.GameMode

fun GameMode.toPacketEvents(): com.github.retrooper.packetevents.protocol.player.GameMode {
    return when (this) {
        GameMode.SURVIVAL -> com.github.retrooper.packetevents.protocol.player.GameMode.SURVIVAL
        GameMode.CREATIVE -> com.github.retrooper.packetevents.protocol.player.GameMode.CREATIVE
        GameMode.ADVENTURE -> com.github.retrooper.packetevents.protocol.player.GameMode.ADVENTURE
        GameMode.SPECTATOR -> com.github.retrooper.packetevents.protocol.player.GameMode.SPECTATOR
    }
}