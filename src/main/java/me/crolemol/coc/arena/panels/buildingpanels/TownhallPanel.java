package me.crolemol.coc.arena.panels.buildingpanels;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.crolemol.coc.Coc;
import me.crolemol.coc.arena.Buildingspecs;
import me.crolemol.coc.arena.building.Townhall;
import me.crolemol.coc.arena.building.interfaces.BuildingPanel;
import me.crolemol.coc.arena.building.interfaces.BuildingSpecs;
import me.crolemol.coc.utils.PanelUtils;
import me.crolemol.coc.utils.TimetoGemCalc;

public class TownhallPanel implements BuildingPanel{
	Coc plugin = Coc.getPlugin();
	Townhall building;
	Inventory inv;
	boolean isdefault = true;
	public static Map<UUID,Townhall> staticbuilding = new HashMap<>();
	
	public TownhallPanel(Townhall building){
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
		Coc.getPlugin();
		FileConfiguration dataconf = plugin.getdataconffile(building.getOwner());
		Inventory inv2 = Bukkit.createInventory(null, 9, "Townhall");
		ItemStack Info = new ItemStack(Material.BOOK_AND_QUILL);
		ItemMeta InfoMeta = Info.getItemMeta();
		InfoMeta.setDisplayName(ChatColor.LIGHT_PURPLE+"Info");
		List<String> list = new ArrayList<String>();
		list.add("The heart of your base,");
		list.add("upgrade your townhall to unlock");
		list.add("more buildings and features");
		InfoMeta.setLore(list);
		Info.setItemMeta(InfoMeta); 
		
		ItemStack Upgrade = new ItemStack(Material.IRON_PICKAXE);
		ItemMeta UpgradeMeta = Upgrade.getItemMeta();
		UpgradeMeta.setDisplayName(ChatColor.LIGHT_PURPLE+"Upgrade");
		List<String> list2 = new ArrayList<String>();
		if(!dataconf.contains("townhall.1.upgrade")){
		list2.add("Upgrade your townhall");
		list2.add("Costs:");
		list2.add("Gold: "+ spec[dataconf.getInt("townhall.1.level")].getUpgradePrice().getAmount());
		list2.add("Time: " + PanelUtils.LongtoSimpleString(spec[dataconf.getInt("townhall.1.level")].getUpgradeTime()));
		}else{
			Calendar cal = Calendar.getInstance();
			Long caltime = cal.getTimeInMillis()/60/1000;
			Long cal2 = dataconf.getLong("townhall.1.upgrade");
			Long time1 = PanelUtils.timeBetweenDates(cal2, caltime);
			int time2 = spec[dataconf.getInt("townhall.1.level")].getUpgradeTime();
			Long time3 = time2 - time1;
			list2.add("Time remain:");
			list2.add(PanelUtils.LongtoSimpleString(time3));
		}
		UpgradeMeta.setLore(list2);
		Upgrade.setItemMeta(UpgradeMeta); 
		
		ItemStack Speed = new ItemStack(Material.EMERALD);
		ItemMeta SpeedMeta = Speed.getItemMeta();
		SpeedMeta.setDisplayName(ChatColor.LIGHT_PURPLE+"Speed up");
		Calendar cal = Calendar.getInstance();
		Long caltime = cal.getTimeInMillis()/60/1000;
		Long cal2 = dataconf.getLong("townhall.1.upgrade");
		Long time1 = PanelUtils.timeBetweenDates(cal2, caltime);
		int time2 = spec[dataconf.getInt("townhall.1.level")].getUpgradeTime();
		Long time3 = time2 - time1;
		List<String> list3 = new ArrayList<String>();
		list3.add("Speed up the building progress,");
		list3.add("Cost:");
		TimetoGemCalc calc = new TimetoGemCalc();
		list3.add(calc.Calc(time3*60)+" Gems");
		SpeedMeta.setLore(list3);
		Speed.setItemMeta(SpeedMeta);
		
		inv2.setItem(0, Info);
		if(building.getLevel() <= Buildingspecs.townhall.getMaxLevel()){
		inv2.setItem(8, Upgrade);
		}
		if(dataconf.contains("townhall.1.upgrade")){
			inv2.setItem(7, Speed);
		}
		return inv2;
	}
	@Override
	public void setPanelInventory(Inventory inv) {
		isdefault = false;
		this.inv = inv;
		
	}


}
