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
import me.crolemol.coc.arena.building.Goldmine;
import me.crolemol.coc.arena.building.interfaces.BuildingPanel;
import me.crolemol.coc.arena.building.interfaces.ResourceBuildingSpecs;
import me.crolemol.coc.utils.PanelUtils;
import me.crolemol.coc.utils.TimetoGemCalc;

public class GoldminePanel implements BuildingPanel{
	Coc plugin = Coc.getPlugin();
	Goldmine building;

	
	public GoldminePanel(Goldmine building){
		this.building = building;

	}


	@Override
	public Inventory getInventory(){
		ResourceBuildingSpecs[] spec = building.getBuildingSpecs();
		Goldmine goldmine = Goldmine.getGoldmine(building.getBuildingID(), building.getOwner());
		Inventory inv2 = Bukkit.createInventory(null, 9, "Goldmine");
		
		ItemStack Info = new ItemStack(Material.BOOK_AND_QUILL);
		ItemMeta InfoMeta = Info.getItemMeta();
		InfoMeta.setDisplayName(ChatColor.LIGHT_PURPLE+"Info");
		List<String> list = new ArrayList<String>();
		list.add("a recoursebuilding who lets you collect gold,");
		list.add("upgrade your goldmine to gain more gold per hour");
		if(building.getLevel() != 0){
		list.add("Level: "+building.getLevel());
		list.add("Health: "+ spec[building.getLevel()-1].getHealth());
		list.add("Production per hour: "+ spec[building.getLevel()-1].getProduction());
		list.add("Capacity: "+ spec[building.getLevel()-1].getCapacity());
		}
		InfoMeta.setLore(list);
		Info.setItemMeta(InfoMeta);
		
		ItemStack Collect = new ItemStack(Material.GOLD_NUGGET);
		ItemMeta CollectMeta = Collect.getItemMeta();
		CollectMeta.setDisplayName(ChatColor.LIGHT_PURPLE+"Collect");
		List<String> list4 = new ArrayList<String>();
		list4.add("Collectable gold: " + goldmine.getCollectable());
		CollectMeta.setLore(list4);
		Collect.setItemMeta(CollectMeta);
		
		ItemStack Upgrade = new ItemStack(Material.IRON_PICKAXE);
		ItemMeta UpgradeMeta = Upgrade.getItemMeta();
		UpgradeMeta.setDisplayName(ChatColor.LIGHT_PURPLE+"Upgrade");
		List<String> list2 = new ArrayList<String>();
		if(building.isUpgrading() == false){
		list2.add("Upgrade your goldmine to");
		list2.add("increase the production and capacity,");
		list2.add("Costs:");
		list2.add("Elixir: "+ spec[building.getLevel()].getUpgradePrice().getAmount());
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
		
		inv2.setItem(0, Info);
		if(building.getLevel() != 0){
		inv2.setItem(1, Collect);}
		if(building.getLevel()!=BuildingType.GoldMine.getMaxLevel()){
		inv2.setItem(8, Upgrade);
		}
		if(building.isUpgrading()){
			inv2.setItem(7, Speed);
		}
		return inv2;
	}
	

}
