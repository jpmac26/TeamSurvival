package nmt.minecraft.TeamSurvival.Enemy;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

/**
 * Holds information about a possible mob spawn, and spawns it when willed.<br />
 * The information about equipment and health is stored here so that random-per-mob
 * spawns are doable
 * @author Herobrine
 * @author Franz Chavez
 * @author James Pelster
 *
 */
public class Mob {
	
	/**
	 * Keep track of what type of entity to spawn
	 */
	private EntityType type;
	
	/**
	 * Some sort of indication of how hard the mob is.<br />
	 * For example, high-difficulty zombies might spawn with lots of enchanted equips, while
	 * low-difficulty zombies would spawn with no equipment at all
	 */
	private int difficulty;
	
	/**
	 * Creates a mob archetype from the passed difficulty and type.<br />
	 * @param mobUsed What kind of mob is this
	 * @param difficulty How difficult are they. This determines, when entities are spawned, what equips & hp they have
	 */
	public Mob(String mobUsed, int difficulty) {
		this.type = EntityType.valueOf(mobUsed);
		this.difficulty = difficulty;
	}
	
	public void SpawnEntity(Location loc) {
		Entity spawnedEntity = loc.getWorld().spawnEntity(loc, type);
		//spawnedEntity
	}
}
