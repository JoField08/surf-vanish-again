package dev.slne.surf.vanish.velocity.command.spectatemode

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.kotlindsl.playerExecutor
import dev.jorel.commandapi.kotlindsl.subcommand
import dev.slne.surf.surfapi.core.api.font.toSmallCaps
import dev.slne.surf.surfapi.core.api.messages.adventure.buildText
import dev.slne.surf.surfapi.core.api.messages.adventure.sendText
import dev.slne.surf.vanish.core.service.spectateModeService
import dev.slne.surf.vanish.core.service.util.VanishPermissionRegistry
import dev.slne.surf.vanish.core.service.vanishService
import net.kyori.adventure.text.event.ClickEvent

class SpectateModeCommand(commandName: String) : CommandAPICommand(commandName) {
    init {
        withPermission(VanishPermissionRegistry.SPECTATE_MODE_COMMAND)
        subcommand(SpectateModeListCommand("list"))

        playerExecutor { player, _ ->
            if(spectateModeService.isSpectating(player.uniqueId)) {
                spectateModeService.setSpectating(player.uniqueId, false)
                player.sendText {
                    appendPrefix()
                    info("Du bist nun nicht mehr im Spectate Modus.")
                }
                return@playerExecutor
            }

            if(!vanishService.isVanished(player.uniqueId)) {
                player.sendText {
                    appendPrefix()
                    darkSpacer("*******************************************************")
                    appendNewline()
                    appendPrefix()
                    error("Du bist nicht im Vanish. Es ist empfohlen,".toSmallCaps())
                    appendNewline()
                    appendPrefix()
                    error("zuerst den Vanish Modus zu aktivieren.".toSmallCaps())
                    appendNewline()
                    appendPrefix()
                    spacer("Klicke hier, um den Spectate Modus")
                    appendNewline()
                    appendPrefix()
                    spacer("trotzdem zu aktivieren.")
                    hoverEvent(buildText {
                        error("Es ist nicht empfohlen, den Spectate Mode ohne Vanish zu nutzen.")
                    })
                    clickEvent(ClickEvent.callback {
                        spectateModeService.setSpectating(player.uniqueId, true)

                        player.sendText {
                            appendPrefix()
                            info("Du bist nun im Spectate Modus.")
                        }
                    })
                    appendNewline()
                    appendPrefix()
                    darkSpacer("*******************************************************")
                }
                return@playerExecutor
            }

            spectateModeService.setSpectating(player.uniqueId, true)

            player.sendText {
                appendPrefix()
                info("Du bist nun im Spectate Modus.")
            }
        }
    }
}