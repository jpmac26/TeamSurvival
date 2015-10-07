package nmt.minecraft.TeamSurvival.IO;

import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * 
 * @author Stephanie Martinez
 *
 */
public class CreationCommand implements CommandExecutor{
	
	private static final String[] commands = {"open", "close", "setShop", "addArena", "addShopButton"};
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		// TODO Auto-generated method stub
		if (args.length == 0) {
			//print usage
			sender.sendMessage(ChatFormat.WARNING.wrap("/ts [session|team] {args}"));
			return false;
		}
		
		if (args[0].equalsIgnoreCase("open")) {
			onOpenCommand(sender, args);
		}
		
		if (args[0].equalsIgnoreCase("close")) {
			onCloseCommand(sender, args);
		}
		
		if (args[0].equalsIgnoreCase("setShop")) {
			onSetShopCommand(sender, args);
		}
		
		if (args[0].equalsIgnoreCase("addArena")) {
			onAddArenaCommand(sender, args);
		}
		
		if (args[0].equalsIgnoreCase("addShopButton")) {
			onAddShopButtonCommand(sender, args);
		}
		return true;
	}
	
	private void onAddShopButtonCommand(CommandSender sender, String[] args) {
		// TODO Auto-generated method stub
		
	}

	public static List<String> getCommands(){
		return Arrays.asList(commands);
	}
	
	/**
	 * Handles admin 'open' command
	 * @param sender
	 * @param args
	 */
	private void onOpenCommand(CommandSender sender, String[] args) {
		//TODO
	}
	
	/**
	 * Handles admin 'close' command
	 * @param sender
	 * @param args
	 */
	private void onCloseCommand(CommandSender sender, String[] args) {
		//TODO
	}
	
	/**
	 * Handles admin 'setShop' command
	 * @param sender
	 * @param args
	 */
	private void onSetShopCommand(CommandSender sender, String[] args) {
		//TODO
	}
	
	/**
	 * Handles admin 'addArena' command
	 * @param sender
	 * @param args
	 */
	private void onAddArenaCommand(CommandSender sender, String[] args) {
		//TODO
	}

}
