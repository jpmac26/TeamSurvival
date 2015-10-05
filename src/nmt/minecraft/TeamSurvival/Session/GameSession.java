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
	 * Adds a team to the session
	 * @param team
	 */
	public void addTeam(Team team) {
		//should we check to see if the team is unique?
		; //TODO
	}
	
	/**
	 * Tries to look up a team
	 * @param name The name to look up
	 * @return The team with the given name, null if it cannot be found
	 */
	public Team getTeam(String name) {
		for(Team t : teams){
			if(t.getName().equals(name)){
				return t;
			}
		}
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
	
	/**
	 * Tries to look up a team
	 * @param player the player to look up
	 * @return The team the player is on, null if the player is not on a team.
	 */
	public Team getTeam(OfflinePlayer player) {
		for(Team t: teams){
			if(t.hasPlayer(player) != null){
				return t;
			}
		}
		return null; //TODO
	}
	
	/**
	 * Looks for the provided player, returning their wrapper
	 * @param player
	 * @return
	 */
	public SurvivalPlayer getPlayer(OfflinePlayer player) {
		for(Team t : teams){
			SurvivalPlayer tmp=t.hasPlayer(player);
			if(tmp != null){
				return tmp;
			}
		}
		return null; //TODO
	}
}
