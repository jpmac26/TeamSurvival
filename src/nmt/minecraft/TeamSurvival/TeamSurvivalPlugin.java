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
		getServer().getPluginManager().registerEvents(this, this);
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
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		try {
			if (!(sender instanceof Player)) {
				sender.sendMessage("Only players may use this command.");
				return false;
			}
			
			//NOTE: These commands are just for testing purposes, this is not how we want to position the spawned mobs
			//when we actually implement this plugin, and we also probably don't want 100 mobs in each wave,
			//or even to have 40 spawned at once, because that's gonna be way too hard.
			if (cmd.getName().equalsIgnoreCase("startTestWave")) {
				List<Location> spawnPoints = new ArrayList<Location>();
				spawnPoints.add(new Location(((Player)sender).getLocation().getWorld(), 0.0, 70.0, 0.0));
				spawnPoints.add(new Location(((Player)sender).getLocation().getWorld(), 20.0, 70.0, 0.0));
				spawnPoints.add(new Location(((Player)sender).getLocation().getWorld(), 0.0, 70.0, 20.0));
				spawnPoints.add(new Location(((Player)sender).getLocation().getWorld(), -20.0, 70.0, 0.0));
				spawnPoints.add(new Location(((Player)sender).getLocation().getWorld(), 0.0, 70.0, -20.0));
				Wave testWave;
				if(args.length == 0 || args[0].isEmpty() || Integer.parseInt(args[0]) <= 0) {
					testWave = new Wave(1, spawnPoints, 100);
				} else {
					testWave = new Wave(Integer.parseInt(args[0]), spawnPoints, 100);
				}
				//Wave testWave = new Wave(1, spawnPoints, 100, 100);
				testWave.start();
				Waves.add(testWave);
			}
			
			if (cmd.getName().equalsIgnoreCase("stopWaves")) {
				for(Wave foundWave : Waves) {
					foundWave.stop();
				}
				Waves.clear();
			}
			
			if (cmd.getName().equalsIgnoreCase("killAllMonsters")) {
				for(LivingEntity ent : ((Player)sender).getLocation().getWorld().getLivingEntities()) {
					if(ent.getType() == EntityType.CREEPER | ent.getType() == EntityType.SKELETON
							| ent.getType() == EntityType.ZOMBIE | ent.getType() == EntityType.ENDERMITE
							| ent.getType() == EntityType.CREEPER | ent.getType() == EntityType.SPIDER
							| ent.getType() == EntityType.CAVE_SPIDER) {
						ent.remove();
					}
				}
			}
			
		} 
		catch (Exception e) {
			TeamSurvivalPlugin.plugin.getLogger().info("Error: " + e + "\r\n");
			e.printStackTrace();
		}
		
		return true;
	}
}
