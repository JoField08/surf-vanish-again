package dev.slne.surf.vanish.velocity.util

import com.github.retrooper.packetevents.PacketEvents
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerDestroyEntities
import com.velocitypowered.api.proxy.Player
import dev.slne.surf.vanish.api.player.VanishPlayer
import dev.slne.surf.vanish.core.service.player.VanishPlayerImpl
import kotlin.jvm.optionals.getOrNull

fun Player.vanish(): VanishPlayer {
    val packetEvents = PacketEvents.getAPI()


    return VanishPlayerImpl(
        uuid = this.uniqueId,
        name = this.username,
        vanished = true
    )
}

fun Player.reAppear(): VanishPlayer {
    val packetEvents = PacketEvents.getAPI()


    return VanishPlayerImpl(
        uuid = this.uniqueId,
        name = this.username,
        vanished = true
    )
}