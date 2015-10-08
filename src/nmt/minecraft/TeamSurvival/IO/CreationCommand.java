package nmt.minecraft.TeamSurvival.IO;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;
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
			sender.sendMessage(ChatFormat.ERROR.wrap("/tsc [session|team] {args}"));
			return false;
		}
		boolean tmp;
		if (args[0].equalsIgnoreCase("open")) {
			tmp = onOpenCommand(sender, args);
			if(tmp){
				sender.sendMessage(ChatFormat.IMPORTANT.wrap("Sucessfully opened session: "+args[1]));
			}else{
				sender.sendMessage(ChatFormat.IMPORTANT.wrap("unable to open session"));
				return false;
			}
			
		}
		
		if (args[0].equalsIgnoreCase("close")) {
			tmp = onCloseCommand(sender, args);
			if(tmp){
				sender.sendMessage(ChatFormat.IMPORTANT.wrap("Sucessfully closed open session."));
			}else{
				sender.sendMessage(ChatFormat.IMPORTANT.wrap("unable to close session"));
				return false;
			}
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
		// /teamsurvivalcreator open [name]
		if(args.length != 2){
			sender.sendMessage(ChatFormat.ERROR.wrap("Wrong number of arguments") +
					ChatFormat.IMPORTANT.wrap("\n usage: /teamsurvivalcreator open [name]"));
			return false;
		}
		
		return Creator.open(sender, args[1]);
	}
	
	/**
	 * Handles admin 'close' command
	 * @param sender
	 * @param args
	 */
	private boolean onCloseCommand(CommandSender sender, String[] args) {
		// /teamsurvivalcreator close
		if(args.length != 1){
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
		// /teamsurvivalcreator setshop
		if(args.length != 1){
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
		// /teamsurvivalcreator setshopbutton
		if(args.length != 1){
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
		if(args.length != 1){
			sender.sendMessage(ChatFormat.WARNING.wrap("Wrong number of arguments. Still attempting to add..."));
		}
		return Creator.addArena(sender);
	}

}
