package nmt.minecraft.TeamSurvival.Session;

import java.util.Collection;
import java.util.LinkedList;

import org.bukkit.OfflinePlayer;

import nmt.minecraft.TeamSurvival.TeamSurvivalPlugin;
import nmt.minecraft.TeamSurvival.IO.ChatFormat;
import nmt.minecraft.TeamSurvival.Map.Map;
import nmt.minecraft.TeamSurvival.Player.SurvivalPlayer;
import nmt.minecraft.TeamSurvival.Player.Team;
import nmt.minecraft.TeamSurvival.Shop.Shop;

/**
 * Holds a single game session.<br />
 * Game sessions will hold the current state of things, the teams involved, etc
 * @author Skyler
 *
 */
public class GameSession {
	
	public enum State {
		PREGAME,
		STARTINGPERIOD,
		INWAVE,
		INSHOP,
		FINISHED;
	}
	
	private Collection<Team> teams;
	
	private State state;
	
	/**
	 * Keep a name so we can reference the game session
	 */
	private String name;
	
	/**
	 * The map with the arenas and the shop
	 */
	private Map map;
	
	/**
	 * The shop instance that's unique to this session
	 */
	private Shop sessionShop;
	
	public GameSession(String name, Map map) {
		this.name = name;
		this.map = map;
		this.state = State.PREGAME;
		this.teams = new LinkedList<Team>();
	}
	
	/**
	 * Starts the game, dealing with the teams and scores, etc
	 */
	public void start() {
		/*
		 * Teleport teams to their positions
		 * create waves for each player
		 * start the wave
		 */
		if (teams.size() == 0) {
			TeamSurvivalPlugin.plugin.getLogger().warning(
					ChatFormat.ERROR.wrap("Unable to start session, as there are no teams!"));
			return;
		}
		
		if (state != State.PREGAME) {
			TeamSurvivalPlugin.plugin.getLogger().warning(
					ChatFormat.ERROR.wrap("Unable to start session, as it's already been started!"));
		}
		
		state = State.STARTINGPERIOD;
		; //TODO
	}
	
	/**
	 * Stops the game.<br />
	 * Games stop automatically, so this method is considered an emergency operation.
	 */
	public void stop() {
		
	}
	
	/**
	 * @return The current state of the session
	 */
	public State getState() {
		return state;
	}
	
	/**
	 * Adds a team to the session
	 * @param team
	 */
	public void addTeam(Team team) {
		if (teams.contains(team)) {
			return;
		}
		
		if (teams.size() >= map.getArenaLocations().size()) {
			TeamSurvivalPlugin.plugin.getLogger().warning("Unable to add class: session is full!");
			return;
		}
		
		teams.add(team);
	}
	
	/**
	 * Tries to look up a team
	 * @param name The name to look up
	 * @return The team with the given name, null if it cannot be found
	 */
	public Team getTeam(String name) {
		if (teams.isEmpty()) {
			return null;
		}
		
		for(Team t : teams){
			if(t.getName().equals(name)){
				return t;
			}
		}
		return null;
	}
	
	/**
	 * Tries to look up a team
	 * @param player the player to look up
	 * @return The team the player is on, null if the player is not on a team.
	 */
	public Team getTeam(SurvivalPlayer player) {
		if (teams.isEmpty()) {
			return null;
		}
		
		for(Team t : teams){
			if(t.hasPlayer(player)){
				return t;
			}
		}
		
		return null;
	}
	
	/**
	 * Tries to look up a team
	 * @param player the player to look up
	 * @return The team the player is on, null if the player is not on a team.
	 */
	public Team getTeam(OfflinePlayer player) {
		if (teams.isEmpty()) {
			return null;
		}
		
		for(Team t: teams){
			if(t.hasPlayer(player) != null){
				return t;
			}
		}
		return null;
	}
	
	/**
	 * Looks for the provided player, returning their wrapper
	 * @param player
	 * @return
	 */
	public SurvivalPlayer getPlayer(OfflinePlayer player) {
		if (teams.isEmpty()) {
			return null;
		}
		
		for(Team t : teams){
			SurvivalPlayer tmp=t.hasPlayer(player);
			if(tmp != null){
				return tmp;
			}
		}
		return null;
	}
	
	/**
	 * Returns summary information about this session.
	 * @param verbose Should this also give extensive information?
	 * @return
	 */
	public String getInfo(boolean verbose) {
		return "NOT IMPLEMENTED";
	}
	
	/**
	 * Get the name associated with this session
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	public Collection<Team> getTeams(){
		return teams;
	}
}
