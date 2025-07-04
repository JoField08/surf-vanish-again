package dev.slne.surf.vanish.velocity.api

import com.google.auto.service.AutoService
import dev.slne.surf.vanish.api.VanishApi
import dev.slne.surf.vanish.core.service.util.VanishPermissionRegistry
import dev.slne.surf.vanish.core.service.vanishService
import dev.slne.surf.vanish.velocity.plugin
import net.kyori.adventure.util.Services
import reactor.core.publisher.zip
import java.util.UUID
import kotlin.jvm.optionals.getOrNull

@AutoService(VanishApi::class)
class VanishApiImpl : VanishApi, Services.Fallback {
    override fun isVanished(uuid: UUID): Boolean {
        return vanishService.isVanished(uuid)
    }

    override fun setVanished(uuid: UUID, vanished: Boolean) {
        vanishService.setVanished(uuid, vanished)
    }

    override fun canSee(uuid: UUID, canSee: UUID): Boolean {
        return !vanishService.isVanished(canSee) || plugin.proxy.getPlayer(uuid).getOrNull()?.hasPermission(VanishPermissionRegistry.VANISH_BYPASS) ?: false
    }
}