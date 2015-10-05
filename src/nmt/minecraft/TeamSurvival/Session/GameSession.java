package nmt.minecraft.TeamSurvival.Session;

import java.util.Collection;

import org.bukkit.OfflinePlayer;

import nmt.minecraft.TeamSurvival.Player.SurvivalPlayer;
import nmt.minecraft.TeamSurvival.Player.Team;

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
	
	public GameSession() {
		this.state = State.PREGAME;
	}
	
	/**
	 * Starts the game, dealing with the teams and scores, etc
	 */
	public void start() {
		; //TODO
	}
	
	/**
	 * Stops the game.<br />
	 * Games stop automatically, so this method is considered an emergency operation.
	 */
	public void stop() {
		; //TODO
	}
	
	/**
	 * @return The current state of the session
	 */
	public State getState() {
		return state;
	}
	
	/**
	 * Adds a team to teh session
	 * @param team
	 */
	public void addTeam(Team team) {
		; //TODO
	}
	
	/**
	 * Tries to look up a team
	 * @param name The name to look up
	 * @return
	 */
	public Team getTeam(String name) {
		return null; //TODO
	}
	
	/**
	 * Tries to look up a team
	 * @param player the player to look up
	 * @return The team the player is on, null if the player is not on a team.
	 */
	public Team getTeam(SurvivalPlayer player) {
		for(Team t : teams){
			if(t.hasPlayer(player)){
				return t;
			}
		}
		
		return null;
	}
	
	public Team getTeam(OfflinePlayer player) {
		return null; //TODO
	}
	
	/**
	 * Looks for the provided player, returning their wrapper
	 * @param player
	 * @return
	 */
	public SurvivalPlayer getPlayer(OfflinePlayer player) {
		return null; //TODO
	}
}
