package nmt.minecraft.TeamSurvival.IO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import nmt.minecraft.TeamSurvival.TeamSurvivalManager;
import nmt.minecraft.TeamSurvival.Map.Map;
import nmt.minecraft.TeamSurvival.Player.Team;
import nmt.minecraft.TeamSurvival.Session.GameSession;

/**
 * 
 * @author Stephanie
 *
 */
public class SurvivalTabCompleter implements TabCompleter{
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
		if(args.length <1){
			return null;
		}
		
		// /teamsurvival [session|team] [args]
		switch(args[0]){
		case "session":
			if(args.length == 2){
				// /teamsurvival session [command]
				return getBeginList(args[1], SurvivalCommand.getSessioncommandlist());
			}else if(args.length == 3 && (!args[1].equals("list"))){//list does not take a session argument
				//teamsurvival session [command] [sessionName]
				return getBeginList(args[2], getSessionList());
			}else if(args.length == 4){
				switch(args[1]){
				// /teamsurvival session create [sessionName] [mapName]
				case "create":
					return getBeginList(args[3], Map.listConfigs());
					
				// /teamsurvival session info [session] verbose
				case "info":
					List<String> list = new LinkedList<String>();
					list.add("verbose");
					list.add("");
					return list;
				}
			}
			break;
		case "team":
			if(args.length == 2){
				return getBeginList(args[1], SurvivalCommand.getTeamcommandlist());
			}else if(args.length==3){
				return getBeginList(args[2], getSessionList());
			}else if(args.length == 4 && (!args[2].equals("list"))){//list does not take a team argument
				return getBeginList(args[3], getTeamList(args[2]));
			}
		default:
			return getBeginList(args[0], SurvivalCommand.getTeamsurvivalcommandlist());
		}
		
		return null;
	}
	
	private List<String> getTeamList(String sessionName){
		Collection<GameSession> list = TeamSurvivalManager.getSessions();
		LinkedList<String> names = new LinkedList<String>();
		for(GameSession s : list){
			if(s.getName().equals(sessionName)){
				for(Team t : s.getTeams()){
					names.add(t.getName());
				}
				return names;
			}
		}
		return names;
	}
	
	private List<String> getSessionList(){
		Collection<GameSession> list = TeamSurvivalManager.getSessions();
		LinkedList<String> names = new LinkedList<String>();
		for(GameSession s : list){
			names.add(s.getName());
		}
		return names;
	}
	
	private List<String> getBeginList(String key, List<String> totalList){
		List<String> list = new ArrayList<String>();
		
		for(String s : totalList){
			if(startsWithIgnoreCase(s,key)){
				list.add(s);
			}
		}
		
		return list;
	}
	
	private static boolean startsWithIgnoreCase(String string1, String string2){
		string1 = string1.toLowerCase();
		string2 = string2.toLowerCase();
		return string1.startsWith(string2);
	}
}
