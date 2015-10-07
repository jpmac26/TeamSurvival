package nmt.minecraft.TeamSurvival.Shop;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum ShopDefaults {
	
	/*
	sharpness 1 books  - 75
unbreaking 1 - 150
knockback 1 - 50
smite 1 - 50
fire aspect/fire 1 - 100
power 1 - 75
infinity bow enchantment - 200


	 */
	WOOD_SWORD(Material.WOOD_SWORD, 1, 20),
	STONE_SWORD(Material.STONE_SWORD, 1, 75),
	GOLD_SWORD(Material.GOLD_SWORD, 1, 75),
	IRON_SWORD(Material.IRON_SWORD, 1, 200),
	DIAMOND_SWORD(Material.DIAMOND_SWORD, 1, 350),
	BOW(Material.BOW, 1, 100),
	ARROWS(Material.ARROW, 10, 50),
	SHARPNESS(     )
	
	
	private ItemStack item;
	
	private int cost;
	
	private ShopDefaults(Material type, int amount, int cost) {
		item = new ItemStack(type, amount);
		this.cost = cost;
	}
	
	private ShopDefaults(ItemStack item, int cost) {
		this.item = item;
		this.cost = cost;
	}

	public ItemStack getItem() {
		return item;
	}

	public int getCost() {
		return cost;
	}
	
	private enum EnchantedBook {
			
		
		
		sharpness 1 books  - 75
		unbreaking 1 - 150
		knockback 1 - 50
		smite 1 - 50
		fire aspect/fire 1 - 100
		power 1 - 75
		infinity bow enchantment - 200
	}
	
}
