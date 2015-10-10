package nmt.minecraft.TeamSurvival;

import nmt.minecraft.TeamSurvival.Player.Team;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * This Event Class is called whenever a team has officially lost.
 * It contains the team class.
 * @author William
 *
 */
public class TeamLossEvent extends Event {
	
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
	
	@Override
	public HandlerList getHandlers() {
		return null;
	}

}
