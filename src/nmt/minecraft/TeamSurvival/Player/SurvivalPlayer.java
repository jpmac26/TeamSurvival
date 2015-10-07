package nmt.minecraft.TeamSurvival.Player;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

/**
 * A wrapper class for someone playing the game.<br />
 * @author Skyler
 * @author William
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
	 * TODO Add more 'winning' content
	 */
	protected void win() {
		//Send the Player a winning message
		this.sendMessage("Winning Message!");
	}
	
	/**
	 * @see Team#lose()
	 * TODO Add more 'losing' content
	 */
	protected void lose() {
		//Send the Player a winning message
		this.sendMessage("Losing Message!");
	}
	
	/**
	 * This method sends a message to the player.<br />
	 * If no player exists for this class then nothing is performed.
	 * @param message The string message to send to the player.
	 */
	protected void sendMessage(String message) {
		Player p = this.getPlayer();
		if (p != null) {
			p.sendMessage(message);
		}
	}
	
	/**
	 * This method plays a sound to the player using default pitch and volume.<br />
	 * If no player exists for this class then nothing is performed.
	 * @param sound The sound to be played.
	 */
	protected void playSound(Sound sound) {
		Player p = this.getPlayer();
		if (p != null) {
			p.playSound(p.getLocation(), sound, 1, 0);
		}
	}
	
	/**
	 * This method plays a sound to the player using the specified pitch and volume.<br />
	 * If no player exists for this class then nothing is performed.
	 * @param sound The sound to be played.
	 * @param volume The volume of the sound (default value is 1).
	 * @param pitch The pitch of the sound (default value is 0).
	 */
	protected void playSound(Sound sound, float volume, float pitch) {
		Player p = this.getPlayer();
		if (p != null) {
			p.playSound(p.getLocation(), sound, volume, pitch);
		}
	}
	
	/**
	 * This method teleports a player to a given location.<br />
	 * If no player exists for this class then nothing is performed.
	 * @param location The location to send the SurvivalPlayer to.
	 */
	protected void teleportPlayer(Location location) {
		Player p = this.getPlayer();
		if (p != null) {
			p.teleport(location);
		}
	}
	
	/**
	 * Looks up and returns the player wrapped by this object.<br />
	 * We have to do it this way in case someone logs off, then back in; their 'Player' id/reference to their Player is now broken.
	 * @return The OfflinePlayer associated with this player
	 * @note don't forget to make sure the player's online
	 * @see OfflinePlayer
	 */
	public OfflinePlayer getOfflinePlayer() {
		return Bukkit.getOfflinePlayer(this.playerID);
	}
	
	/**
	 * This method performs a UUID lookup with bukkit to return a Player class.<br />
	 * <b>WARNING</b>: This method returns <i>null</i> if no Player class could be returned.
	 * @return A the Player class or null if no player could be found.
	 */
	public Player getPlayer() {
		return Bukkit.getPlayer(this.playerID);
	}
	
}
