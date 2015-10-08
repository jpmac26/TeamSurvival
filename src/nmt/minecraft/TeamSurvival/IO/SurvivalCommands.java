package nmt.minecraft.TeamSurvival.IO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import nmt.minecraft.TeamSurvival.TeamSurvivalPlugin;
import nmt.minecraft.TeamSurvival.Enemy.Wave;

public class SurvivalCommands implements CommandExecutor {
	private static String[] commandList = {"someCommand", "startTestWave"};
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players may use this command.");
			return false;
		}
		TeamSurvivalPlugin.plugin.getLogger().info("In commands");
		
		if (cmd.getName().equalsIgnoreCase("startTestWave")) {
			List<Location> spawnPoints = new ArrayList<Location>();
			spawnPoints.add(new Location(TeamSurvivalPlugin.plugin.getServer().getWorld("world"), 0.0f, 70.0f, 0.0f));
			Wave testWave = new Wave(1, spawnPoints, 25);
			testWave.start();
		}
		
		//TODO
		
		return true;
	}
	
	public static List<String> getCommandList(){
		return Arrays.asList(commandList);
	}
}
