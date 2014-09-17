package me.crolemol.coc.arena.panels.buildingpanels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.crolemol.coc.Coc;
import me.crolemol.coc.arena.building.BuildersHut;
import me.crolemol.coc.arena.building.interfaces.BuildingPanel;
import me.crolemol.coc.arena.building.interfaces.BuildingSpecs;

public class BuildersHutPanel implements BuildingPanel{

	Coc plugin = Coc.getPlugin();
	BuildersHut building;
	Inventory inv;
	boolean isdefault = true;
	public static Map<UUID,BuildersHut> staticbuilding = new HashMap<>();
	
	public BuildersHutPanel(BuildersHut building){
		this.building = building;
		if(building == null){
			inv = null;
		}else{
			inv = this.getDefaultInventory();
		}

	}
	@SuppressWarnings("deprecation")
	@Override
	public void Open(Player player) {
		if(isdefault == true){
			inv = this.getDefaultInventory();
		}
	if(plugin.getServer().getPlayer(player.getName()) != null){
		player.openInventory(inv);
		staticbuilding.put(player.getUniqueId(), building);
	}
		
	}

	@Override
	public Inventory getInventory() {
		return inv;
	}
	@Override
	public Inventory getDefaultInventory(){
		BuildingSpecs[] spec = building.getBuildingSpecs();
		Inventory inv2 = Bukkit.createInventory(null, 9, "Builder's hut");
		
		ItemStack Info = new ItemStack(Material.BOOK_AND_QUILL);
		ItemMeta InfoMeta = Info.getItemMeta();
		InfoMeta.setDisplayName(ChatColor.LIGHT_PURPLE+"Info");
		List<String> list = new ArrayList<String>();
		list.add("Nothing gets done around here without Builders!");
		list.add("You can hire more Builders to start multiple construction projects,");
		list.add("or speed up their work by using green gems.");
		if(building.getLevel() != 0){
		list.add("Level: "+building.getLevel());
		list.add("Health: "+ spec[building.getLevel()-1].getHealth());
		}
		InfoMeta.setLore(list);
		Info.setItemMeta(InfoMeta);
		
		inv2.setItem(0, Info);
		return inv2;
	}
	@Override
	public void setPanelInventory(Inventory inv) {
		isdefault = false;
		this.inv = inv;
		
	}

}
