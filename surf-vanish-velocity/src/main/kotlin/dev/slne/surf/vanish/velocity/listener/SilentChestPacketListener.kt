package dev.slne.surf.vanish.velocity.listener

import com.github.retrooper.packetevents.event.PacketListener
import com.github.retrooper.packetevents.event.PacketReceiveEvent
import com.github.retrooper.packetevents.protocol.packettype.PacketType
import com.github.retrooper.packetevents.protocol.player.DiggingAction
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerDigging
import com.velocitypowered.api.proxy.Player
import dev.slne.surf.vanish.core.service.spectateModeService
import dev.slne.surf.vanish.core.service.util.PluginMessageChannels
import dev.slne.surf.vanish.velocity.plugin
import dev.slne.surf.vanish.velocity.util.toPluginChannel
import java.io.ByteArrayOutputStream
import java.io.DataOutputStream
import java.util.UUID
import kotlin.jvm.optionals.getOrNull

class SilentChestPacketListener : PacketListener {
    override fun onPacketReceive(event: PacketReceiveEvent) {
        if(event.packetType != PacketType.Play.Client.PLAYER_DIGGING) {
            return
        }

        val player = event.getPlayer<Player>()
        val packet = WrapperPlayClientPlayerDigging(event)

        if(packet.action != DiggingAction.RELEASE_USE_ITEM) {
            return
        }

        if(!spectateModeService.isSpectating(player.uniqueId)) {
            return
        }

        this.pushInventoryOpening(player.uniqueId, packet.blockPosition.x, packet.blockPosition.y, packet.blockPosition.z)
    }

    fun pushInventoryOpening(uuid: UUID, x: Int, y: Int, z: Int) {
        val player = plugin.proxy.getPlayer(uuid).getOrNull() ?: return
        val server = player.currentServer.getOrNull() ?: return

        val outputStream = ByteArrayOutputStream()
        val dataOutput = DataOutputStream(outputStream)

        dataOutput.writeUTF(uuid.toString())
        dataOutput.writeInt(x)
        dataOutput.writeInt(y)
        dataOutput.writeInt(z)

        server.sendPluginMessage(PluginMessageChannels.SILENT_CHEST.toPluginChannel(), outputStream.toByteArray())
    }
}