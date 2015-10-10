package nmt.minecraft.TeamSurvival.Session;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import nmt.minecraft.TeamSurvival.TeamSurvivalPlugin;
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
		WAVESTART(ChatColor.DARK_RED + "The wave has begun!" + ChatColor.RESET);
		 
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
		SHOPOVER,
		STARTWAVE,
		WAVECONTINUE;
	}
	
	
	
	//private TreeMap<Team, Wave> teams;
	private List<Team> teams;
	
	private List<Wave> waves;
	
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
		this.teams = new LinkedList<Team>();
		this.waves = new LinkedList<Wave>();
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
		
		for(Team t : teams){
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
		
		for(Team t : teams){
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
		
		for(Team t: teams){
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
		
		for(Team t : teams){
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
			for(Team t : teams){
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
		if (teams.size() == 0) {
			TeamSurvivalPlugin.plugin.getLogger().warning(
					ChatFormat.ERROR.wrap("Unable to start session, as there are no teams!"));
			return false;
		}
		
		if (state != State.PREGAME) {
			TeamSurvivalPlugin.plugin.getLogger().warning(
					ChatFormat.ERROR.wrap("Unable to start session, as it's already been started!"));
			return false;
		}
		
		Bukkit.getPluginManager().registerEvents(this, 
				TeamSurvivalPlugin.plugin);
		
		waveNumber = 1;
		
		//generate waves
		fillWaves();
		
		//teleport teams
		moveToStart(4);//TODO only 4 blocks apart for testing
		
		//start the timer
		Scheduler.getScheduler().schedule(this, Reminders.ONEMINUTE, 1);//TODO 15 min to start
		//generate waves
		
		state = State.STARTINGPERIOD;
		
		for (Team team : teams) {
			team.sendTeamMessage(Messages.STARTINFO.toString());
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
		sessionShop = null;
		state = State.FINISHED;
		
		for (Wave wave : waves) {
			wave.stop();
		}
	}
	
	/**
	 * Adds a team to the session
	 * @param team
	 */
	public void addTeam(Team team) {
		if (teams.contains(team)) {
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
		List<Location> lists = new LinkedList<Location>();
		lists.add(team.getArenaLocation());
		
		//Add teams
		teams.add(team);
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
		return teams.remove(team);
	}
	
	
	public Collection<Team> getTeams(){
		return teams;
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
			for (Team team : teams) {
				team.sendTeamMessage(Messages.ONEMINUTE.toString());
			}
			Scheduler.getScheduler().schedule(this, Reminders.THIRTYSECONDS, 30);
			break;
		case THIRTYSECONDS:
			for (Team team : teams) {
				team.sendTeamMessage(Messages.THIRTYSECONDS.toString());
			}
			Scheduler.getScheduler().schedule(this, Reminders.PUSHTOARENA, 30);
			break;
		case PUSHTOARENA:
			state = State.INWAVE;
			moveToArena();
			Scheduler.getScheduler().schedule(this, Reminders.STARTWAVE, 10);
			for (Team t : teams) {
				t.sendTeamMessage(Messages.WAVEWARNING.toString());
			}
			break;
		case SHOPOVER:
			state = State.INWAVE;
			moveToArena();
			Scheduler.getScheduler().schedule(this, Reminders.STARTWAVE, 10);
			for (Team t : teams) {
				t.sendTeamMessage(Messages.WAVEWARNING.toString());
			}
			break;
		case STARTWAVE:
			startNextWave(true);
			for (Team t : teams) {
				t.sendTeamMessage(Messages.WAVESTART.toString());
			}
			break;
		case WAVECONTINUE:
			startNextWave(false);
			for (Team t : teams) {
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
		return o.toString().equalsIgnoreCase(toString());
	}
	
	private void moveToArena() {
		for (Team team : teams) {
			team.moveTo(team.getArenaLocation());
		}
	}
	
	/**
	 * moves to the teams to separated starting locations
	 * @param distanceBetween is the distance to seperate the teams by
	 */
	private void moveToStart(int distanceBetween){
		int side = (int) Math.floor(Math.sqrt(this.teams.size())) + 1;
		
		Location start = map.getStartingLocation().clone();
		Iterator<Team> iterate = teams.iterator();
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
		for(Team t : teams){
			t.moveTo(map.getShopLocation());
		}
	}
	
	
	
	/**
	 * Handles what happens at the end of a wave
	 * @param event 
	 */
	@EventHandler
	public void onWaveEnd(WaveFinishEvent event){
		//this.currentWave = null; //TODO
		if (!waves.contains(event.getWave())) {
			return;
		}
		
		waves.remove(event.getWave());
		
		if (!waves.isEmpty()) {
			return;
		}
		
		this.waveNumber++;
		//TODO TeamLossEvent
		fillWaves();
		
		//no more waves, but is this the end of our third one?
		if ((waveNumber-1) % 3 != 0) {

			Scheduler.getScheduler().schedule(this, Reminders.WAVECONTINUE, 10);
			for (Team t : teams) {
				t.sendTeamMessage(Messages.WAVEWARNING.toString());
			}
			return;
		}
		
		for (Team team : teams) {
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
		for (Wave wave : waves) {
			wave.start();
		}
	}
	
	/**
	 * Generates one wave, and then clones it for all teams<br />
	 * <b>This method will clear the waves list if it's not empty!</b>
	 */
	private void fillWaves() {
		if (!waves.isEmpty()) {
			waves.clear();
		}
		
		Wave wave = new Wave(waveNumber, null, numberOfMobs());
		List<Location> locs;
		for (Team team : teams) {
			locs = new LinkedList<Location>();
			locs.add(team.getArenaLocation());
			waves.add(wave.clone(locs));
		}
	}
	
	/**
	 * Calculates the number of mobs per wave
	 * @return number of mobs to spawn for the waveNumber
	 */
	private int numberOfMobs(){
		//avg the number of players still in
		int sum =0;
		for(Team t : teams){
			sum += t.getPlayerList().size();
		}
		
		int avg = sum/teams.size();
		
		return avg + (int)(waveNumber*(avg+5));
	}
}
