package dev.slne.surf.vanish.velocity.command.vanish

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.kotlindsl.playerExecutor
import dev.jorel.commandapi.kotlindsl.subcommand
import dev.slne.surf.surfapi.core.api.messages.adventure.sendText
import dev.slne.surf.vanish.core.service.util.VanishPermissionRegistry
import dev.slne.surf.vanish.core.service.vanishService

class VanishCommand(commandName: String) : CommandAPICommand(commandName) {
    init {
        withPermission(VanishPermissionRegistry.VANISH_COMMAND)
        subcommand(VanishListCommand("list"))
        playerExecutor { player, _ ->
            if(vanishService.isVanished(player.uniqueId)) {
                vanishService.setVanished(player.uniqueId, false)

                player.sendText {
                    appendPrefix()
                    info("Du bist nun nicht mehr im Vanish.")
                }
                return@playerExecutor
            }

            vanishService.setVanished(player.uniqueId, true)

            player.sendText {
                appendPrefix()
                info("Du bist nun im Vanish.")
            }
        }
    }
}