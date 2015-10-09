package nmt.minecraft.TeamSurvival.IO;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import nmt.minecraft.TeamSurvival.Map.Map;

/**
 * Does tab completion for the creation command
 * @author Stephanie
 */
public class CreationTabCompleter implements TabCompleter{

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if(args.length == 1){
			return getBeginList(args[0], CreationCommand.getCommands());
		}
		
		// /teamsurvivalcreator [command] [configName]
		if(args.length == 2 && (args[0].equals("open")||args[0].equals("close"))){
			return getBeginList(args[1], Map.listConfigs());
		}

		return null;
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
