package nmt.minecraft.TeamSurvival.Enemy;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Event signaling that an event has finished.<br />
 * Waves should be certain to only send this once.
 * @author Skyler
 */
public class WaveFinishEvent extends Event {

	private static final HandlerList handlers = new HandlerList();
	
	private Wave wave;
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}
	
	/**
	 * Creates a wave finish event, which houses which wave has finished.
	 * @param wave The wave that finished
	 */
	public WaveFinishEvent(Wave wave) {
		this.wave = wave;
	}
	
	/**
	 * @return the wave that signaled this event
	 */
	public Wave getWave() {
		return wave;
	}
}
