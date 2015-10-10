package nmt.minecraft.TeamSurvival;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import nmt.minecraft.TeamSurvival.Player.Team;

/**
 * This Event Class is called whenever a team has officially lost.
 * It contains the team class.
 * @author William
 *
 */
public class TeamLossEvent extends Event {

	private static final HandlerList handlers = new HandlerList();
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}
	
	private Team losingTeam;
	
	public TeamLossEvent(Team losingTeam) {
		this.losingTeam = losingTeam;
	}
	
	/**
	 * Return the losing team's object.
	 * @return The team that just lost.
	 */
	public Team getTeam() {
		return this.losingTeam;
	}

}
