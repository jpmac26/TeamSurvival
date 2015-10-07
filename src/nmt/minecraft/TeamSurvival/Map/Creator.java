package nmt.minecraft.TeamSurvival.Map;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import nmt.minecraft.TeamSurvival.IO.ChatFormat;

public final class Creator {
	private static Map current = null;
	
	public static boolean open(CommandSender sender,String name){
		if(current != null){
			sender.sendMessage(ChatFormat.ERROR.wrap("There is already an open session"));
			return false;
		}
		
		if(Map.listConfigs().contains(name)){
			//TODO: call Map's load config for the file
		}else{
			Creator.current = new Map(name);
		}
		return true;
	}
	
	public static boolean close(CommandSender sender){
		if(current == null){
			sender.sendMessage(ChatFormat.WARNING.wrap("There is no open session"));
			return false;
		}
		
		Creator.current = null;
		
		return true;
	}
	
	public static boolean addArena(CommandSender sender){
		if(current == null){
			sender.sendMessage(ChatFormat.ERROR.wrap("There is no open session"));
			return false;
		}
		
		if(sender instanceof Player){
			Creator.current.addArenaLocation(((Player)sender).getLocation());
		}else{
			sender.sendMessage(ChatFormat.ERROR.wrap("You need to be a player!"));
			sender.sendMessage("Set added arena at: "+Creator.getLocation(((Player)sender).getLocation()));
		}
		return true;
	}
	
	public static boolean setShopLocation(CommandSender sender){
		if(current == null){
			sender.sendMessage(ChatFormat.ERROR.wrap("There is no open session"));
			return false;
		}
		
		if(sender instanceof Player){
			Creator.current.setShopLocation(((Player)sender).getLocation());
			sender.sendMessage("Set shop location to: "+Creator.getLocation(((Player)sender).getLocation()));
		}else{
			sender.sendMessage(ChatFormat.ERROR.wrap("You need to be a player!"));
		}
		
		return true;
	}
	
	public static boolean setShopButton(CommandSender sender){
		if(current == null){
			ChatFormat.ERROR.wrap("There is no open session");
			return false;
		}
		if(sender instanceof Player){
			Creator.current.setShopButton(((Player)sender).getLocation());
			sender.sendMessage("Set button to: "+Creator.getLocation(((Player)sender).getLocation()));
		}else{
			sender.sendMessage(ChatFormat.ERROR.wrap("You need to be a player!"));
		}
		
		return true;
	}
	
	private static String getLocation(Location location){
		String loc = "";
		loc += location.getX()+", ";
		loc += location.getY()+", ";
		loc += location.getZ()+", ";
		return loc;
	}
	
}
