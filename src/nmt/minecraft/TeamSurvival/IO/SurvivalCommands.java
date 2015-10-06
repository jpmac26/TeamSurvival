package nmt.minecraft.TeamSurvival.IO;

import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SurvivalCommands implements CommandExecutor {
	
	/**
	 * The plugin's commands, handled by this executor
	 */
	private static final String[] commandList = {"teamsurvival", "jointeam"};
	
	/**
	 * A list of commands handled by the 'team survival' wrapping command
	 */
	private static final String[] subCommandList = {"session", "team"};
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("teamsurvival")) {
			onTeamSurvivalCommand(sender, args);
			return true;
		}
		
		if (cmd.getName().equalsIgnoreCase("jointeam")) {
			onJoinCommand(sender, args);
		}
		
		return false;
	}
	
	public static List<String> getCommandList(){
		return Arrays.asList(commandList);
	}
	
	/**
	 * Handles a join command
	 * @param sender Who send the command
	 * @param args The args passed to the command
	 */
	private void onJoinCommand(CommandSender sender, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players can use this command!");
			return;
		}
		
		; //TODO
	}
	
	/**
	 * Handles the admin-command wrapper
	 * @param sender Who sent the command
	 * @param args The arguments they supplied
	 */
	private void onTeamSurvivalCommand(CommandSender sender, String[] args) {
		if (args.length == 0) {
			//print usage
			sender.sendMessage("/ts [session|team] {args}");
			return;
		}
		
		if (args[0].equalsIgnoreCase("session")) {
			onSessionCommand(sender, args);
			return;
		}
		
		if (args[0].equalsIgnoreCase("team")) {
			onTeamCommand(sender, args);
			return;
		}
	}
	
	/**
	 * Handles the admin 'session' command
	 * @param sender
	 * @param args
	 */
	private void onSessionCommand(CommandSender sender, String[] args) {
		
	}
	
	/**
	 * HAndles the admin 'team' command
	 * @param sender
	 * @param args
	 */
	private void onTeamCommand(CommandSender sender, String[] args) {
		
	}
}
