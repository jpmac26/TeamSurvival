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
 * 
 * @author Stephanie
 */
public class LeaveTeamCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)){
			sender.sendMessage("You need to be a player to execute command");
			return false;
		}
		
		//find the team that this player is on.
		Team team = findTeam((Player)sender);
		if(team == null){
			sender.sendMessage(ChatFormat.ERROR.wrap("You are not on a team!"));
			return false;
		}
		
		//remove them from the team
		team.removePlayer(team.hasPlayer((Player)sender));
		sender.sendMessage(ChatFormat.SUCCESS.wrap("You have left your team: "+team.getName()));
		return true;
	}
	
	/**
	 * finds the team the player is on
	 * @param player the player to search for
	 * @return The team the player is on, or null if the player is not on a team.
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
