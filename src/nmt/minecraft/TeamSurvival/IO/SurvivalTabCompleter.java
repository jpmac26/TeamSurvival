package nmt.minecraft.TeamSurvival.IO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import nmt.minecraft.TeamSurvival.TeamSurvivalManager;
import nmt.minecraft.TeamSurvival.Session.GameSession;

public class SurvivalTabCompleter implements TabCompleter{
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
		List<String> list = null;
		
		if(args.length == 1){
			return getBeginList(args[0], SurvivalCommands.getCommandList());
		}
				
		switch(cmd.getName()){
		case "teamsurvival":
			if(args.length ==2){
				list = getBeginList(args[1],SurvivalCommands.getSubCommandList());
				return list;
			}
			break;
		case "jointeam":
			//figure out a way to get a list of teams
			Collection<GameSession> sessions = TeamSurvivalManager.getSessions();
			
			break;
		case "default":
			return null;
		}
		
		
		return list;
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
