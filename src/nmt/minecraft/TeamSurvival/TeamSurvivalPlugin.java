package nmt.minecraft.TeamSurvival;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
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
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		try {
			if (!(sender instanceof Player)) {
				sender.sendMessage("Only players may use this command.");
				return false;
			}
			
			if (cmd.getName().equalsIgnoreCase("startTestWave")) {
				List<Location> spawnPoints = new ArrayList<Location>();
				spawnPoints.add(new Location(TeamSurvivalPlugin.plugin.getServer().getWorld("world"), 0.0, 70.0, 0.0));
				spawnPoints.add(new Location(TeamSurvivalPlugin.plugin.getServer().getWorld("world"), 20.0, 70.0, 0.0));
				spawnPoints.add(new Location(TeamSurvivalPlugin.plugin.getServer().getWorld("world"), 0.0, 70.0, 20.0));
				spawnPoints.add(new Location(TeamSurvivalPlugin.plugin.getServer().getWorld("world"), -20.0, 70.0, 0.0));
				spawnPoints.add(new Location(TeamSurvivalPlugin.plugin.getServer().getWorld("world"), 0.0, 70.0, -20.0));
				Wave testWave = new Wave(Integer.parseInt(args[0]), spawnPoints, 100, 100);
				//Wave testWave = new Wave(1, spawnPoints, 100, 100);
				testWave.start();
				Waves.add(testWave);
			}
			
			if (cmd.getName().equalsIgnoreCase("stopWaves")) {
				for(Wave foundWave : Waves) {
					foundWave.stop();
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
