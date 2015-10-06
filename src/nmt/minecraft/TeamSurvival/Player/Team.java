package nmt.minecraft.TeamSurvival.Player;

import java.util.Collection;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

/**
 * A group of players.<br />
 * Teams are the the big operational block for the user part of our game. We rarely will operate on single players;
 * instead we'll use teams.
 * @author Skyler
 */
public class Team {
	
	/**
	 * Collection of the players that are part of this team
	 */
	private Collection<SurvivalPlayer> players;
	
	/**
	 * This team's name
	 */
	private String name;
	
	public Team(String name, Collection<SurvivalPlayer> players) {
		; //TODO
	}
	
	public String getName() {
		return name;
	}
	
	public boolean addPlayer(SurvivalPlayer player) {
		return players.add(player);
	}
	
	/**
	 * Checks to see if a player is on this team
	 * @param player the player to look for
	 * @return true if the player is on the team, false otherwise
	 */
	public boolean hasPlayer(SurvivalPlayer player){
		return players.contains(player);
	}
	
	/**
	 * Checks to see if a player is on this team
	 * @param player the player to look for
	 * @return will return the SurvivalPlayer if found, null otherwise
	 */
	public SurvivalPlayer hasPlayer(OfflinePlayer player){
		for(SurvivalPlayer p : players){
			if(p.getOfflinePlayer().equals(player)){
				return p;
			}
		}
		
		return null;
	}
	
	/**
	 * Mainly a glamour function. Does whatever we want the players see/experience when they win, like
	 * play a sound and display something in chat, etc. Lots of room for creativity here.<br />
	 * Should also return players to lobby, etc
	 */
	public void win() {
		; //TODO
	}
	
	/**
	 * The counterpart to win.
	 * @see #win()
	 */
	public void lose() {
		; //TODO
	}
	
	/**
	 * Moves a whole team to a specific location.<br />
	 * This can be the shop area, or the arena they're gonna fight monsters on
	 * @param location
	 */
	public void moveTo(Location location) {
		; //TODO
	}
	
	
}
