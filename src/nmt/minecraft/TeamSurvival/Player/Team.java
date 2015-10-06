package nmt.minecraft.TeamSurvival.Player;

import java.util.Collection;
import java.util.HashSet;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

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
	 * This is the lobby location for the team.
	 */
	private Location lobbyLocation;
	
	/**
	 * Main constructor for the Team Class.
	 * @param name The name for the team.
	 * @param players A collection of players that are associated with a team.
	 * TODO We should probably somehow limit the size and character set of a team name.
	 */
	public Team(String name, Collection<SurvivalPlayer> players) {
		this.name = name;
		this.players = new HashSet<SurvivalPlayer>(players);
	}
	
	/**
	 * A secondary constructor where the lobbyLocation can be specified.
	 * @param name The name for the team.
	 * @param players A collection of players that are associated with a team.
	 * @param lobbyLocation The location of the lobby. Players are teleported
	 * here when the game ends.
	 */
	public Team(String name, Collection<SurvivalPlayer> players, Location lobbyLocation) {
		this.name = name;
		this.players = new HashSet<SurvivalPlayer>(players);
		this.lobbyLocation = lobbyLocation;
	}
	
	/**
	 * This method sets the team's lobby location
	 * @param lobbyLocation The location of the lobby. Players are teleported
	 * here when the game ends.
	 */
	public void setLobbyLocation(Location lobbyLocation) {
		this.lobbyLocation = lobbyLocation;
	}
	
	/**
	 * Returns a team's name.
	 * @return the Team's name.
	 */
	public String getName() {
		return this.name;
	}
	

	/**
	 * This method adds a player to a team.
	 * @param player
	 * @return True if the player was successfully added to the Team.
	 */
	public boolean addPlayer(SurvivalPlayer player) {
		return this.players.add(player);
	}
	
	/**
	 * This method removes a player from a team.
	 * @param player The player to be removed.
	 * @return <b>True</b> if the player was successfully removed.<br>
	 * <b>False</b> if the player could not be removed or was not in the team to 
	 * begin with.
	 */
	public boolean removePlayer(SurvivalPlayer player) {
		return this.players.remove(player);
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
		//For each player in this team...
		for (SurvivalPlayer p : this.players) {
			p.win();
		}
		//Teleport all players
		this.moveTo(lobbyLocation);
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
		//Teleport all players
		this.moveTo(lobbyLocation);
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
