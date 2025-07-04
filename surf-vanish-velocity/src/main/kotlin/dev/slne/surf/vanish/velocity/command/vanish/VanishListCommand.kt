package dev.slne.surf.vanish.velocity.command.vanish

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.kotlindsl.anyExecutor
import dev.jorel.commandapi.kotlindsl.playerExecutor
import dev.slne.surf.surfapi.core.api.messages.adventure.appendNewline
import dev.slne.surf.surfapi.core.api.messages.adventure.buildText
import dev.slne.surf.vanish.core.service.spectateModeService
import dev.slne.surf.vanish.core.service.util.VanishPermissionRegistry
import dev.slne.surf.vanish.core.service.vanishService
import dev.slne.surf.vanish.velocity.plugin
import kotlin.jvm.optionals.getOrNull

class VanishListCommand(commandName: String) : CommandAPICommand(commandName) {
    init {
        withPermission(VanishPermissionRegistry.SPECTATE_MODE_LIST)
        anyExecutor { sender, _ ->
            sender.sendMessage {//TODO: Replace with PageableMessageBuilder or Pagination System (surf-api)
                buildText {
                    appendPrefix()
                    info("Aktuell sind folgende Spieler im Vanish:")
                    appendNewline()

                    for (uuid in vanishService.getVanishedPlayers()) {
                        val player = plugin.proxy.getPlayer(uuid).getOrNull() ?: continue

                        primary(player.username)
                        spacer(" - ")
                        info(player.currentServer.getOrNull()?.serverInfo?.name ?: "Server unbekannt")
                    }
                }
            }
        }
    }
}