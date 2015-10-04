package nmt.minecraft.TeamSurvival.Enemy;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

/**
 * A wave of enemies.<br />
 * Responsible for keeping track of how many enemies are left to spawn and providing a way to spawn one.
 * @author Skyler
 *
 */
public class Wave {
	
	
	/**
	 * Creates a wave with the given types of mobs.
	 * @param mob1
	 * @param mob2
	 * @param mob3
	 * @param count
	 * @note See wave implementation in google doc file
	 */
	public Wave(Mob mob1, Mob mob2, Mob mob3, int count) {
		; //TODO
	}
	
	/**
	 * Gets a random mob from what's left to spawn and spawns it, returning the entity
	 * @return
	 */
	public LivingEntity spawnRandomMob(Location location) {
		return null; //TODO
	}
}
