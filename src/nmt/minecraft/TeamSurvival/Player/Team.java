package nmt.minecraft.TeamSurvival.Player;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;

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
	
	public List<String> getPlayerList() {
		List<String> list = new LinkedList<String>();
		for(SurvivalPlayer player : players){
			list.add(player.getPlayer().getDisplayName());
		}
		return list;
	}

	/**
	 * This team's name
	 */
	private String name;
	
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
	 * Returns a team's name.
	 * @return the Team's name.
	 */
	public String getName() {
		return this.name;
	}
	

	/**
	 * This method adds a player to a team.<br />
	 * The player's pregame location is also set at this point.
	 * @param player
	 * @return True if the player was successfully added to the Team.
	 */
	public boolean addPlayer(SurvivalPlayer player) {
		player.setPreGameLocation(player.getPlayer().getLocation());
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
		//this.moveTo(lobbyLocation);
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
		//this.moveTo(lobbyLocation);
	}
	
	/**
	 * This method sends a message to all players in the Team.
	 * @param message The message to send.
	 */
	public void sendTeamMessage(String message) {
		//For each player in this team
		for (SurvivalPlayer p : this.players) {
			p.sendMessage(message);
		}
	}
	
	/**
	 * This method plays a sound to all players in the Team with<br>
	 * default volumes and pitch.
	 * @param sound The sound to be played.
	 */
	public void sendTeamSound(Sound sound) {
		//For each player in this team...
		for (SurvivalPlayer p : this.players) {
			p.playSound(sound);
		}
	}
	
	/**
	 * This method plays a sound to all players in the Team with a<br>
	 * specified volume and pitch.
	 * @param sound The sound to be played.
	 * @param volume The volume of the sound (default value is 1).
	 * @param pitch The pitch change of the sound (default value is 0).
	 */
	public void sendTeamSound(Sound sound, float volume, float pitch) {
		//For each player in this team..
		for (SurvivalPlayer p : this.players) {
			p.playSound(sound, volume, pitch);
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
			p.teleportPlayer(location);
		}
	}
	
	
}
