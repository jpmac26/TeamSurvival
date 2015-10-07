package nmt.minecraft.TeamSurvival.IO;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import nmt.minecraft.TeamSurvival.TeamSurvivalManager;
import nmt.minecraft.TeamSurvival.Map.Map;
import nmt.minecraft.TeamSurvival.Player.SurvivalPlayer;
import nmt.minecraft.TeamSurvival.Player.Team;
import nmt.minecraft.TeamSurvival.Session.GameSession;

/**
 * 
 * @author Stephanie
 */
public class SurvivalCommand implements CommandExecutor {

	/**
	 * A list of commands handled by the 'team survival' wrapping command
	 */
	private static final String[] teamSurvivalCommandList = {"session", "team"};

	private static final String[] sessionCommandList = {"list", "create", "info", "dispatch"};
	
	private static final String[] teamCommandList = {"list", "create", "info", "dispatch"};
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (args.length == 0) {
			//print usage
			sender.sendMessage("/ts [session|team] {args}");
			return true;
		}
		
		if (args[0].equalsIgnoreCase("session")) {
			return onSessionCommand(sender, args);
		}
		
		if (args[0].equalsIgnoreCase("team")) {
			return onTeamCommand(sender, args);
		}
		
		return false;
	}
	
	protected static List<String> getTeamsurvivalcommandlist() {
		return Arrays.asList(teamSurvivalCommandList);
	}

	protected static List<String> getSessioncommandlist() {
		return Arrays.asList(sessionCommandList);
	}

	protected static List<String> getTeamcommandlist() {
		return Arrays.asList(teamCommandList);
	}
	
	/**
	 * Handles the admin 'team' command
	 * @param sender
	 * @param args
	 * @return 
	 */
	private boolean onTeamCommand(CommandSender sender, String[] args) {
		//[list | create | info | disband] ?
		if(args.length <2){
			return false;
		}
		switch(args[1]){
		case "list":
			return onTeamList(sender, args);
		case "create":
			return onTeamCreate(sender, args);
		case "info":
			return onTeamInfo(sender, args);
		case "dispand":
			return onTeamDispand(sender, args);
		}
		
		return false;
	}

	private boolean onTeamCreate(CommandSender sender, String[] args) {
		// /ts team create [sessionName] [teamName]
		if(args.length != 4){
			sender.sendMessage(ChatFormat.ERROR.wrap("Incorrect number of arguments! ")
					+ ChatFormat.IMPORTANT.wrap("usage: /teamsurvival team create [sessionName] [teamName]"));
			return false;
		}
		
		GameSession session = TeamSurvivalManager.getSession(args[2]);
		if(session == null){
			sender.sendMessage(ChatFormat.ERROR.wrap("Could not find session."));
			return false;
		}
		
		if(session.getMap().getMaxTeams() >= session.getTeams().size()){
			sender.sendMessage(ChatFormat.ERROR.wrap("Cannot create another team, max limit reached"));
			return false;
		}
		
		Team team = session.getTeam(args[3]);
		if(team != null){
			sender.sendMessage(ChatFormat.ERROR.wrap("There is already a team with that name in that session"));
			return false;
		}
		
		LinkedList<SurvivalPlayer> players = new LinkedList<SurvivalPlayer>();
		
		Team theTeam = new Team(args[3], players);
		session.addTeam(theTeam);
		sender.sendMessage(ChatFormat.IMPORTANT.wrap("Regisered new team "+args[3]+" with session ")+ChatFormat.SESSION.wrap(args[2]));
		return true;
	}

	private boolean onTeamInfo(CommandSender sender, String[] args) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean onTeamDispand(CommandSender sender, String[] args) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean onTeamList(CommandSender sender, String[] args) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Handles the admin 'session' command
	 * @param sender
	 * @param args
	 * @return 
	 */
	private boolean onSessionCommand(CommandSender sender, String[] args) {
		//[list | create | start | stop | remove | info] ?
		//this is what EDFs has, and it feels pretty similar in terms of 'sessions'
		if (args.length < 2) {
			return false;
		}
		
		if (args[1].equalsIgnoreCase("list")) {
			onSessionListCommand(sender, args);
			return true;
		}
		
		if (args[1].equalsIgnoreCase("info")) {
			return onSessionInfoCommand(sender, args);
		}
		
		if (args[1].equalsIgnoreCase("create")) {
			return onSessionCreateCommand(sender, args);
		}
		
		if (args[1].equalsIgnoreCase("start")) {
			return onSessionStartCommand(sender, args);
		}
		
		if (args[1].equalsIgnoreCase("stop")) {
			return onSessionStopCommand(sender, args);
		}
		
		if (args[1].equalsIgnoreCase("remove")) {
			return onSessionRemoveCommand(sender, args);
		}
		return false;
	}

	/**
	 * Handles the 'list' argument for the admin 'session' command
	 * @param sender
	 * @param args
	 */
	private void onSessionListCommand(CommandSender sender, String[] args) {
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
	}
	
	/**
	 * Handles the 'create' argument for the admin 'session' command
	 * @param sender
	 * @param args
	 */
	private boolean onSessionCreateCommand(CommandSender sender, String[] args) {
		// /ts session create [sessionName] [mapName]
		if(args.length != 4){
			sender.sendMessage(ChatFormat.ERROR.wrap("Incorrect number of arguments! ")
					+ ChatFormat.IMPORTANT.wrap("usage: /teamsurvival session create [sessionName] [mapName]"));
			return false;
		}
		
		if(TeamSurvivalManager.getSession(args[2])!=null){
			sender.sendMessage(ChatFormat.ERROR.wrap("There already exists an active session with that name"));
			return false;
		}
		
		//check for map matching the given name
		if(!Map.listConfigs().contains(args[3])){
			sender.sendMessage(ChatFormat.ERROR.wrap("Could not find config for the given map"));
			return false;
		}
		
		Map tmpMap = Map.loadConfig(args[3]);
		if(tmpMap == null){
			sender.sendMessage(ChatFormat.ERROR.wrap("Could not load the config file for the map"));
			return false;
		}
		
		GameSession session = new GameSession(args[2], tmpMap);
		
		if(!TeamSurvivalManager.register(session)){
			sender.sendMessage(ChatFormat.ERROR.wrap("Could not register the session with the TSManager"));
			return false;
		}
		
		sender.sendMessage(ChatFormat.SESSION.wrap("Session successfully created"));
		return true;
		
		
	}
	
	/**
	 * Handles the 'start' argument for the admin 'session' command
	 * @param sender
	 * @param args
	 */
	private boolean onSessionStartCommand(CommandSender sender, String[] args) {
		// /ts session start [session]
		if(args.length != 3){
			sender.sendMessage(ChatFormat.ERROR.wrap("Incorrect number of arguments! ")
					+ ChatFormat.IMPORTANT.wrap("usage: /teamsurvival session start [sessionName]"));
			return false;
		}
		
		GameSession session = TeamSurvivalManager.getSession(args[2]);
		if(session == null){
			sender.sendMessage(ChatFormat.ERROR.wrap("Could not find session"));
			return false;
		}
		
		sender.sendMessage(ChatFormat.SESSION.wrap("Starting session..."));
		session.start();
		
		return true;
	}
	
	/**
	 * Handles the 'stop' argument for the admin 'session' command
	 * @param sender
	 * @param args
	 */
	private boolean onSessionStopCommand(CommandSender sender, String[] args) {
		// /ts session stop [session]
			if(args.length != 3){
				sender.sendMessage(ChatFormat.ERROR.wrap("Incorrect number of arguments! ")
						+ ChatFormat.IMPORTANT.wrap("usage: /teamsurvival session stop [sessionName]"));
				return false;
			}
			
			GameSession session = TeamSurvivalManager.getSession(args[2]);
			if(session == null){
				sender.sendMessage(ChatFormat.ERROR.wrap("Could not find session"));
				return false;
			}
			
			sender.sendMessage(ChatFormat.SESSION.wrap("Stoping session..."));
			session.stop();
			
			return true;
	}
	
	/**
	 * Handles the 'remove' argument for the admin 'session' command
	 * @param sender
	 * @param args
	 */
	private boolean onSessionRemoveCommand(CommandSender sender, String[] args) {
		// /ts session remove [sessionName]
		if(args.length != 3){
			sender.sendMessage(ChatFormat.ERROR.wrap("Incorrect number of arguments! ")
					+ ChatFormat.IMPORTANT.wrap("usage: /teamsurvival session remove [sessionName]"));
			return false;
		}
		
		GameSession session = TeamSurvivalManager.getSession(args[2]);
		if(session == null){
			sender.sendMessage(ChatFormat.ERROR.wrap("Could not find session"));
			return false;
		}
		
		if(!TeamSurvivalManager.unregister(session)){
			sender.sendMessage(ChatFormat.ERROR.wrap("Could not remove session."));
			return false;
		}
		
		sender.sendMessage(ChatFormat.SESSION.wrap("Session removed."));
		return true;
	}
	
	/**
	 * Handles the 'info' argument for the admin 'session' command
	 * @param sender
	 * @param args
	 */
	private boolean onSessionInfoCommand(CommandSender sender, String[] args) {
		if (args.length < 2 || args.length > 4) {
			sender.sendMessage("/ts session info " + ChatFormat.SESSION.wrap("[sessionName] {verbose}"));
			return false;
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
			return false;
		}
		
		boolean verbose = false;
		if (args.length == 4) {
			if (args[3].equalsIgnoreCase("true") || args[3].equals("verbose")) {
				verbose = true;
			}
		}
		
		sender.sendMessage(gameSession.getInfo(verbose));
		return true;
	}
}
