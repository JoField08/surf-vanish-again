package dev.slne.surf.vanish.velocity.service

import com.google.auto.service.AutoService

import dev.slne.surf.surfapi.core.api.util.mutableObjectSetOf
import dev.slne.surf.vanish.core.service.VanishService
import dev.slne.surf.vanish.core.service.util.VanishPermissionRegistry
import dev.slne.surf.vanish.velocity.channelIdentifier
import dev.slne.surf.vanish.velocity.plugin
import dev.slne.surf.vanish.velocity.util.hasPermission

import java.io.ByteArrayOutputStream
import java.io.DataOutputStream

import java.util.UUID
import kotlin.jvm.optionals.getOrNull

@AutoService(VanishService::class)
class VanishServiceImpl : VanishService {
    val vanishedPlayers = mutableObjectSetOf<UUID>()

    override fun setVanished(uuid: UUID, vanished: Boolean) {
        if (vanished) {
            vanishedPlayers.add(uuid)
        } else {
            vanishedPlayers.remove(uuid)
        }

        this.pushUpdate(uuid, vanished)
    }

    override fun isVanished(uuid: UUID): Boolean {
        return vanishedPlayers.contains(uuid)
    }

    override fun canSeeVanished(uuid: UUID): Boolean {
        return uuid.hasPermission(VanishPermissionRegistry.VANISH_BYPASS)
    }

    fun pushUpdate(uuid: UUID, vanished: Boolean) {
        val player = plugin.proxy.getPlayer(uuid).getOrNull() ?: return
        val server = player.currentServer.getOrNull() ?: return

        val outputStream = ByteArrayOutputStream()
        val dataOutput = DataOutputStream(outputStream)

        dataOutput.writeUTF(uuid.toString())
        dataOutput.writeBoolean(vanished)

        server.sendPluginMessage(channelIdentifier, outputStream.toByteArray())
    }
}