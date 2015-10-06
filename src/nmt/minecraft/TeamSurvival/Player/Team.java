package nmt.minecraft.TeamSurvival.Player;

import java.util.Collection;
import java.util.HashSet;

import org.bukkit.Location;

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
	
	/**
	 * Main constructor for the Team Class.
	 * @param name The name for the Team
	 * @param players A collection of players that are associated with a team
	 * TODO We should probably somehow limit the size and character set of a team name.
	 */
	public Team(String name, HashSet<SurvivalPlayer> players) {
		this.name = name;
	}
	
	/**
	 * Returns a team's name.
	 * @return the Team's name.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * This method adds a player to a team.
	 * @param player
	 * @return True if the player was successfully added to the Team.
	 */
	public boolean addPlayer(SurvivalPlayer player) {
		return players.add(player);
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
