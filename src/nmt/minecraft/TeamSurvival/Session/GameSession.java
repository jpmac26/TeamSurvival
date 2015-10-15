package nmt.minecraft.TeamSurvival.Session;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import nmt.minecraft.TeamSurvival.TeamLossEvent;
import nmt.minecraft.TeamSurvival.TeamSurvivalPlugin;
import nmt.minecraft.TeamSurvival.Enemy.BossWave;
import nmt.minecraft.TeamSurvival.Enemy.SkeletonGroupMob;
import nmt.minecraft.TeamSurvival.Enemy.Wave;
import nmt.minecraft.TeamSurvival.Enemy.WaveFinishEvent;
import nmt.minecraft.TeamSurvival.IO.ChatFormat;
import nmt.minecraft.TeamSurvival.Map.Map;
import nmt.minecraft.TeamSurvival.Player.SurvivalPlayer;
import nmt.minecraft.TeamSurvival.Player.Team;
import nmt.minecraft.TeamSurvival.Scheduling.Scheduler;
import nmt.minecraft.TeamSurvival.Scheduling.Tickable;
import nmt.minecraft.TeamSurvival.Shop.Shop;

/**
 * Holds a single game session.<br />
 * Game sessions will hold the current state of things, the teams involved, etc
 * @author Skyler
 * @author Stephanie
 *
 */
public class GameSession implements Listener, Tickable {
	
	public static final int defaultWaveCount = 12;//TODO change this to 12 for the event
	private static final int defaultStartingTime = 1;//in seconds TODO
	private static final int defaultStartingBlocksApart = 10;//TODO
	
	public static final EntityType bossType = EntityType.ENDER_DRAGON;
	
	public enum State {
		PREGAME,
		STARTINGPERIOD,
		INWAVE,
		INSHOP,
		FINISHED;
	}
	
	/**
	 * Defines standard messages that GameSessions will send out
	 * @author Skyler
	 *
	 */
	public static enum Messages {
		STARTINFO("The game has begun!\n" + ChatColor.GREEN + "15 Minutes until waves begin" + ChatColor.RESET),
		ONEMINUTE(ChatColor.GOLD + "One minute until waves begin!" + ChatColor.RESET),
		SHOPINFO(ChatColor.GOLD + "You have 2 minutes to use the shop."),
		THIRTYSECONDS(ChatColor.DARK_RED + "30 seconds until waves begin!" + ChatColor.RESET),
		WAVEWARNING(ChatColor.YELLOW + "Wave beginning in 10 seconds!" + ChatColor.RESET),
		WAVESTART(ChatColor.DARK_RED + "The wave has begun!" + ChatColor.RESET),
		BOSSWARNING(ChatColor.DARK_RED + "Boss Wave" + ChatColor.RESET);
		 
		private String message;
		
		private Messages(String msg) {
			this.message = msg;
		}
		
		@Override
		public String toString() {
			return message;
		}
		
		/**
		 * Returns the stirng equivalent of this predefined message.<br />
		 * For convenience, consider using {@link #toString()} instead
		 * @return
		 */
		public String getString() {
			return message;
		}
	}
	
	/**
	 * Holds the different types of time-based reminders we'd need
	 * @author Skyler
	 * @see {@link GameSession#tick(Object)}
	 */
	private enum Reminders {
		ONEMINUTE,
		THIRTYSECONDS,
		PUSHTOARENA,
		STARTWAVE,
		WAVECONTINUE;
	}
	
	
	
	//private TreeMap<Team, Wave> teams;
	private HashMap<Team, Wave> teams;
	
	//private List<Wave> waves;
	
	private State state;
	
	/**
	 * Keep a name so we can reference the game session
	 */
	private String name;
	
	/**
	 * The map with the arenas and the shop
	 */
	private Map map;
	
	/**
	 * The shop instance that's unique to this session
	 */
	private Shop sessionShop;
	
	private int waveNumber;

	public GameSession(String name, Map map) {
		this.name = name;
		this.map = map;
		this.state = State.PREGAME;
		this.teams = new HashMap<Team, Wave>();
		System.out.println("Shop Location: " + map.getShopButtonLocation());
		this.sessionShop = new Shop(map.getShopButtonLocation(), null);
	}
	
	/**
	 * @return The current state of the session
	 */
	public State getState() {
		return state;
	}

	public Map getMap() {
		return map;
	}

