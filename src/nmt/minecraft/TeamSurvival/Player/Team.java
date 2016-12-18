package nmt.minecraft.TeamSurvival.Player;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import nmt.minecraft.TeamSurvival.TeamLossEvent;
import nmt.minecraft.TeamSurvival.TeamSurvivalManager;
import nmt.minecraft.TeamSurvival.TeamSurvivalPlugin;
import nmt.minecraft.TeamSurvival.Session.GameSession;

/**
 * A group of players.<br />
 * Teams are the the big operational block for the user part of our game. We rarely will operate on single players;
 * instead we'll use teams.
 * @author Skyler
 * @author William
 */
public class Team implements Listener {
	
	private static int lastID = 0;
	
	/**
	 * Collection of the players that are part of this team.
	 */
	private HashSet<SurvivalPlayer> players;

	/**
	 * This team's name.
	 */
	private String name;
	
	private int ID;
	
	/**
	 * The location of the arena that this team belongs to.
	 */
	private Location arenaLocation;
	
	/**
	 * The location where the team will go to fight the boss.
	 */
	private Location bossLocation;
	
	private static int getNextID() {
		return ++lastID;
	}
	
	/**
	 * Main constructor for the Team class.
	 * @param name The name for the team
	 * @param players A collection of players that are associated with a team
	 * TODO We should probably somehow limit the size and character set of a team name.
	 */
	public Team(String name, Collection<SurvivalPlayer> players) {
		this.name = name;
		this.ID = Team.getNextID();
		this.players = new HashSet<SurvivalPlayer>(players);
		Bukkit.getPluginManager().registerEvents(this, 
				TeamSurvivalPlugin.plugin);
	}
	
	public void setArenaLocation(Location arenaLocation) {
		this.arenaLocation = arenaLocation;
	}
	
	public void setBossLocation(Location bossLocation) {
		this.bossLocation = bossLocation;
	}
	
	
	/**
	 * @return The Team's name
	 */
	public String getName() {
		return this.name;
	}
	

	/**
	 * This method adds a player to a team<.br />
	 * The player's pregame location is also set at this point.
	 * @param player the player to be added to the team
	 * @return true, if the player was successfully added to the Team
	 */
	public boolean addPlayer(SurvivalPlayer player) {
		player.setPreGameLocation(player.getPlayer().getLocation());
		if (player.getPlayer() != null) {
			player.getPlayer().setLevel(0);
			player.getPlayer().getActivePotionEffects().clear();
		}
		return this.players.add(player);
	}
	
	/**
	 * This method removes a player from a team.
	 * @param player The player to be removed
	 * @return <b>True</b> if the player was successfully removed<br>
	 * <b>False</b> if the player could not be removed or was not in the team to 
	 * begin with
	 */
	public boolean removePlayer(SurvivalPlayer player) {
		return this.players.remove(player);
	}
	
	/**
	 * Checks to see if a player is on this team.
	 * @param player the player to look for
	 * @return true if the player is on the team, false otherwise
	 */
	public boolean hasPlayer(SurvivalPlayer player){
		return players.contains(player);
	}
	
	/**
	 * Checks to see if a player is on this team.
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
	 * Should also return players to lobby, etc.
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
		
		HandlerList.unregisterAll(this);
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
	
	/**
	 * This private method checks to see if the team is dead.
	 * @return True if the team is dead.
	 */
	private boolean isTeamDead() {
		for (SurvivalPlayer s : this.players) {
			if (s.getPlayer().getGameMode() != GameMode.SPECTATOR) {
				return false;
			}
		}
		//All players are in spectator mode, team is dead
		return true;
	}
	
	/**
	 * This comparison method compares by team names.
	 */
	@Override
	public boolean equals(Object o){
		if(! (o instanceof Team)){
			return false;
		}
		
		return this.name.equals(((Team)o).name);
	}
	
	@Override
	public int hashCode() {
		return 886345 + this.ID * 7;
	}
	
	/**
	 * Returns the arena location.
	 * @return The location of the arena.
	 */
	public Location getArenaLocation() {
		return arenaLocation;
	}
	
	/**
	 * Returns whre the boss location is.<br />
	 * This is the location where the team will go to figh the boss and where the boss will spawn from.
	 * @return
	 */
	public Location getBossLocation() {
		return bossLocation;
	}
	
	/**
	 * This event handler checks to see if the Team is still alive<br>
	 * whenever a player dies.
	 * @param e The damage event
	 */
	@EventHandler
	public void entityDamagedEvent(EntityDamageEvent e) {
		//Check to see if the damage is actually valid
		if (e.isCancelled()) return;
		
		//Check to see if the entity being damaged is a player
		if (e.getEntity() instanceof Player) {
			//Check to see if the damage received is lethal
			double damage = e.getDamage();
			Player victim = (Player) e.getEntity();
			
			if (damage >= victim.getHealth() && this.hasPlayer((OfflinePlayer) victim) != null) {
				//Damage is lethal, prevent them from dying
				e.setCancelled(true);
				
				if (TeamSurvivalManager.getSession(this).getState() != GameSession.State.INWAVE) {
					return;
				}
				
				victim.setGameMode(GameMode.SPECTATOR);
				
				//Check to see if the team is dead
				if(this.isTeamDead()) {
					//Team is dead
					TeamLossEvent teamEvent = new TeamLossEvent(this);
					lose();
					Bukkit.getPluginManager().callEvent(teamEvent);
				}
			}
		}
	}
	
	public List<String> getPlayerList() {
		List<String> list = new LinkedList<String>();
		for(SurvivalPlayer player : players){
			list.add(player.getPlayer().getDisplayName());
		}
		return list;
	}
	
	public Set<SurvivalPlayer> getPlayers() {
		return players;
	}
	
	public void setGamemode(GameMode gamemode){
		for(SurvivalPlayer p : players){
			GameMode current = p.getPlayer().getGameMode();
			if(p.getPlayer()!=null && current != GameMode.CREATIVE && current != gamemode){
				p.setGamemode(gamemode);
			}
		}
	}
}
