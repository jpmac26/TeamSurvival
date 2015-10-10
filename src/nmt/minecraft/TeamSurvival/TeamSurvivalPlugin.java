package nmt.minecraft.TeamSurvival;


import java.util.ArrayList;
import java.util.List;

import nmt.minecraft.TeamSurvival.Enemy.Wave;
import nmt.minecraft.TeamSurvival.IO.CreationCommand;
import nmt.minecraft.TeamSurvival.IO.CreationTabCompleter;
import nmt.minecraft.TeamSurvival.IO.JoinTeamCommand;
import nmt.minecraft.TeamSurvival.IO.JoinTeamTabCompleter;
import nmt.minecraft.TeamSurvival.IO.SurvivalCommand;
import nmt.minecraft.TeamSurvival.IO.SurvivalTabCompleter;
import nmt.minecraft.TeamSurvival.Session.GameSession;
import nmt.minecraft.TeamSurvival.Util.LocationState;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Plugin class.<br />
 * Creates everything and all things and everything.
 * @author Skyler
 * @author Stephanie
 *
 */
public class TeamSurvivalPlugin extends JavaPlugin implements Listener {
	
	public static JavaPlugin plugin;
	private List<Wave> Waves = new ArrayList<Wave>();
	
	@Override
	public void onEnable() {
		this.getCommand("jointeam").setExecutor(new JoinTeamCommand());
		this.getCommand("jointeam").setTabCompleter(new JoinTeamTabCompleter());
		this.getCommand("teamsurvival").setExecutor(new SurvivalCommand());
		this.getCommand("teamsurvival").setTabCompleter(new SurvivalTabCompleter());
		this.getCommand("teamsurvivalcreator").setExecutor(new CreationCommand());
		this.getCommand("teamsurvivalcreator").setTabCompleter(new CreationTabCompleter());
		
		LocationState.registerWithAliases();
		
	}
	
	@Override
	public void onDisable() {

		if (TeamSurvivalManager.getSessions().isEmpty()) {
			return; //nothing to stop
		}
		
		getLogger().warning("Stopping " + TeamSurvivalManager.getSessions().size() + " sessions!");
		
		for (GameSession session : TeamSurvivalManager.getSessions()) {
			//stop all sessions
			session.stop();
		}
		
	}
	
	@Override
	public void onLoad() {
		TeamSurvivalPlugin.plugin = this;
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	private void triggerWaveNewSpawnCheck(EntityDeathEvent event) {
		for(Wave foundWave : Waves) {
			foundWave.onEntityDeath(event);
		}	
	}
}
