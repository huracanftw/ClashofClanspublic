package me.crolemol.coc.arena.building.interfaces;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public interface BuildingPanel {
	public void Open(Player player);
	public Inventory getInventory();
	public void setPanelInventory(Inventory inv);
	public Inventory getDefaultInventory();
}
