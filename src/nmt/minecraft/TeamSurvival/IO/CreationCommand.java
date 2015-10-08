package nmt.minecraft.TeamSurvival.IO;

import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import nmt.minecraft.TeamSurvival.Map.Creator;

/**
 * 
 * @author Stephanie
 */
public class CreationCommand implements CommandExecutor{
	
	private static final String[] commands = {"open", "close", "setShop", "addArena", "setShopButton"};
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0) {
			//print usage
			sender.sendMessage(ChatFormat.ERROR.wrap("/ts [session|team] {args}"));
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
		
		if (args[0].equalsIgnoreCase("setShopButton")) {
			onSetShopButtonCommand(sender, args);
		}
		return true;
	}

	public static List<String> getCommands(){
		return Arrays.asList(commands);
	}
	
	/**
	 * Handles admin 'open' command
	 * @param sender
	 * @param args
	 */
	private boolean onOpenCommand(CommandSender sender, String[] args) {
		if(args.length != 1){
			sender.sendMessage(ChatFormat.ERROR.wrap("Wrong number of arguments"));
			return false;
		}
		
		return Creator.open(sender, args[0]);
	}
	
	/**
	 * Handles admin 'close' command
	 * @param sender
	 * @param args
	 */
	private boolean onCloseCommand(CommandSender sender, String[] args) {
		if(args.length != 0){
			sender.sendMessage(ChatFormat.WARNING.wrap("Wrong number of arguments. Still closing..."));
		}
		return Creator.close(sender);
	}
	
	/**
	 * Handles admin 'setShop' command
	 * @param sender
	 * @param args
	 */
	private boolean onSetShopCommand(CommandSender sender, String[] args) {
		if(args.length != 0){
			sender.sendMessage(ChatFormat.WARNING.wrap("Wrong number of arguments. Still attempting to set..."));
		}
		return Creator.setShopLocation(sender);
	}
	
	/**
	 * Handles admin 'setShopButton' command
	 * @param sender
	 * @param args
	 */
	private boolean onSetShopButtonCommand(CommandSender sender, String[] args) {
		if(args.length != 0){
			sender.sendMessage(ChatFormat.WARNING.wrap("Wrong number of arguments. Still attempting to set..."));
		}
		return Creator.setShopButton(sender);
	}
	
	/**
	 * Handles admin 'addArena' command
	 * @param sender
	 * @param args
	 */
	private boolean onAddArenaCommand(CommandSender sender, String[] args) {
		if(args.length != 0){
			sender.sendMessage(ChatFormat.WARNING.wrap("Wrong number of arguments. Still attempting to add..."));
		}
		return Creator.setShopLocation(sender);
	}

}
