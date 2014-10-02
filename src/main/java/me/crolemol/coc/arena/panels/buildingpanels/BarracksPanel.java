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
import me.crolemol.coc.arena.BuildingType;
import me.crolemol.coc.arena.building.Barracks;
import me.crolemol.coc.arena.building.Barracks.BarracksSpecs;
import me.crolemol.coc.arena.building.interfaces.BuildingPanel;
import me.crolemol.coc.utils.PanelUtils;
import me.crolemol.coc.utils.TimetoGemCalc;

public class BarracksPanel implements BuildingPanel{
	Barracks building;
	public BarracksPanel(Barracks building){
		this.building = building;
	}
	@Override
	public Inventory getInventory() {
		BarracksSpecs[] spec = building.getBuildingSpecs();
		Coc.getPlugin();
		Inventory inv2 = Bukkit.createInventory(null, 9, "Barracks");
		ItemStack Info = new ItemStack(Material.BOOK_AND_QUILL);
		ItemMeta InfoMeta = Info.getItemMeta();
		InfoMeta.setDisplayName(ChatColor.LIGHT_PURPLE+"Info");
		List<String> list = new ArrayList<String>();
		list.add("Your troops are stationed in Army Camps.");
		list.add("Build more camps and upgrade");
		list.add("them to muster a powerful army.");
		if(building.getLevel() != 0){
			list.add("Level: "+building.getLevel());
			list.add("Health: "+ spec[building.getLevel()].getHealth());
			}
		InfoMeta.setLore(list);
		Info.setItemMeta(InfoMeta); 
		
		ItemStack Upgrade = null;
		if(building.getLevel() <= BuildingType.Barracks.getMaxLevel()){
			Upgrade = new ItemStack(Material.IRON_PICKAXE);
			ItemMeta UpgradeMeta = Upgrade.getItemMeta();
			UpgradeMeta.setDisplayName(ChatColor.LIGHT_PURPLE+"Upgrade");
			List<String> list2 = new ArrayList<String>();
			if(building.isUpgrading() == false){
			list2.add("Upgrade your army camp");
			list2.add("to increase their troop capacity");
			list2.add("Costs:");
			list2.add("Elixir: "+ spec[building.getLevel()].getUpgradePrice().getAmount());
			list2.add("Time: " + PanelUtils.LongtoSimpleString(spec[building.getLevel()].getUpgradeTime()));
			}else{
				list2.add("Time remain:");
				list2.add(PanelUtils.LongtoSimpleString(building.getUpgradeTimeRemain()));
			}
		UpgradeMeta.setLore(list2);
		Upgrade.setItemMeta(UpgradeMeta); 
		}
		ItemStack Train = new ItemStack(Material.STONE_SWORD);
		ItemMeta TrainMeta = Train.getItemMeta();
		TrainMeta.setDisplayName(ChatColor.LIGHT_PURPLE+"Train troops");
		Train.setItemMeta(TrainMeta);
		
		ItemStack Speed = new ItemStack(Material.EMERALD);
		ItemMeta SpeedMeta = Speed.getItemMeta();
		SpeedMeta.setDisplayName(ChatColor.LIGHT_PURPLE+"Speed up");
		List<String> list3 = new ArrayList<String>();
		list3.add("Speed up the building progress,");
		list3.add("Cost:");
		TimetoGemCalc calc = new TimetoGemCalc();
		list3.add(calc.Calc(building.getUpgradeTimeRemain()*60)+" Gems");
		SpeedMeta.setLore(list3);
		Speed.setItemMeta(SpeedMeta);
		
		inv2.setItem(0, Info);
		if(building.getLevel() <= BuildingType.Barracks.getMaxLevel()){
		inv2.setItem(8, Upgrade);
		}
		if(building.isUpgrading() == true){
			inv2.setItem(7, Speed);
		}
		inv2.setItem(1, Train);
		return inv2;
	}

}
