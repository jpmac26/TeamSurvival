package nmt.minecraft.TeamSurvival;

import java.util.Collection;
import java.util.HashSet;

import org.bukkit.OfflinePlayer;

import nmt.minecraft.TeamSurvival.Player.SurvivalPlayer;
import nmt.minecraft.TeamSurvival.Player.Team;
import nmt.minecraft.TeamSurvival.Session.GameSession;

/**
 * Manager class for all sessions.<br />
 * Static class that gives access to global information
 * @author Skyler
 * @author Stephanie
 * @note Classified as a session job
 */
public class TeamSurvivalManager {
	
	private static Collection<GameSession> sessions = new HashSet<GameSession>();
	
	/**
	 * Looks up a player using an offline player.
	 * @param player
	 * @return
	 */
	public static SurvivalPlayer getPlayer(OfflinePlayer player) {
		for(GameSession s : sessions){
			SurvivalPlayer play = s.getPlayer(player);
			if(play != null){
				return play;
			}
		}
		return null;
	}
	
	/**
	 * Looks up a team by a player.<br />
	 * In other words, this method returns the team a player belongs to
	 * @param player
	 * @return
	 */
	public static Team getTeam(SurvivalPlayer player) {
		for(GameSession s : sessions){
			Team team = s.getTeam(player);
			if(team != null){
				return team;
			}
		}
		return null;
	}
	
	/**
	 * Returns the underlying collection of sessions.<br />
	 * Does NOT clone! This collection is the same collection this thing holds
	 * @return
	 */
	public static Collection<GameSession> getSessions() {
		return sessions;
	}
	
	/**
	 * Registers a game session, if it's not already registered
	 * @param session The game session to add.
	 * @return true if the session was added
	 */
	public static boolean register(GameSession session) {
		boolean check = sessions.add(session);
		if (check) {
			System.out.println("Successfully added session: " + session.getName());
		} else {
			System.out.println("WARNING! Session: " + session.getName() + " was not added!");
		}
		return check;
	}
	
	/**
	 * Attempts to unregister a game session
	 * @param session The game session to remove.
	 * @return true if the session was found and unregistered
	 */
	public static boolean unregister(GameSession session) {
		if (session.getState() != GameSession.State.FINISHED && session.getState() != GameSession.State.PREGAME) {
			TeamSurvivalPlugin.plugin.getLogger().warning("ERROR! Session: " + session.getName() + " is not finished! Please wait!");
			return false;
		}
		
		if (sessions.remove(session)) {
			TeamSurvivalPlugin.plugin.getLogger().info("Successfully removed session: " + session.getName());
			return true;
		}
		
		TeamSurvivalPlugin.plugin.getLogger().warning("WARNING! Session: " + session.getName() + " was not added!");
		return false;
	}
	
	public static GameSession getSession(String sessionName){
		for(GameSession s : TeamSurvivalManager.getSessions()){
			if(s.getName().equalsIgnoreCase(sessionName)){
				return s;
			}
		}
		return null;
	}
	
	/**
	 * Fetches the session that the team is in.<br />
	 * If the team is not found in any session, returns null
	 * @param team
	 * @return
	 */
	public static GameSession getSession(Team team) {
		for (GameSession s : TeamSurvivalManager.getSessions()) {
			if (s.getTeams().contains(team)) {
				return s;
			}
		}
		
		return null;
	}
	
}
