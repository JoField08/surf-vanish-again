package dev.slne.surf.vanish.paper

import com.github.shynixn.mccoroutine.folia.SuspendingJavaPlugin
import dev.slne.surf.vanish.core.service.util.PluginMessageChannels
import dev.slne.surf.vanish.paper.command.SurfVanishCommand
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
            PluginMessageChannels.SPECTATE_MODE_UPDATES,
            SpectateModeListener()
        )
        VanishListener.load()

        placeholderApi = Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")

        SurfVanishCommand("vanishpaper").register()
        saveDefaultConfig()
    }
}

val plugin get() = JavaPlugin.getPlugin(PaperMain::class.java)