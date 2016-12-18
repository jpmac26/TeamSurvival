package nmt.minecraft.TeamSurvival.IO;

import java.util.Collection;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import nmt.minecraft.TeamSurvival.TeamSurvivalManager;
import nmt.minecraft.TeamSurvival.Player.Team;
import nmt.minecraft.TeamSurvival.Session.GameSession;

/**
 * Attempts to remove the sender from their current team.
 *
 * @author Stephanie
 */
public class LeaveTeamCommand implements CommandExecutor{

	/**
	 * Executes the given command, returning its success.
	 *
	 * @param sender Source of the command
	 * @param command Command which was executed
	 * @param label Alias of the command which was used
	 * @param args Passed command arguments
	 * @return true if a valid command, otherwise false
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)){
			sender.sendMessage("You need to be a player to execute this command.");
			return false;
		}
		
		//find the team that this player is on
		Team team = findTeam((Player)sender);
		if(team == null){
			sender.sendMessage(ChatFormat.ERROR.wrap("You are not on a team!"));
			return false;
		}
		
		GameSession session = TeamSurvivalManager.getSession(team);
		
		if (session.getState() != GameSession.State.PREGAME) {
			sender.sendMessage(ChatFormat.ERROR.wrap("You cannot leave a session that's already started!"));
			return true;
		}
		
		//remove them from the team
		team.removePlayer(team.hasPlayer((Player)sender));
		sender.sendMessage(ChatFormat.SUCCESS.wrap("You have left your team: " + team.getName() + "."));
		return true;
	}
	
	/**
	 * Finds the team the player is on.
	 * @param player the player to search for
	 * @return The team the player is on, or null if the player is not on a team
	 */
	private Team findTeam(Player player){
		Collection<GameSession> sessions = TeamSurvivalManager.getSessions();
		for(GameSession s : sessions){
			Collection<Team> teams = s.getTeams();
			for(Team t : teams){
				if(t.hasPlayer(player)!= null){
					return t;
				}
			}
		}
		return null;
	}
}
