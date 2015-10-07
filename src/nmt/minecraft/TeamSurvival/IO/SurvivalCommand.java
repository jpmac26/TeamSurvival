package nmt.minecraft.TeamSurvival.IO;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import nmt.minecraft.TeamSurvival.TeamSurvivalManager;
import nmt.minecraft.TeamSurvival.Session.GameSession;

/**
 * 
 * @author Stephanie Martinez
 */
public class SurvivalCommand implements CommandExecutor {
	
	/**
	 * The plugin's commands, handled by this executor
	 */
	private static final String[] commandList = {"teamsurvival", "jointeam"};
	
	/**
	 * A list of commands handled by the 'team survival' wrapping command
	 */
	private static final String[] teamSurvivalCommandList = {"session", "team"};

	private static final String[] sessionCommandList = {"list", "create", "info", "dispatch"};
	
	private static final String[] teamCommandList = {"list", "create", "info", "dispatch"};
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("teamsurvival")) {
			onTeamSurvivalCommand(sender, args);
			return true;
		}
		
		if (cmd.getName().equalsIgnoreCase("jointeam")) {
			onJoinCommand(sender, args);
		}
		
		return false;
	}
	
	public static List<String> getCommandlist() {
		return Arrays.asList(commandList);
	}

	public static List<String> getTeamsurvivalcommandlist() {
		return Arrays.asList(teamSurvivalCommandList);
	}

	public static List<String> getSessioncommandlist() {
		return Arrays.asList(sessionCommandList);
	}

	public static List<String> getTeamcommandlist() {
		return Arrays.asList(teamCommandList);
	}
	
	/**
	 * Handles a join command
	 * @param sender Who send the command
	 * @param args The args passed to the command
	 */
	private void onJoinCommand(CommandSender sender, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players can use this command!");
			return;
		}
		
		; //TODO
	}
	
	/**
	 * Handles the admin-command wrapper
	 * @param sender Who sent the command
	 * @param args The arguments they supplied
	 */
	private void onTeamSurvivalCommand(CommandSender sender, String[] args) {
		if (args.length == 0) {
			//print usage
			sender.sendMessage("/ts [session|team] {args}");
			return;
		}
		
		if (args[0].equalsIgnoreCase("session")) {
			onSessionCommand(sender, args);
			return;
		}
		
		if (args[0].equalsIgnoreCase("team")) {
			onTeamCommand(sender, args);
			return;
		}
	}
	
	/**
	 * Handles the admin 'session' command
	 * @param sender
	 * @param args
	 */
	private void onSessionCommand(CommandSender sender, String[] args) {
		//[list | create | start | stop | remove | info] ?
		//this is what EDFs has, and it feels pretty similar in terms of 'sessions'
		if (args.length < 2) {
			return;
		}
		
		if (args[1].equalsIgnoreCase("list")) {
			if (args.length > 2) {
				sender.sendMessage("/ts session list");
				return;
			}
			
			Collection<GameSession> sessions = TeamSurvivalManager.getSessions();
			
			if (sessions == null) {
				sender.sendMessage(ChatFormat.ERROR.wrap("Session list is null!"));
				return;
			}
			
			if (sessions.isEmpty()) {
				sender.sendMessage(ChatFormat.IMPORTANT.wrap("There are no sessions"));
				return;
			}
			
			sender.sendMessage("There are currently " + ChatColor.GREEN + sessions.size() + ChatColor.RESET + " sessions:");
			
			for (GameSession s : sessions) {
				sender.sendMessage(ChatFormat.SESSION.wrap(s.getName()) + "  " 
						+ ChatFormat.IMPORTANT.wrap("[" + s.getState().toString() + "]"));
			}
			
			return;
		}
		
		if (args[1].equalsIgnoreCase("info")) {
			if (args.length < 2 || args.length > 4) {
				sender.sendMessage("/ts session info " + ChatFormat.SESSION.wrap("[sessionName] {verbose}"));
				return;
			}
			
			String sessionName = args[2];
			GameSession gameSession = null;
			
			for (GameSession session : TeamSurvivalManager.getSessions()) {
				if (session.getName().equals(sessionName)) {
					gameSession = session;
					break;
				}
			}
			
			if (gameSession == null) {
				sender.sendMessage(ChatFormat.ERROR.wrap("Unable to find session ") + ChatFormat.SESSION.wrap(sessionName));
				return;
			}
			
			boolean verbose = false;
			if (args.length == 4) {
				if (args[3].equalsIgnoreCase("true") || args[3].equals("verbose")) {
					verbose = true;
				}
			}
			
			sender.sendMessage(gameSession.getInfo(verbose));
			
			return;
		}
	}
	
	/**
	 * Handles the admin 'team' command
	 * @param sender
	 * @param args
	 */
	private void onTeamCommand(CommandSender sender, String[] args) {
		//[list | create | info | disband] ?
	}
}
