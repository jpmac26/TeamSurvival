package nmt.minecraft.TeamSurvival.Player;

import java.util.Collection;
import java.util.HashSet;

import org.bukkit.Location;

/**
 * A group of players.<br />
 * Teams are the the big operational block for the user part of our game. We rarely will operate on single players;
 * instead we'll use teams.
 * @author Skyler
 * @author William
 */
public class Team {
	
	/**
	 * Collection of the players that are part of this team
	 */
	private HashSet<SurvivalPlayer> players;
	
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
	public Team(String name, Collection<SurvivalPlayer> players) {
		this.name = name;
		this.players = new HashSet<SurvivalPlayer>(players);
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
		//For each player in this team...
		for (SurvivalPlayer p : this.players) {
			p.win();
		}
	}
	
	/**
	 * The counterpart to win.
	 * @see #win()
	 */
	public void lose() {
		//For each player in this team...
		for (SurvivalPlayer p : this.players) {
			p.lose();
		}
	}
	
	/**
	 * Moves a whole team to a specific location.<br />
	 * This can be the shop area, or the arena they're gonna fight monsters on
	 * @param location
	 */
	public void moveTo(Location location) {
		//For each player in this team...
		for (SurvivalPlayer p : this.players) {
			//This next command gets the Player and teleports them to 'location'
			p.getOfflinePlayer().getPlayer().teleport(location);
		}
	}
	
	
}
