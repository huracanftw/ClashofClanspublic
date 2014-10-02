package me.crolemol.coc.arena.panels.buildingpanels;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.crolemol.coc.arena.building.Barracks;
import me.crolemol.coc.arena.building.interfaces.BuildingPanel;

public class TrainTroopsPanel implements BuildingPanel{
	Barracks building;
	public TrainTroopsPanel(Barracks building){
		this.building = building;
	}
	@Override
	public Inventory getInventory() {
		Inventory inv = Bukkit.createInventory(null, 27, "Train Troops");
		
		ItemStack Barbarian = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
		ItemMeta BarbarianMeta = Barbarian.getItemMeta();
		BarbarianMeta.setDisplayName(ChatColor.LIGHT_PURPLE+"Barbarian");
		Barbarian.setItemMeta(BarbarianMeta);
		
		inv.setItem(9, Barbarian);
		return inv;
	}
	public Barracks getBarracks(){
		return this.building;
	}

}
