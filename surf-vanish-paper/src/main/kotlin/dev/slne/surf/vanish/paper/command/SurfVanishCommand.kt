package dev.slne.surf.vanish.paper.command

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.kotlindsl.subcommand
import dev.slne.surf.vanish.core.service.util.VanishPermissionRegistry

class SurfVanishCommand(commandName: String) : CommandAPICommand(commandName) {
    init {
        withPermission(VanishPermissionRegistry.VANISH_COMMAND)
        subcommand(SurfVanishReloadCommand("reload"))
    }
}