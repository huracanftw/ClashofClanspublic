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
import me.crolemol.coc.arena.building.GoldStorage;
import me.crolemol.coc.arena.building.interfaces.BuildingPanel;
import me.crolemol.coc.arena.building.interfaces.StorageBuildingSpecs;
import me.crolemol.coc.utils.PanelUtils;
import me.crolemol.coc.utils.TimetoGemCalc;

public class GoldStoragePanel implements BuildingPanel{
	Coc plugin = Coc.getPlugin();
	GoldStorage building;
	Inventory inv;
	boolean isdefault = true;
	public static Map<UUID,GoldStorage> staticbuilding = new HashMap<>();
	
	public GoldStoragePanel(GoldStorage building){
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
		StorageBuildingSpecs[] spec = building.getBuildingSpecs();
		FileConfiguration dataconf = plugin.getdataconffile(building.getOwner());
		Inventory inv2 = Bukkit.createInventory(null, 9, "Goldstorage");
		
		ItemStack Info = new ItemStack(Material.BOOK_AND_QUILL);
		ItemMeta InfoMeta = Info.getItemMeta();
		InfoMeta.setDisplayName(ChatColor.LIGHT_PURPLE+"Info");
		List<String> list = new ArrayList<String>();
		list.add("All your precious gold is stored here,");
		list.add("Upgrade the storage to increase its capacity");
		list.add("and durability against attack.");
		if(building.getLevel() != 0){
		list.add("Level: "+building.getLevel());
		list.add("Health: "+ spec[dataconf.getInt("goldstorage."+building.getBuildingID()+".level")-1].getHealth());
		list.add("Capacity: "+ spec[dataconf.getInt("goldstorage."+building.getBuildingID()+".level")-1].getCapacity());}
		InfoMeta.setLore(list);
		Info.setItemMeta(InfoMeta);
		
		ItemStack Upgrade = new ItemStack(Material.IRON_PICKAXE);
		ItemMeta UpgradeMeta = Upgrade.getItemMeta();
		UpgradeMeta.setDisplayName(ChatColor.LIGHT_PURPLE+"Upgrade");
		List<String> list2 = new ArrayList<String>();
		if(!dataconf.contains("goldstorage."+building.getBuildingID()+".upgrade")){
		list2.add("Upgrade your goldstorage to");
		list2.add("increase its capacity durability against attack.");
		
		list2.add("Costs:");
		list2.add("Elixir: "+ spec[dataconf.getInt("goldstorage."+building.getBuildingID()+".level")].getUpgradePrice().getAmount());
		list2.add("Time: " + PanelUtils.LongtoSimpleString(spec[dataconf.getInt("goldstorage."+building.getBuildingID()+".level")].getUpgradeTime()));
		}else{
			Calendar cal = Calendar.getInstance();
			Long caltime = cal.getTimeInMillis()/60/1000;
			Long cal2 = dataconf.getLong("goldstorage."+building.getBuildingID()+".upgrade");
			Long time1 = PanelUtils.timeBetweenDates(cal2, caltime);
			int time2 = spec[dataconf.getInt("goldstorage."+building.getBuildingID()+".level")].getUpgradeTime();
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
		Long cal2 = dataconf.getLong("goldstorage."+building.getBuildingID()+".upgrade");
		Long time1 = PanelUtils.timeBetweenDates(cal2, caltime);
		int time2 = spec[dataconf.getInt("goldstorage."+building.getBuildingID()+".level")].getUpgradeTime();
		Long time3 = time2 - time1;
		List<String> list3 = new ArrayList<String>();
		list3.add("Speed up the building progress,");
		list3.add("Cost:");
		TimetoGemCalc calc = new TimetoGemCalc();
		list3.add(calc.Calc(time3*60)+" Gems");
		SpeedMeta.setLore(list3);
		Speed.setItemMeta(SpeedMeta);
		
		inv2.setItem(0, Info);
		if(building.getLevel()<=Buildingspecs.goldstorage.getMaxLevel()){
		inv2.setItem(8, Upgrade);
		}
		if(dataconf.contains("goldstorage."+building.getBuildingID()+".upgrade")){
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
