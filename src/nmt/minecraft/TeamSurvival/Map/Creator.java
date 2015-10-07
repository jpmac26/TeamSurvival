package nmt.minecraft.TeamSurvival.Map;

import org.bukkit.Location;

import nmt.minecraft.TeamSurvival.IO.ChatFormat;

public class Creator {
	private static Map current = null;
	
	public static boolean open(String name){
		if(current != null){
			ChatFormat.ERROR.wrap("There is already an open session");
			return false;
		}
		
		Creator.current = new Map(name);
		
		return true;
	}
	
	public static boolean close(){
		if(current == null){
			ChatFormat.WARNING.wrap("There is no open session");
			return false;
		}
		
		Creator.current = null;
		
		return true;
	}
	
	public static boolean addArena(Location location){
		if(current == null){
			ChatFormat.ERROR.wrap("There is no open session");
			return false;
		}
		
		Creator.current.addArenaLocation(location);
		
		return true;
	}
	
	public static boolean setShopLocation(Location location){
		if(current == null){
			ChatFormat.ERROR.wrap("There is no open session");
			return false;
		}
		
		Creator.current.setShopLocation(location);
		
		return true;
	}
	
	public static boolean setShopButton(Location location){
		if(current == null){
			ChatFormat.ERROR.wrap("There is no open session");
			return false;
		}
		
		Creator.current.setShopButton(location);
		
		return true;
	}
	
	
}