	/**
	 * Tries to look up a team
	 * @param name The name to look up
	 * @return The team with the given name, null if it cannot be found
	 */
	public Team getTeam(String name) {
		if (teams.isEmpty()) {
			return null;
		}
		
		for(Team t : teams.keySet()){
			if(t.getName().equals(name)){
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
	public Team getTeam(SurvivalPlayer player) {
		if (teams.isEmpty()) {
			return null;
		}
		
		for(Team t : teams.keySet()){
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
		if (teams.isEmpty()) {
			return null;
		}
		
		for(Team t: teams.keySet()){
			if(t.hasPlayer(player) != null){
				return t;
			}
		}
		return null;
	}

	/**
	 * Looks for the provided player, returning their wrapper
	 * @param player
	 * @return The survival player of the given player, or null if not found
	 */
	public SurvivalPlayer getPlayer(OfflinePlayer player) {
		if (teams.isEmpty()) {
			return null;
		}
		
		for(Team t : teams.keySet()){
			SurvivalPlayer tmp=t.hasPlayer(player);
			if(tmp != null){
				return tmp;
			}
		}
		return null;
	}

	/**
	 * Get the name associated with this session
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns summary information about this session.
	 * @param verbose Should this also give extensive information?
	 * @return a string with the name of the session, it's current state, and 
	 * the name of the map it is running. On verbose mode it also includes a 
	 * list of teams in the session as well as the current number of teams and 
	 * the max number of teams.  
	 */
	public String getInfo(boolean verbose) {
		String str = "Session Name: "+ChatFormat.SESSION.wrap(this.name)+"\n"; 
		str += "Map Name: "+ ChatFormat.IMPORTANT.wrap(this.map.getName())+"\n";
		str += "Session State: "+ChatFormat.IMPORTANT.wrap(this.state + "\n");
		
		if(verbose){
			str += "Team size: "+this.teams.size() + "/" + this.map.getMaxTeams();
			str += "\n";
			for(Team t : teams.keySet()){
				str += ChatFormat.TEAM.wrap(t.getName()) + "   ";
			}
		}
		return str;
	}

	/**
	 * Starts the game, dealing with the teams and scores, etc
	 */
	public boolean start() {
		/*
		 * Teleport teams to their positions
		 * create waves for each player
		 * start the wave
		 */
		
		if (state != State.PREGAME) {
			TeamSurvivalPlugin.plugin.getLogger().warning(
					ChatFormat.ERROR.wrap("Unable to start session, as it's already been started!"));
			return false;
		}
		
		purgeTeams();
		
		if (teams.size() == 0) {
			TeamSurvivalPlugin.plugin.getLogger().warning(
					ChatFormat.ERROR.wrap("Unable to start session, as there are no teams!"));
			return false;
		}
		
		Bukkit.getPluginManager().registerEvents(this, 
				TeamSurvivalPlugin.plugin);
		
		waveNumber = 1;
		
		//check for empty teams
		
		//generate waves
		fillWaves();
		
		//teleport teams
		moveToStart(defaultStartingBlocksApart);
		
		//start the timer
		Scheduler.getScheduler().schedule(this, Reminders.ONEMINUTE, defaultStartingTime);
		//generate waves
		
		state = State.STARTINGPERIOD;
		
		for (Team team : teams.keySet()) {
			team.sendTeamMessage(Messages.STARTINFO.toString());
			for (SurvivalPlayer player : team.getPlayers()) {
				player.healPlayer();
				player.setGamemode(GameMode.SURVIVAL);
			}
		}
		return true;
	}
	
	/**
	 * Stops the game.<br />
	 * Games stop automatically, so this method is considered an emergency operation.
	 */
	public void stop() {
		HandlerList.unregisterAll(sessionShop);
		Scheduler.getScheduler().unregister(this);
		HandlerList.unregisterAll(this);
		
		if (!teams.isEmpty()) {
			for (Team team : teams.keySet()) {
				HandlerList.unregisterAll(team);
			}
		}
		
		sessionShop = null;
		state = State.FINISHED;
		
		for (Wave wave : teams.values()) {
			if(wave != null)
				wave.stop();
		}
		
		teams.clear();
	}
	
	/**
	 * Adds a team to the session
	 * @param team
	 */
	public void addTeam(Team team) {
		if (teams.containsKey(team)) {
			return;
		}
		
		if (teams.size() >= map.getMaxTeams()) {
			TeamSurvivalPlugin.plugin.getLogger().warning("Unable to add team: session is full!");
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (player.isOp()) {
					player.sendMessage(ChatFormat.ERROR.wrap("Cannot add team: Session already full!"));
				}
			}
			return;
		}
		
		team.setArenaLocation(map.getNextArena());
		team.setBossLocation(map.getNextBoss());
		
		//Add teams
		teams.put(team, null);
	}
	
	/**
	 * Removes a team from the game session
	 * @param team the team to kick
	 * @return true if the team was successfully removed
	 */
	public boolean removeTeam(Team team){
		if (state != State.PREGAME && state != State.FINISHED) {
			System.out.println("Cannot remove team while the session is not PREGAME or FINISHED");
			return false;
		}
		
		HandlerList.unregisterAll(team);
		
		map.addArenaLocation(team.getArenaLocation());
		map.addBossLocation(team.getBossLocation());
		
		teams.get(team).stop();
		return teams.remove(team) != null;
	}
	
	
	public Collection<Team> getTeams(){
		return teams.keySet();
	}
	
	@Override
	public void tick(Object reference) {
		if (!(reference instanceof Reminders)) {
			//what the heck is this?
			return; //error
		}
		
		Reminders reminder = (Reminders) reference;
		
		switch (reminder) {
		case ONEMINUTE:
			for (Team team : teams.keySet()) {
				team.sendTeamMessage(Messages.ONEMINUTE.toString());
			}
			Scheduler.getScheduler().schedule(this, Reminders.THIRTYSECONDS, 30);
			break;
		case THIRTYSECONDS:
			for (Team team : teams.keySet()) {
				team.sendTeamMessage(Messages.THIRTYSECONDS.toString());
			}
			Scheduler.getScheduler().schedule(this, Reminders.PUSHTOARENA, 30);
			break;
		case PUSHTOARENA:
			state = State.INWAVE;
			moveToArena();
			Scheduler.getScheduler().schedule(this, Reminders.STARTWAVE, 10);
			for (Team t : teams.keySet()) {
				t.sendTeamMessage(ChatFormat.INFO.wrap("WAVE "+this.waveNumber));
				t.sendTeamMessage(Messages.WAVEWARNING.toString());
			}
			break;
		case STARTWAVE:
			startNextWave(true);
			for (Team t : teams.keySet()) {
				t.sendTeamMessage(Messages.WAVESTART.toString());
			}
			break;
		case WAVECONTINUE:
			startNextWave(false);
			for (Team t : teams.keySet()) {
				t.sendTeamMessage("WAVE "+this.waveNumber);
				t.sendTeamMessage(Messages.WAVESTART.toString());
			}			
			break;
		}
	}
	
	@Override
	public String toString() {
		return "GameSession[" + getName() + "]";
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof GameSession)) {
			return false;
		}
		return o.toString().equalsIgnoreCase(toString());
	}
	
	private void moveToArena() {
		if (teams.values().iterator().next() instanceof BossWave) {
			for (Team t : teams.keySet()) {
				t.moveTo(t.getBossLocation());
				t.sendTeamMessage(Messages.BOSSWARNING.getString());
			}
			return;
		}
		
		for (Team team : teams.keySet()) {
			team.moveTo(team.getArenaLocation());
			team.setGamemode(GameMode.ADVENTURE);
		}
	}
	
	/**
	 * moves to the teams to separated starting locations
	 * @param distanceBetween is the distance to seperate the teams by
	 */
	private void moveToStart(int distanceBetween){
		int side = (int) Math.floor(Math.sqrt(this.teams.size())) + 1;
		
		Location start = map.getStartingLocation().clone();
		Iterator<Team> iterate = teams.keySet().iterator();
		for(int i =0; i<side; i++){
			for(int j=0; j<side; j++){
				if(iterate.hasNext()){
					Team tmp = iterate.next();
					tmp.moveTo(start.getWorld().getHighestBlockAt(start).getLocation());
					TeamSurvivalPlugin.plugin.getLogger().info("Team: "
							+ tmp.getName()+ "\t starting at:"+start.getBlockX()+", "+start.getBlockY()+", "+ start.getBlockZ());
				}else{
					return;
				}
				start.add(distanceBetween, 0, 0);
			}
			start.setX(map.getStartingLocation().getX());
			start.add(0, 0, distanceBetween);
		}
	}
	
	/**
	 * Moves teams to the shop location
	 */
	private void moveToShop(){
		for(Team t : teams.keySet()){
			t.moveTo(map.getShopLocation());
		}
	}
	
	@EventHandler
	public void onTeamLose(TeamLossEvent event){
		if(!teams.containsKey(event.getTeam())){
			return;
		}
		
		//Kill the related wave
		teams.get(event.getTeam()).stop();
		
		teams.remove(event.getTeam());
		
		HandlerList.unregisterAll(event.getTeam());
		
		if(teams.size() == 1){
			teams.keySet().iterator().next().win();
			stop();
		}
	}
	
	/**
	 * Eliminates empty teams from the team list
	 */
	private void purgeTeams() {
		List<Team> teamList = new ArrayList<Team>(teams.keySet().size());
		
		teamList.addAll(teams.keySet());
		
		Iterator<Team> it = teamList.iterator();
		Team team;
		while (it.hasNext()) {
			for (Team t : teams.keySet()) {
				System.out.println("Contains key: " + t.getName());
			}
			team = it.next();
			System.out.println("Checking team: " + team.getName());
			if (team.getPlayerList().isEmpty()) {
				System.out.println("!!!Removing team: " + team.getName());
				teams.remove(team);
			}
		}
	}	
	
	/**
	 * Handles what happens at the end of a wave
	 * @param event 
	 */
	@EventHandler
	public void onWaveEnd(WaveFinishEvent event){
		
		if (!teams.values().contains(event.getWave())) {
			return;
		}

		Team key = null;
		for (Team team : teams.keySet()) {
			if (teams.get(team) != null && teams.get(team).equals(event.getWave())) {
				key = team;
				break;
			}
		}
		
		if (event.getWave() instanceof BossWave) {
			//a team just finished the boss wave!
						
			teams.put(key, null);
			
			for (Team t : teams.keySet()) {
				if (!t.equals(key)) {
					t.lose();
				}
			}
			
			key.win();
			stop();
			return;
		}
		
		teams.put(key, null);
		
		if (!areWavesNull()) {
			return;
		}
		
		//get ready for the next wave
		for (Team team : teams.keySet())
		for (SurvivalPlayer player : team.getPlayers()){
			if (player.getPlayer() == null) {
				continue;
			}
			if (player.getPlayer().getGameMode() == GameMode.SPECTATOR) {
				player.setGamemode(GameMode.ADVENTURE);
				player.healPlayer();
				player.getPlayer().teleport(team.getArenaLocation());
			}
		}
		
		this.waveNumber++;
		
		if (waveNumber > GameSession.defaultWaveCount) {
			fillBossWaves();
		} else {
			fillWaves();
		}
		
		//no more waves, but is this the end of our third one?
		if ((waveNumber-1) % 3 != 0) {

			Scheduler.getScheduler().schedule(this, Reminders.WAVECONTINUE, 10);
			for (Team t : teams.keySet()) {
				t.sendTeamMessage(Messages.WAVEWARNING.toString());
			}
			
			if (teams.values().iterator().next() instanceof BossWave)
			for (Team t : teams.keySet()) {
				t.moveTo(t.getBossLocation());
				t.sendTeamMessage(Messages.BOSSWARNING.getString());
			}
			return;
		}
		
		for (Team team : teams.keySet()) {
			team.sendTeamMessage(Messages.SHOPINFO.toString());
		}
		state = State.INSHOP;
		
		//teleport teams to the shop
		moveToShop();
		
		Scheduler.getScheduler().schedule(this, Reminders.ONEMINUTE, 60);//2 min for each shop period
		
		//this.currentWave = new Wave(waveNumber, map.getArenaLocations(), numberOfMobs(waveNumber));
		
	}
	
	/**
	 * Starts the next wave, assuming it's set up already.
	 * @param fresh whether or not the players are returning to the arena.
	 */
	private void startNextWave(boolean fresh) {
		for (Wave wave : teams.values()) {
			wave.start();
		}
	}
	
	/**
	 * Generates one wave, and then clones it for all teams<br />
	 */
	private void fillWaves() {
		
		Wave wave = new Wave(waveNumber, null, numberOfMobs());
		List<Location> locs;
		for (Team team : teams.keySet()) {
			locs = new LinkedList<Location>();
			locs.add(team.getArenaLocation());
			teams.put(team, wave.clone(locs));
		}
	}
	
	private void fillBossWaves() {
		
		Wave wave = new BossWave(null, new SkeletonGroupMob());
		List<Location> locs;
		for (Team team : teams.keySet()) {
			locs = new LinkedList<Location>();
			locs.add(team.getBossLocation());
			teams.put(team, wave.clone(locs));
		}
	}
	
	/**
	 * Calculates the number of mobs per wave
	 * @return number of mobs to spawn for the waveNumber
	 */
	private int numberOfMobs(){
		//avg the number of players still in
		int sum =0;
		for(Team t : teams.keySet()){
			sum += t.getPlayerList().size();
		}
		
		int avg = sum/teams.keySet().size();
		
		return avg + (int)(waveNumber*(avg+5));
	}
	
	/**
	 * Clears the current wave, trying to eliminate the mess. Then advances to the next wave.<br />
	 * This method is not meant to be called casually. It's instead provided as an emergency method
	 * incase entities do what entities do and become rogue.
	 */
	public void clearWave() {
		if (areWavesNull()) {
			return;
		}
		
		Wave wave = null;
		
		for (Entry<Team, Wave> entry : teams.entrySet()) {
			if (entry.getValue() != null) {
				wave = entry.getValue();
				entry.getValue().stop();
				teams.put(entry.getKey(), null);
			}
		}
		
		//wave will hold last wave
		Bukkit.getPluginManager().callEvent(new WaveFinishEvent(wave));
		
	}
	
	/**
	 * Goes through the team map and checks whether all the waves are null
	 * @return
	 */
	private boolean areWavesNull() {
		for (Entry<Team, Wave> entry : teams.entrySet()) {
			if (entry.getValue() != null) {
				return false;
			}
		}
		
		return true;
	}
}
