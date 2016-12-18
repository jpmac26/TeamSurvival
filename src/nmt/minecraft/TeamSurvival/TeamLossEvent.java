package nmt.minecraft.TeamSurvival;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import nmt.minecraft.TeamSurvival.Player.Team;

/**
 * This Event is called whenever a team has officially lost.
 * It contains the team class.
 *
 * @author William
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
	
	/**
	 * This variable contains a reference to the losing team
	 */
	private Team losingTeam;
	
	/**
	 * The constructor for this event requires the reference to the team that lost
	 * @param losingTeam
	 */
	public TeamLossEvent(Team losingTeam) {
		this.losingTeam = losingTeam;
	}
	
	/**
	 * Return the losing team's object
	 * @return The team that just lost
	 */
	public Team getTeam() {
		return this.losingTeam;
	}

}
