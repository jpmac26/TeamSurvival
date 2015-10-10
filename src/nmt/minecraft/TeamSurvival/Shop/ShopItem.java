package nmt.minecraft.TeamSurvival.Shop;

import org.bukkit.inventory.ItemStack;

public class ShopItem {
	
	private int cost;
	
	private ItemStack item;
	
	public ShopItem(ItemStack item, int cost) {
		this.item = item;
		this.cost = cost;
	}
	
	public ItemStack getItem() {
		return item;
	}
	
	public int getCost() {
		return cost;
	}
}
