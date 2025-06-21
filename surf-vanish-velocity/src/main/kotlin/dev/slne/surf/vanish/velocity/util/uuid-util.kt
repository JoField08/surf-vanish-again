package dev.slne.surf.vanish.velocity.util

import dev.slne.surf.vanish.velocity.plugin
import java.util.UUID
import kotlin.jvm.optionals.getOrNull

fun UUID.hasPermission(permission: String): Boolean {
    return plugin.proxy.getPlayer(this).getOrNull()?.hasPermission(permission) ?: false
}