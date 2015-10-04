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
 * @note Classified as a session job
 */
public class TeamSurvivalManager {
	
	private static Collection<GameSession> sessions = new HashSet<GameSession>();
	
	public static SurvivalPlayer getPlayer(OfflinePlayer player) {
		return null; //TODO
	}
	
	public static Team getTeam(SurvivalPlayer player) {
		return null; //TODO
	}
	
	public static Collection<GameSession> getSessions() {
		return sessions;
	}
}
