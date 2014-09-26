package me.crolemol.coc.arena.panels.buildingpanels;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.crolemol.coc.Coc;
import me.crolemol.coc.arena.building.BarbarianKingAltar;
import me.crolemol.coc.arena.building.interfaces.BuildingPanel;
import me.crolemol.coc.arena.building.interfaces.BuildingSpecs;

public class BarbarianKingAltarPanel implements BuildingPanel{
	Coc plugin = Coc.getPlugin();
	BarbarianKingAltar building;
	
	public BarbarianKingAltarPanel(BarbarianKingAltar building){
		this.building = building;

	}
	@Override
	public Inventory getInventory(){
		BuildingSpecs[] spec = building.getBuildingSpecs();
		Inventory inv2 = Bukkit.createInventory(null, 9, "Barbarian King");
		
		ItemStack Info = new ItemStack(Material.BOOK_AND_QUILL);
		ItemMeta InfoMeta = Info.getItemMeta();
		InfoMeta.setDisplayName(ChatColor.LIGHT_PURPLE+"Info");
		List<String> list = new ArrayList<String>();
		list.add("This colossal menace soaks up huge amounts of damage");
		list.add("and bashes everything in his path. He guards his");
		list.add("erritory fiercely when defending, and can launch into devastating");
		list.add("rage when attacking once his Iron Fist ability is unlocked!");
		if(building.getLevel() != 0){
		list.add("Level: "+building.getLevel());
		list.add("Health: "+ spec[building.getLevel()-1].getHealth());
		}
		InfoMeta.setLore(list);
		Info.setItemMeta(InfoMeta);
		
		inv2.setItem(0, Info);
		return inv2;
	}
}