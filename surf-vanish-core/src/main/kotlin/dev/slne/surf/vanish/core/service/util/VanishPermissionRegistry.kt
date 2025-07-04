package dev.slne.surf.vanish.core.service.util

object VanishPermissionRegistry {
    const val PREFIX = "surf.vanish"

    const val VANISH_RELOAD = "$PREFIX.reload"

    const val VANISH_COMMAND = "$PREFIX.command"
    const val VANISH_BYPASS = "$PREFIX.bypass"
    const val VANISH_TOGGLE = "$PREFIX.toggle"
    const val VANISH_ENABLE = "$PREFIX.enable"
    const val VANISH_DISABLE = "$PREFIX.disable"
    const val VANISH_LIST = "$PREFIX.list"

    const val SPECTATE_MODE_COMMAND = "$PREFIX.spectatemode.command"
    const val SPECTATE_MODE_LIST = "$PREFIX.spectatemode.list"
    const val SPECTATE_MODE_TOGGLE = "$PREFIX.spectatemode.toggle"
    const val SPECTATE_MODE_NEXT = "$PREFIX.spectatemode.next"
    const val SPECTATE_MODE_PREVIOUS = "$PREFIX.spectatemode.previous"
    const val SPECTATE_MODE_VIEW_INVENTORY = "$PREFIX.spectatemode.inventory"
    const val SPECTATE_MODE_BYPASS = "$PREFIX.spectatemode.bypass"
}