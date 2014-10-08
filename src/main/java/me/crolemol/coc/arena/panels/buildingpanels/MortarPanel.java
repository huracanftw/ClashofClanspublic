package me.crolemol.coc.arena.panels.buildingpanels;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.crolemol.coc.arena.building.Mortar;
import me.crolemol.coc.arena.building.interfaces.BuildingPanel;
import me.crolemol.coc.arena.building.interfaces.BuildingSpecs;
import me.crolemol.coc.utils.PanelUtils;
import me.crolemol.coc.utils.TimetoGemCalc;


public class MortarPanel implements BuildingPanel{
	Mortar building;
	public MortarPanel(Mortar building){
		this.building = building;
	}
	@Override
	public Inventory getInventory(){
		BuildingSpecs[] spec = building.getBuildingSpecs();
		Inventory inv2 = Bukkit.createInventory(null, 9, "Mortar");
		ItemStack Info = new ItemStack(Material.BOOK_AND_QUILL);
		ItemMeta InfoMeta = Info.getItemMeta();
		InfoMeta.setDisplayName(ChatColor.LIGHT_PURPLE+"Info");
		List<String> list = new ArrayList<String>();
		list.add("The Mortar can mow down hordes of enemies by");
		list.add("by the splash damage from its shell.");
		list.add("Don't let enemies get too close to it!");
		if(building.getLevel() != 0){
		list.add("Level: "+building.getLevel());
		list.add("Health: "+ spec[building.getLevel()-1].getHealth());
		}
		InfoMeta.setLore(list);
		Info.setItemMeta(InfoMeta);
		
		ItemStack Upgrade = new ItemStack(Material.IRON_PICKAXE);
		ItemMeta UpgradeMeta = Upgrade.getItemMeta();
		UpgradeMeta.setDisplayName(ChatColor.LIGHT_PURPLE+"Upgrade"); 
		List<String> list2 = new ArrayList<String>();
		if(building.isUpgrading() == false){
		list2.add("Upgrade your Mortar to");
		list2.add("increase its range!");
		
		list2.add("Costs:");
		list2.add("Gold: "+ spec[building.getLevel()].getUpgradePrice().getAmount());
		list2.add("Time: " + PanelUtils.LongtoSimpleString(spec[building.getLevel()].getUpgradeTime()));
		}else{
			list2.add("Time remain:");
			list2.add(PanelUtils.LongtoSimpleString(building.getUpgradeTimeRemain()));
			
		}
		UpgradeMeta.setLore(list2);
		Upgrade.setItemMeta(UpgradeMeta);
		
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
		
		UpgradeMeta.setLore(list2);
		Upgrade.setItemMeta(UpgradeMeta);
		inv2.setItem(0, Info);
		inv2.setItem(8, Upgrade);
		
		if(building.isUpgrading() == true){
			inv2.setItem(7, Speed);
		
		}
		return inv2;
	}

}
