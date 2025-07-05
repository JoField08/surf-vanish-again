package dev.slne.surf.vanish.paper

import com.github.retrooper.packetevents.PacketEvents
import com.github.retrooper.packetevents.event.PacketListenerPriority
import com.github.shynixn.mccoroutine.folia.SuspendingJavaPlugin
import dev.slne.surf.vanish.core.service.util.PluginMessageChannels
import dev.slne.surf.vanish.paper.command.SurfVanishCommand
import dev.slne.surf.vanish.paper.listener.SilentChestListener
import dev.slne.surf.vanish.paper.listener.SpectateModeListener
import dev.slne.surf.vanish.paper.listener.VanishListener
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class PaperMain() : SuspendingJavaPlugin() {
    var placeholderApi = false

    override fun onEnable() {
        Bukkit.getMessenger().registerIncomingPluginChannel(
            this,
            PluginMessageChannels.VANISH_UPDATES,
            VanishListener())

        Bukkit.getMessenger().registerIncomingPluginChannel(
            this,
            PluginMessageChannels.SPECTATE_MODE_TELEPORTS,
            SpectateModeListener()
        )
        Bukkit.getMessenger().registerIncomingPluginChannel(
            this,
            PluginMessageChannels.SPECTATE_MODE_GLOW,
            SpectateModeListener()
        )
        Bukkit.getMessenger().registerIncomingPluginChannel(
            this,
            PluginMessageChannels.SILENT_CHEST,
            SilentChestListener()
        )

        VanishListener.load()

        placeholderApi = Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")

        SurfVanishCommand("vanishpaper").register()
        saveDefaultConfig()
    }
}

val plugin get() = JavaPlugin.getPlugin(PaperMain::class.java)