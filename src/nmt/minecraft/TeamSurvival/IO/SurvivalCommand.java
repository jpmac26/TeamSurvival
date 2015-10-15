package nmt.minecraft.TeamSurvival.IO;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import nmt.minecraft.TeamSurvival.TeamSurvivalManager;
import nmt.minecraft.TeamSurvival.Map.Map;
import nmt.minecraft.TeamSurvival.Player.SurvivalPlayer;
import nmt.minecraft.TeamSurvival.Player.Team;
import nmt.minecraft.TeamSurvival.Session.GameSession;

/**
 * @author Stephanie
 */
public class SurvivalCommand implements CommandExecutor {

	/**
	 * A list of commands handled by the 'team survival' wrapping command
	 */
	private static final String[] teamSurvivalCommandList = {"session", "team"};

	private static final String[] sessionCommandList = {"list", "create", "info", "remove", "start", "stop", "advance"};
	
	private static final String[] teamCommandList = {"list", "create", "info", "dispand"};
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (args.length == 0) {
			//print usage
			//sender.sendMessage("/ts [session|team] {args}");
			return false;
		}
		
		if (args[0].equalsIgnoreCase("session")) {
			onSessionCommand(sender, args);
			return true;
		}
		
		if (args[0].equalsIgnoreCase("team")) {
			onTeamCommand(sender, args);
			return true;
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
	 * TODO: don't let them create a session using the same map as another session
	 * 
	 */
	private void onTeamCommand(CommandSender sender, String[] args) {
		//[list | create | info | disband] ?
		if(args.length <2){
			//print usage, cause we're no longer handled by onCommand!
			sender.sendMessage(ChatFormat.ERROR.wrap("/ts team [list|create|info|disband] {args}"));
			return;
		}
		
		switch(args[1]) {
		case "list":
			onTeamList(sender, args);
		break;
		case "create":
			onTeamCreate(sender, args);
		break;
		case "info":
			onTeamInfo(sender, args);
		break;
		case "dispand":
			onTeamDispand(sender, args);
		break;
		}
	}

	private void onTeamCreate(CommandSender sender, String[] args) {
		// /ts team create [sessionName] [teamName]
		if(args.length != 4){
			sender.sendMessage(ChatFormat.ERROR.wrap("Incorrect number of arguments! \n")
					+ ChatFormat.ERROR.wrap("usage: /teamsurvival team create [sessionName] [teamName]"));
			return;
		}
		
		GameSession session = TeamSurvivalManager.getSession(args[2]);
		if(session == null){
			sender.sendMessage(ChatFormat.ERROR.wrap("Could not find session."));
			return;
		}
		
		if(session.getState() != GameSession.State.PREGAME){
			sender.sendMessage(ChatFormat.ERROR.wrap("Session is already running."));
			return;
		}
		
		if(session.getMap().getMaxTeams() <= session.getTeams().size()){
			sender.sendMessage(ChatFormat.ERROR.wrap("Cannot create another team, max limit reached"));
			return;
		}
		
		Team team = session.getTeam(args[3]);
		if(team != null){
			sender.sendMessage(ChatFormat.ERROR.wrap("There is already a team with that name in that session"));
			return;
		}
		
		LinkedList<SurvivalPlayer> players = new LinkedList<SurvivalPlayer>();
		
		Team theTeam = new Team(args[3], players);
		session.addTeam(theTeam);
		sender.sendMessage(ChatFormat.IMPORTANT.wrap("Regisered new team "+args[3]+" with session ")+ChatFormat.SESSION.wrap(args[2]));
	}

	private void onTeamInfo(CommandSender sender, String[] args) {
		// /ts team info [sessionName] [teamName]
		if(args.length != 4){
			sender.sendMessage(ChatFormat.ERROR.wrap("Incorrect number of arguments")+
					ChatFormat.IMPORTANT.wrap("usage: /teamsurvival info [session] [team]"));
			return;
		}
		
		GameSession session = TeamSurvivalManager.getSession(args[2]);
		if(session == null){
			sender.sendMessage(ChatFormat.ERROR.wrap("Could not find session."));
			return;
		}
		
		Team team = session.getTeam(args[3]);
		if(team == null){
			sender.sendMessage(ChatFormat.ERROR.wrap("Could not find a team with that name in the session"));
			return;
		}
		
		sender.sendMessage("Session: "+ChatFormat.SESSION.wrap(session.getName())+ " Team: "+ ChatFormat.TEAM.wrap(team.getName()));
		
		List<String> playerList = team.getPlayerList();
		if(playerList.isEmpty()){
			sender.sendMessage(ChatFormat.IMPORTANT.wrap("This team does not have any team members!"));			
		}else{
			for(String player : playerList){
				sender.sendMessage(ChatFormat.IMPORTANT.wrap(player));
			}
		}
		
	}

	private void onTeamDispand(CommandSender sender, String[] args) {
		// /ts team dispand [sessionName] [teamName]
		if(args.length != 4){
			sender.sendMessage(ChatFormat.ERROR.wrap("Incorrect number of arguments! ")
					+ ChatFormat.IMPORTANT.wrap("usage: /teamsurvival team dispand [sessionName] [teamName]"));
			return;
		}
		
		GameSession session = TeamSurvivalManager.getSession(args[2]);
		if(session == null){
			sender.sendMessage(ChatFormat.ERROR.wrap("Could not find session."));
			return;
		}
		
		if(session.getState() != GameSession.State.PREGAME){
			sender.sendMessage(ChatFormat.ERROR.wrap("Session is already running."));
			return;
		}
		
		Team team = session.getTeam(args[3]);
		if(team == null){
			sender.sendMessage(ChatFormat.ERROR.wrap("Could not find a team with that name in the session"));
			return;
		}
		
		if(!session.removeTeam(team)){
			sender.sendMessage(ChatFormat.ERROR.wrap("Could not remove team"));
			return;
		}
		
		sender.sendMessage(ChatFormat.IMPORTANT.wrap(args[3]+" has been removed from the session"));
	}

	private void onTeamList(CommandSender sender, String[] args) {
		if (args.length != 3) {
			sender.sendMessage(ChatFormat.ERROR.wrap("/ts team list [session]"));
			return;
		}
		
		GameSession session = TeamSurvivalManager.getSession(args[2]);
		
		if (session == null) {
			sender.sendMessage(ChatFormat.ERROR.wrap("Session list is null!"));
			return;
		}
		
		Collection<Team> teams = session.getTeams();
		
		if (teams.isEmpty()) {
			sender.sendMessage(ChatFormat.IMPORTANT.wrap("There are no teams"));
			return;
		}
		
		sender.sendMessage("There are currently " + ChatColor.GREEN + teams.size() + ChatColor.RESET + " teams:");
		
		for (Team s : teams) {
			sender.sendMessage(ChatFormat.TEAM.wrap(s.getName()));
		}
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
			sender.sendMessage(ChatFormat.ERROR.wrap("/ts session [list|create|start|stop|remove|info] {args}"));
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
			onSessionCreateCommand(sender, args);
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
		
		if (args[1].equalsIgnoreCase("advance")) {
			return onSessionAdvanceCommand(sender, args);
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
	private void onSessionCreateCommand(CommandSender sender, String[] args) {
		// /ts session create [sessionName] [mapName]
		if(args.length != 4){
			sender.sendMessage(ChatFormat.ERROR.wrap("Incorrect number of arguments! ")
					+ ChatFormat.IMPORTANT.wrap("usage: /teamsurvival session create [sessionName] [mapName]"));
			return;
		}
		
		if(TeamSurvivalManager.getSession(args[2])!=null){
			sender.sendMessage(ChatFormat.ERROR.wrap("There already exists an active session with that name"));
			return;
		}
		
		//check for map matching the given name
		if(!Map.listConfigs().contains(args[3])){
			sender.sendMessage(ChatFormat.ERROR.wrap("Could not find config for the given map"));
			return;
		}
		
		Map tmpMap = Map.loadConfig(args[3]);
		if(tmpMap == null){
			sender.sendMessage(ChatFormat.ERROR.wrap("Could not load the config file for the map"));
			return;
		}
		
		if(!tmpMap.isValid()){
			sender.sendMessage(ChatFormat.ERROR.wrap("Could not load "+tmpMap.getName()+" Map is invalid"));
			return;
		}
		
		GameSession session = new GameSession(args[2], tmpMap);
		
		if(!TeamSurvivalManager.register(session)){
			sender.sendMessage(ChatFormat.ERROR.wrap("Could not register the session with the TSManager"));
			return;
		}
		
		sender.sendMessage(ChatFormat.SESSION.wrap("Session successfully created"));
		return;
		
		
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
		if(session.start()){
			sender.sendMessage(ChatFormat.SUCCESS.wrap("Session Started."));
		}else{
			sender.sendMessage(ChatFormat.ERROR.wrap("Could not open session."));
		}
		
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
		// /teamsurvival session info [session] [verbose]
		if (args.length < 3 || args.length > 4) {
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
	
	/**
	 * Advanced the session
	 * @param sender
	 * @param args
	 * @return
	 * @see {@link nmt.minecraft.TeamSurvival.Session.GameSession#clearWave() GameSession.clearWave()}
	 */
	private boolean onSessionAdvanceCommand(CommandSender sender, String[] args) {
		//ts session advance [session]
		if (args.length != 3) {
			sender.sendMessage(ChatFormat.ERROR.wrap("/ts session advance ") + ChatFormat.SESSION.wrap("[sessionName]"));
			return false;
		}
		
		GameSession session = TeamSurvivalManager.getSession(args[2]);
		if (session == null) {
			sender.sendMessage(ChatFormat.ERROR.wrap("Unable to find session: " + args[2]));
			return false;
		}
		
		session.clearWave();
		sender.sendMessage(ChatFormat.SUCCESS.wrap("Advancing to the next wave..."));
		//TODO actually advance to the next wave
		return true;
	}
}
