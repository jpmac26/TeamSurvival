package nmt.minecraft.TeamSurvival;


import org.bukkit.plugin.java.JavaPlugin;

import nmt.minecraft.TeamSurvival.IO.CreationCommand;
import nmt.minecraft.TeamSurvival.IO.CreationTabCompleter;
import nmt.minecraft.TeamSurvival.IO.JoinTeamCommand;
import nmt.minecraft.TeamSurvival.IO.JoinTeamTabCompleter;
import nmt.minecraft.TeamSurvival.IO.LeaveTeamCommand;
import nmt.minecraft.TeamSurvival.IO.SurvivalCommand;
import nmt.minecraft.TeamSurvival.IO.SurvivalTabCompleter;
import nmt.minecraft.TeamSurvival.Session.GameSession;
import nmt.minecraft.TeamSurvival.Util.LocationState;

/**
 * Plugin class.<br />
 * Creates everything and all things and everything.
 * @author Skyler
 * @author Stephanie
 *
 */
public class TeamSurvivalPlugin extends JavaPlugin {
	
	public static JavaPlugin plugin;
	
	@Override
	public void onEnable() {
		this.getCommand("leaveteam").setExecutor(new LeaveTeamCommand());
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
}
