package dev.slne.surf.vanish.api

import dev.slne.surf.surfapi.core.api.util.requiredService
import it.unimi.dsi.fastutil.objects.ObjectSet
import java.util.UUID

/**
 * Interface representing the Vanish API.
 * Provides methods to manage and query the vanish state of players.
 */
interface VanishApi {

    /**
     * Checks if a player is vanished.
     *
     * @param uuid The UUID of the player to check.
     * @return `true` if the player is vanished, `false` otherwise.
     */
    fun isVanished(uuid: UUID): Boolean

    /**
     * Sets the vanish state of a player.
     *
     * @param uuid The UUID of the player.
     * @param vanished `true` to set the player as vanished, `false` to unvanish.
     */
    fun setVanished(uuid: UUID, vanished: Boolean)

    /**
     * Retrieves a set of UUIDs representing all vanished players.
     *
     * @return An `ObjectSet` containing the UUIDs of all vanished players.
     */
    fun getVanishedPlayers(): ObjectSet<UUID>

    /**
     * Determines if one player can see another player.
     *
     * @param uuid The UUID of the player attempting to see.
     * @param canSee The UUID of the player being checked for visibility.
     * @return `true` if the first player can see the second, `false` otherwise.
     */
    fun canSee(uuid: UUID, canSee: UUID): Boolean

    companion object {
        /**
         * Singleton instance of the Vanish API.
         * Automatically retrieves the required service implementation.
         */
        val INSTANCE = requiredService<VanishApi>()
    }
}

/**
 * Property to access the singleton instance of the Vanish API.
 */
val surfVanishApi get() = VanishApi.INSTANCE