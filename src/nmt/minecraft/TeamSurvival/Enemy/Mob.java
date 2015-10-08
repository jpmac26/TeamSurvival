package nmt.minecraft.TeamSurvival.Enemy;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Skeleton.SkeletonType;
import org.bukkit.entity.Creeper;
import org.bukkit.inventory.ItemStack;

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
	private String type;
	
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
	public Mob(String mobUsed, int diffBase, int waveNum) {
		type = mobUsed.toUpperCase();
		difficulty = diffBase * waveNum;
	}
	
	public LivingEntity SpawnEntity(Location loc) {
		Random rn = new Random();
		LivingEntity spawnedEntity;
		if(type.equalsIgnoreCase("Jockey")) {
			LivingEntity jockeyVehicle = (LivingEntity)loc.getWorld().spawnEntity(loc, EntityType.SPIDER);
			spawnedEntity = (LivingEntity)loc.getWorld().spawnEntity(loc, EntityType.SKELETON);
			jockeyVehicle.setPassenger(GiveRandomStuff(spawnedEntity, 2));
			return GiveRandomStuff(jockeyVehicle, 2);
		} else if(type.equalsIgnoreCase("Wither_Skeleton")) {
			spawnedEntity = (LivingEntity)loc.getWorld().spawnEntity(loc, EntityType.SKELETON);
			((Skeleton)spawnedEntity).setSkeletonType(SkeletonType.WITHER);
			return GiveRandomStuff(spawnedEntity, 9);
		} else if(type.equalsIgnoreCase("Creeper") & rn.nextInt(50) == 49) {
			spawnedEntity = (LivingEntity)loc.getWorld().spawnEntity(loc, EntityType.CREEPER);
			((Creeper)spawnedEntity).setPowered(true);
			return GiveRandomStuff(spawnedEntity, 7);
		} else {
			spawnedEntity = (LivingEntity)loc.getWorld().spawnEntity(loc, EntityType.valueOf(type.toUpperCase()));
			spawnedEntity.setCanPickupItems(false);
			//set items for spawned entity and return it
			return GiveRandomStuff(spawnedEntity, difficulty);
		}
		
	}
	
	private LivingEntity GiveRandomStuff(LivingEntity ent, int diff) {
		Random rn = new Random();
		//int RandChance = diff * ((rn.nextInt(4) + 1) / 10);
		int RandChance = (rn.nextInt(diff + 1) * 100) / (diff + 1) + 10;
		if (RandChance >= 90) {
			ItemStack[] armor = new ItemStack[4];
			ItemStack weapon;
			armor[0] = new ItemStack(Material.DIAMOND_HELMET);
			armor[1] = new ItemStack(Material.DIAMOND_CHESTPLATE);
			armor[2] = new ItemStack(Material.DIAMOND_LEGGINGS);
			armor[3] = new ItemStack(Material.DIAMOND_BOOTS);
			ent.getEquipment().setArmorContents(armor);
			
			if(ent.getType() == EntityType.SKELETON) {
				if(rn.nextInt(2) == 1){
					weapon = new ItemStack(Material.DIAMOND_SWORD);
					ent.getEquipment().setItemInHand(weapon);
				} else if(rn.nextInt(4) == 3) {
					weapon = ent.getEquipment().getItemInHand();
					weapon.addEnchantment(Enchantment.ARROW_DAMAGE, 2);
					if(rn.nextInt(20) == 19) {
						weapon.addEnchantment(Enchantment.ARROW_KNOCKBACK, 10);
					}
					ent.getEquipment().setItemInHand(weapon);
				}
			} else {
				weapon = new ItemStack(Material.DIAMOND_SWORD);
				ent.getEquipment().setItemInHand(weapon);
			}
		} else if (RandChance >= 75) {
			ItemStack[] armor = new ItemStack[4];
			ItemStack weapon;
			armor[0] = new ItemStack(Material.IRON_HELMET);
			armor[1] = new ItemStack(Material.IRON_CHESTPLATE);
			armor[2] = new ItemStack(Material.IRON_LEGGINGS);
			armor[3] = new ItemStack(Material.IRON_BOOTS);
			ent.getEquipment().setArmorContents(armor);
			
			if(ent.getType() == EntityType.SKELETON) {
				if(rn.nextInt(2) == 1){
					weapon = new ItemStack(Material.IRON_SWORD);
					ent.getEquipment().setItemInHand(weapon);
				} else if(rn.nextInt(10) == 9) {
					weapon = ent.getEquipment().getItemInHand();
					weapon.addEnchantment(Enchantment.ARROW_FIRE, 1);
					ent.getEquipment().setItemInHand(weapon);
				}
			} else {
				weapon = new ItemStack(Material.IRON_SWORD);
				ent.getEquipment().setItemInHand(weapon);
			}
		} else if (RandChance >= 50) {
			ItemStack[] armor = new ItemStack[4];
			ItemStack weapon;
			armor[0] = new ItemStack(Material.LEATHER_HELMET);
			armor[1] = new ItemStack(Material.LEATHER_CHESTPLATE);
			armor[2] = new ItemStack(Material.LEATHER_LEGGINGS);
			armor[3] = new ItemStack(Material.LEATHER_BOOTS);
			ent.getEquipment().setArmorContents(armor);
			
			if(ent.getType() == EntityType.SKELETON) {
				if(rn.nextInt(2) == 1){
					weapon = new ItemStack(Material.STONE_SWORD);
					ent.getEquipment().setItemInHand(weapon);
				}
			} else {
				weapon = new ItemStack(Material.STONE_SWORD);
				ent.getEquipment().setItemInHand(weapon);
			}
		}
		return ent;
	}
}
