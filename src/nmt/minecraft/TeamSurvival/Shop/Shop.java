package nmt.minecraft.TeamSurvival.Shop;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import nmt.minecraft.TeamSurvival.TeamSurvivalManager;
import nmt.minecraft.TeamSurvival.TeamSurvivalPlugin;

/**
 * A shop that offers items to players for a price.<br />
 * Has a location that it listens for to interact with, and then presents an inventory menu.
 * @author Skyler
 *
 */
public class Shop implements Listener {
	
	/**
	 * Map between integer position and the item
	 */
	private Map<Integer, ShopItem> items;
	
	private Location buttonLocation;
	
	/**
	 * Sets up a shop, with a list of items and a button location
	 * @param buttonLocation The location of the button to listen for
	 * @param items The list of items. If this is null, will use {@link ShopDefaults} to generate defaults.
	 */
	public Shop(Location buttonLocation, Map<Integer, ShopItem> items) {
		this.buttonLocation = buttonLocation.getBlock().getLocation();
		
		if (items == null) {
			this.items = new HashMap<Integer, ShopItem>();
			int index = 0;
			for (ShopDefaults def : ShopDefaults.values()) {
				this.items.put(index, def.getShopItem());
				index ++;
			}
		} else {
			this.items = items;
		}
		
		Bukkit.getPluginManager().registerEvents(this, TeamSurvivalPlugin.plugin);
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if (e.isCancelled() || e.getClickedBlock() == null || e.getClickedBlock().getType() == Material.AIR) {
			return;
		}
		
		if (e.getClickedBlock().getLocation().equals(buttonLocation))
		if (TeamSurvivalManager.getPlayer(e.getPlayer()) != null) { //check that they're on a team
			e.setCancelled(true);
			
			//open inventory
			Inventory inv = Bukkit.createInventory(e.getPlayer(), 45, "TSSHOP-" + e.getPlayer().getName());
			for (Entry<Integer, ShopItem> entry : items.entrySet()) {
				inv.setItem(entry.getKey(), entry.getValue().getFancyItem());
			}
			
			e.getPlayer().openInventory(inv);
		}
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if (e.isCancelled()) {
			return;
		}
		
		if (!e.getInventory().getName().equals("TSSHOP-" + e.getWhoClicked().getName())) {
			//wrong inventory; not a inventory that we spawned
			return;
		}
		
		if (!(e.getWhoClicked() instanceof Player)) {
			return;
		}

		e.setCancelled(true);
		
		//right inventory; check the pos
		int rawSlot = e.getRawSlot();
		if (rawSlot >= 45) {
			return;
		}
		
		if (!items.containsKey(rawSlot)) {
			return;
		}
		
		ShopItem item = items.get(rawSlot);
		
		purchaseItem((Player) e.getWhoClicked(), item);
		
	}
	
	/**
	 * Attempts to purchase an item for the given player
	 * @param player
	 * @param item
	 */
	private static void purchaseItem(Player player, ShopItem item) {
		if (player.getLevel() < item.getCost()) {
			return;
		}
		
		if (player.getInventory().firstEmpty() == -1) {
			return; //no room
		}
		
		player.setLevel(player.getLevel() - item.getCost());
		player.getInventory().addItem(item.getItem());
	}
}
