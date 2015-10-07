package nmt.minecraft.TeamSurvival.IO;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CreationCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		// TODO Auto-generated method stub
		if (args.length == 0) {
			//print usage
			sender.sendMessage("/ts [session|team] {args}");
			return true;
		}
		
		if (args[0].equalsIgnoreCase("new")) {
			onNewCommand(sender, args);
			return true;
		}
		
		if (args[0].equalsIgnoreCase("open")) {
			onOpenCommand(sender, args);
			return true;
		}
		
		if (args[0].equalsIgnoreCase("close")) {
			onCloseCommand(sender, args);
			return true;
		}
		
		if (args[0].equalsIgnoreCase("setShop")) {
			onSetShopCommand(sender, args);
			return true;
		}
		
		if (args[0].equalsIgnoreCase("addArena")) {
			onAddArenaCommand(sender, args);
			return true;
		}
		
		return false;
	}
	
	/**
	 * Handles admin 'new' command
	 * @param sender
	 * @param args
	 */
	private void onNewCommand(CommandSender sender, String[] args) {
		//TODO
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
