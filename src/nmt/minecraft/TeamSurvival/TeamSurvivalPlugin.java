package nmt.minecraft.TeamSurvival;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

import nmt.minecraft.TeamSurvival.Enemy.Wave;

/**
 * Plugin class.<br />
 * Creates everything and all things and everything.
 * @author Skyler
 *
 */
public class TeamSurvivalPlugin extends JavaPlugin implements Listener {
	
	public static JavaPlugin plugin;
	private List<Wave> Waves = new ArrayList<Wave>();
	
	
	@Override
	public void onEnable() {
		
	}
	
	@Override
	public void onDisable() {
		for(Wave foundWave : Waves) {
			foundWave.stop();
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
