package me.crolemol.coc.arena.panels.buildingpanels;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.crolemol.coc.arena.building.HiddenTesla;
import me.crolemol.coc.arena.building.interfaces.BuildingPanel;
import me.crolemol.coc.arena.building.interfaces.BuildingSpecs;
import me.crolemol.coc.utils.PanelUtils;
import me.crolemol.coc.utils.TimetoGemCalc;


public class HiddenTeslaPanel implements BuildingPanel{
	HiddenTesla building;
	public HiddenTeslaPanel(HiddenTesla building){
		this.building = building;
	}
	@Override
	public Inventory getInventory(){
		BuildingSpecs[] spec = building.getBuildingSpecs();
		Inventory inv2 = Bukkit.createInventory(null, 9, "Hiddentesla");
		ItemStack Info = new ItemStack(Material.BOOK_AND_QUILL);
		ItemMeta InfoMeta = Info.getItemMeta();
		InfoMeta.setDisplayName(ChatColor.LIGHT_PURPLE+"Info");
		List<String> list = new ArrayList<String>();
		list.add("Lay a deadly trap with the Hidden Tesla!");
		list.add("Our Wizards have trapped a storm cloud into each of");
		list.add("these sneaky towers. When an enemy walks or flies");
		list.add("close enough, the tower springs up and fries");
		list.add("it using the power of Electrickery!");
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
		list2.add("Upgrade your Hiddentesla to");
		list2.add("increase its power of Electrickery!");
		
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
