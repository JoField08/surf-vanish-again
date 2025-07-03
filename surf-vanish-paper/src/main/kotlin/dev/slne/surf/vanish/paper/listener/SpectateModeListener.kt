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
import dev.slne.surf.vanish.core.service.util.PluginMessageChannels
import dev.slne.surf.vanish.core.service.util.VanishPermissionRegistry
import dev.slne.surf.vanish.paper.util.toPacketEvents
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.messaging.PluginMessageListener
import java.io.ByteArrayInputStream
import java.io.DataInputStream
import java.util.UUID

class SpectateModeListener : PluginMessageListener {
    override fun onPluginMessageReceived (
        channel: String,
        player: Player,
        message: ByteArray
    ) {
        if(channel != PluginMessageChannels.SPECTATE_MODE_UPDATES) {
            return
        }

        val input = DataInputStream(ByteArrayInputStream(message))
        val uuid = UUID.fromString(input.readUTF())
        val target = UUID.fromString(input.readUTF())

        val player = Bukkit.getPlayer(uuid) ?: return
        val targetPlayer = Bukkit.getPlayer(target) ?: return

        player.teleportAsync(targetPlayer.location.clone().addRotation(0f, 0f))
    }
}