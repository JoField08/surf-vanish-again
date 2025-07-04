package dev.slne.surf.vanish.paper.command

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.kotlindsl.anyExecutor
import dev.slne.surf.surfapi.core.api.messages.adventure.sendText
import dev.slne.surf.vanish.core.service.util.VanishPermissionRegistry
import dev.slne.surf.vanish.paper.listener.VanishListener
import dev.slne.surf.vanish.paper.plugin
import kotlin.system.measureTimeMillis

class SurfVanishReloadCommand(commandName: String) : CommandAPICommand(commandName) {
    init {
        withPermission(VanishPermissionRegistry.VANISH_RELOAD)
        anyExecutor { executor, _ ->
            val ms = measureTimeMillis {
                plugin.reloadConfig()
                VanishListener.announceConnectionChange = plugin.config.getBoolean("announce-connection-change", true)
                VanishListener.vanishFormat = plugin.config.getString("vanish-format") ?: ""
                VanishListener.reappearFormat = plugin.config.getString("reappear-format") ?: ""
            }

            executor.sendText {
                appendPrefix()
                info("Successfully reloaded configuration ")
                spacer("(${ms}ms)")
                info("!")
            }
        }
    }
}