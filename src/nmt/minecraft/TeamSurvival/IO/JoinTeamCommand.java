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
		return onJoinCommand(sender, args);
	}
	
	/**
	 * Handles a join command
	 * @param sender Who send the command
	 * @param args The args passed to the command
	 * @return true if the sender was successfully added
	 */
	private boolean onJoinCommand(CommandSender sender, String[] args) {
		if (!(sender instanceof Player) || !(sender instanceof OfflinePlayer)) {
			sender.sendMessage(ChatFormat.ERROR.wrap("Only players can use this command!"));
			return true; //The command was usedp roperly, but by wrong people! We don't want to print usage!
		} else if(args.length != 2 ){
			sender.sendMessage(ChatFormat.ERROR.wrap("Incorrect number of arguments."));
			//sender.sendMessage(ChatFormat.IMPORTANT.wrap("usage: /jointeam [session] [team]"));
			return false; //returning false already prints out usage from the plugin.yml -sm
		} else if(onTeam((OfflinePlayer)sender)) {
			//make sure the player is not already on a team
			sender.sendMessage(ChatFormat.ERROR.wrap("You are already registered to a team!"));
			return true; //properly got the command, just some other error. Return true.
		}
		
		//find the team they want to join
		GameSession session = TeamSurvivalManager.getSession(args[0]);
		if(session == null) {
			sender.sendMessage(ChatFormat.ERROR.wrap("Could not locate session "+args[0]));
			return true;
		}
		Team team = session.getTeam(args[1]);
		if(team == null) {
			sender.sendMessage(ChatFormat.ERROR.wrap("Could not locate team "+args[1]+" in session "+args[0]));
			return true;
		}
		
		//add them to the team
		SurvivalPlayer player = new SurvivalPlayer((OfflinePlayer)sender);
		if(!team.addPlayer(player)) {
			sender.sendMessage(ChatFormat.ERROR.wrap("Could not add you to the team"));
			return true;
		}
		
		sender.sendMessage(ChatFormat.TEAM.wrap("You have been added to "+team.getName()));
		return true;
	}
	
	/**
	 * Checks to see if player is on any team
	 * @param player the player to look for
	 * @return true if the player is on a team
	 */
	private boolean onTeam(OfflinePlayer player){
		for(GameSession s: TeamSurvivalManager.getSessions()){
			if(s.getPlayer(player) != null){
				return true;
			}
		}
		return false;
	}
	
}
