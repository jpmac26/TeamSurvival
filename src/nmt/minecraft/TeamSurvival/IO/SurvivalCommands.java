package nmt.minecraft.TeamSurvival.IO;

import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SurvivalCommands implements CommandExecutor {
	private static String[] commandList = {"teamsurvival", "jointeam"};
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players may use this command.");
			return false;
		}
		
		//TODO
		
		return true;
	}
	
	public static List<String> getCommandList(){
		return Arrays.asList(commandList);
	}
}
