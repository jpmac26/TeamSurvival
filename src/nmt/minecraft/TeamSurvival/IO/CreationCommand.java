package nmt.minecraft.TeamSurvival.IO;

import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CreationCommand implements CommandExecutor{
	
	private static final String[] commands = {"new", "open", "close", "setShop", "addArena"};
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public static List<String> getCommands(){
		return Arrays.asList(commands);
	}

}
