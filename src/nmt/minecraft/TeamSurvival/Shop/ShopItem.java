package nmt.minecraft.TeamSurvival.Shop;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
	
	public ItemStack getFancyItem() {
		ItemStack fancy = item.clone();
		ItemMeta meta = fancy.getItemMeta();
		List<String> lore = new ArrayList<String>(2);
		lore.add(ChatColor.RED + "Cost: " + cost);
		meta.setLore(lore);
		fancy.setItemMeta(meta);
		
		return fancy;
	}
}
