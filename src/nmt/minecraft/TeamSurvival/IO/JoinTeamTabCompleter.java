package nmt.minecraft.TeamSurvival.IO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import nmt.minecraft.TeamSurvival.TeamSurvivalManager;
import nmt.minecraft.TeamSurvival.Player.Team;
import nmt.minecraft.TeamSurvival.Session.GameSession;

/**
 * Does tab completion for the join command.
 * @author Stephanie
 */
public class JoinTeamTabCompleter implements TabCompleter{

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if(args.length == 1){
			//get list of sessions
			Collection<GameSession> sessions = TeamSurvivalManager.getSessions();
			List<String> sessionList = new ArrayList<String>();
			for(GameSession s : sessions){
				sessionList.add(s.getName());
			}
			return getBeginList(args[0], sessionList);
		}else if(args.length == 2){
			//get list of teams
			Collection<GameSession> sessions = TeamSurvivalManager.getSessions();
			List<String> teamList = new ArrayList<String>();
			for(GameSession s : sessions){
				if(s.getName().equals(args[0])){
					for(Team t : s.getTeams()){
						teamList.add(t.getName());
					}
					break;
				}
			}
			return getBeginList(args[1],teamList);
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
