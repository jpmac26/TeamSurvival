package nmt.minecraft.TeamSurvival.IO;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import nmt.minecraft.TeamSurvival.TeamSurvivalManager;
import nmt.minecraft.TeamSurvival.Player.SurvivalPlayer;
import nmt.minecraft.TeamSurvival.Player.Team;
import nmt.minecraft.TeamSurvival.Session.GameSession;

public class JoinTeamCommand implements CommandExecutor{
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		// TODO Auto-generated method stub
		return onJoinCommand(sender, args);
	}
	
	/**
	 * Handles a join command
	 * @param sender Who send the command
	 * @param args The args passed to the command
	 */
	private boolean onJoinCommand(CommandSender sender, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatFormat.ERROR.wrap("Only players can use this command!"));
			return false;
		}else if(args.length != 2){
			sender.sendMessage(ChatFormat.ERROR.wrap("Incorrect number of arguments."));
			sender.sendMessage(ChatFormat.IMPORTANT.wrap("usage: /jointeam [session] [team]"));
			return false;
		}
		
		
		//find the team they want to join
		Team team = getTeam(args[1], getSession(args[0]));
		if(team == null){
			sender.sendMessage(ChatFormat.ERROR.wrap("Could not locate team "+args[1]+" in session "+args[0]));
			return false;
		}
		
		//add them to the team
		if(sender instanceof OfflinePlayer){
			SurvivalPlayer player = new SurvivalPlayer((OfflinePlayer)sender);
			team.addPlayer(player);
		}
		
		return true;
	}
	
	private GameSession getSession(String name){
		for(GameSession g : TeamSurvivalManager.getSessions()){
			if(g.getName().equals(name)){
				return g;
			}
		}
		
		return null;
	}
	
	private Team getTeam(String name, GameSession session){
		if(session == null){
			return null;
		}
		
		for(Team t: session.getTeams()){
			if(t.getName().equals(name));
		}
		return null;
	}
	
}
