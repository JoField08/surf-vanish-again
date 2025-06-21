package dev.slne.surf.vanish.paper

import com.github.shynixn.mccoroutine.folia.SuspendingJavaPlugin
import dev.slne.surf.vanish.paper.listener.VanishListener
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class PaperMain() : SuspendingJavaPlugin() {
    override fun onEnable() {
        Bukkit.getMessenger().registerIncomingPluginChannel(
            this,
            "surf-vanish:vanish-updates",
            VanishListener())
    }
}

val plugin get() = JavaPlugin.getPlugin(PaperMain::class.java)