package me.crolemol.coc.arena.panels.buildingpanels;

import java.util.ArrayList;
import java.util.List;

import me.crolemol.coc.Coc;
import me.crolemol.coc.arena.building.ArcherQueenAltar;
import me.crolemol.coc.arena.building.interfaces.BuildingPanel;
import me.crolemol.coc.arena.building.interfaces.BuildingSpecs;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ArcherQueenAltarPanel implements BuildingPanel{
	Coc plugin = Coc.getPlugin();
	ArcherQueenAltar building;
	
	public ArcherQueenAltarPanel(ArcherQueenAltar building){
		this.building = building;

	}
	@Override
	public Inventory getInventory(){
		BuildingSpecs[] spec = building.getBuildingSpecs();
		Inventory inv2 = Bukkit.createInventory(null, 9, "Archer Queen");
		
		ItemStack Info = new ItemStack(Material.BOOK_AND_QUILL);
		ItemMeta InfoMeta = Info.getItemMeta();
		InfoMeta.setDisplayName(ChatColor.LIGHT_PURPLE+"Info");
		List<String> list = new ArrayList<String>();
		list.add("his graceful huntress is a master destructive force,");
		list.add("though modest in health. She snipes targets in her territory ");
		list.add("when defending, and can summon stealth and terrifying damage");
		list.add("when attacking once her Royal Cloak ability is unlocked!");
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
