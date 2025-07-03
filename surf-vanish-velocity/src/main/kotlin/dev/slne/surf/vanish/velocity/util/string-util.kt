package dev.slne.surf.vanish.velocity.util

import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier

fun String.toPluginChannel(): MinecraftChannelIdentifier {
    return MinecraftChannelIdentifier.from(this)
}