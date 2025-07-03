package dev.slne.surf.vanish.velocity.command

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.kotlindsl.playerExecutor
import dev.slne.surf.vanish.core.service.util.VanishPermissionRegistry

class VanishCommand(commandName: String) : CommandAPICommand(commandName) {
    init {
        withPermission(VanishPermissionRegistry.VANISH_COMMAND)
        playerExecutor { player, args ->
            player.sendRichMessage("abc")
        }
    }
}