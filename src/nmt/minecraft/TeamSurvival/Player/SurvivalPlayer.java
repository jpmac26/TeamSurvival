package nmt.minecraft.TeamSurvival.Player;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

/**
 * A wrapper class for someone playing the game.<br />
 * @author Skyler
 *
 */
public class SurvivalPlayer {
	
	/**
	 * The UUID of the player we're following
	 */
	private UUID playerID;
	
	/**
	 * Creates a new wrapper survival player.<br />
	 * Since we only want one survival player per player, we make this private and use static getter
	 * @param player The player to wrap around
	 */
	private SurvivalPlayer(OfflinePlayer player) {
		//Only need the Player User ID
		this.playerID = player.getUniqueId();
	}
	
	/**
	 * @see Team#win()
	 */
	protected void win() {
		; //TODO
	}
	
	/**
	 * @see Team#lose()
	 */
	protected void lose() {
		; //TODO
	}
	
	
	/**
	 * Looks up and returns the player wrapped by this object.<br />
	 * We have to do it this way incase someone logs off, then back in; their 'Player' id/reference to their Player is now broken.
	 * @return The OfflinePlayer associated with this player
	 * @note don't forget to make sure the player's online
	 * @see OfflinePlayer
	 */
	public OfflinePlayer getOfflinePlayer() {
		return Bukkit.getOfflinePlayer(playerID);
	}
	
}
