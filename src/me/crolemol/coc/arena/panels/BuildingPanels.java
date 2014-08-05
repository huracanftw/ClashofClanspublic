package me.crolemol.coc.arena.panels;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import me.crolemol.coc.Coc;
import me.crolemol.coc.arena.UpgradeBuilding;
import me.crolemol.coc.arena.panels.Specs.specsGoldMine;
import me.crolemol.coc.arena.panels.Specs.specsTownhall;
import me.crolemol.coc.economy.Resources;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BuildingPanels implements Listener{
	public void openBuildingPanel(String BuildingName, String buildingID,Player owner) throws ParseException{
		if(BuildingName.equals("elixir_collector") || BuildingName.equals("gold_mine")){
			return;
		}
		if(BuildingName.equals("townhall")){
			openTownhallPanel(owner);
			return;
		}
		
	}
	private void openGoldMinePanel(String BuildingNumber, Player Buildingowner){
		specsGoldMine[] spec = Specs.specsGoldMine.values();
		FileConfiguration dataconf = Coc.getPlugin().getdataconffile(Buildingowner);
		Inventory inv = Bukkit.createInventory(null, 9, "Goldmine");
		ItemStack Info = new ItemStack(Material.BOOK_AND_QUILL);
		ItemMeta InfoMeta = Info.getItemMeta();
		InfoMeta.setDisplayName(ChatColor.LIGHT_PURPLE+"Info");
		List<String> list = new ArrayList<String>();
		list.add("a recoursebuilding who lets you collect gold,");
		list.add("upgrade your goldmine to gain more gold per hour");
		list.add("Health: "+ spec[dataconf.getInt("goldmine."+BuildingNumber+".level")-1].getHealth());
		list.add("Production per hour: "+ spec[dataconf.getInt("goldmine."+BuildingNumber+".level")-1].getProduction());
		list.add("Capacity: "+ spec[dataconf.getInt("goldmine."+BuildingNumber+".level")-1].getCapacity());
		InfoMeta.setLore(list);
		Info.setItemMeta(InfoMeta);
		
		
	}
	private void openTownhallPanel(Player owner) throws ParseException{
		specsTownhall[] spec = Specs.specsTownhall.values();
		FileConfiguration dataconf = Coc.getPlugin().getdataconffile(owner);
		Inventory inv = Bukkit.createInventory(null, 9, "Townhall");
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
		list2.add("Gold: "+ spec[dataconf.getInt("townhall.1.level")].getGoldPrice());
		}else{
			Calendar cal = Calendar.getInstance();
			Long caltime = cal.getTimeInMillis()/60/1000;
			Long cal2 = dataconf.getLong("townhall.1.upgrade");
			Long time1 = timeBetweenDates(cal2, caltime);
			int time2 = spec[dataconf.getInt("townhall.1.level")].getUpgradeTime();
			Long time3 = time2 - time1;
			list2.add("Time remain:");
			list2.add(LongtoSimpleString(time3));
		}
		UpgradeMeta.setLore(list2);
		Upgrade.setItemMeta(UpgradeMeta); 
		
		ItemStack Speed = new ItemStack(Material.EMERALD);
		ItemMeta SpeedMeta = Speed.getItemMeta();
		SpeedMeta.setDisplayName(ChatColor.LIGHT_PURPLE+"Speed up");
		Calendar cal = Calendar.getInstance();
		Long caltime = cal.getTimeInMillis()/60/1000;
		Long cal2 = dataconf.getLong("townhall.1.upgrade");
		Long time1 = timeBetweenDates(cal2, caltime);
		int time2 = spec[dataconf.getInt("townhall.1.level")].getUpgradeTime();
		Long time3 = time2 - time1;
		List<String> list3 = new ArrayList<String>();
		list3.add("Speed up the building progress,");
		list3.add("Cost:");
		list3.add(round(Math.ceil(time3),0)+" Gems");
		SpeedMeta.setLore(list3);
		Speed.setItemMeta(SpeedMeta);
		
		inv.setItem(0, Info);
		inv.setItem(8, Upgrade);
		if(dataconf.contains("townhall.1.upgrade")){
			inv.setItem(7, Speed);
		}
		owner.openInventory(inv);
		
	}
	
	@EventHandler
	public void OnInventoryClick(InventoryClickEvent event){
		if(ChatColor.stripColor(event.getInventory().getName()).equalsIgnoreCase("Townhall")){
			Date now = new Date(0);
			SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
			Player player = (Player) event.getWhoClicked();
			FileConfiguration dataconf = Coc.getPlugin().getdataconffile(player);
			event.setCancelled(true);
			if(event.getCurrentItem()==null || !event.getCurrentItem().hasItemMeta()){
				return;
			}
			switch(event.getCurrentItem().getType()){
			case BOOK_AND_QUILL:
				break;
			case IRON_PICKAXE:
				specsTownhall[] spec = Specs.specsTownhall.values();
				if(!dataconf.contains("townhall.1.upgrade") && dataconf.getInt("Gold") >= spec[dataconf.getInt("townhall.1.level")].getGoldPrice()){
				format.format(now);
				UpgradeBuilding upgrade = new UpgradeBuilding();
				upgrade.startNewUpgrade("townhall", "1", (Player)event.getWhoClicked());
				Resources rc = new Resources();

				rc.takeGold((Player)event.getWhoClicked(), spec[dataconf.getInt("townhall.1.level")].getGoldPrice());
				event.getWhoClicked().closeInventory();}
				break;
			case EMERALD:
				UpgradeBuilding ub = new UpgradeBuilding();
				ub.FinishUpgrade("townhall", "1", (Player)event.getWhoClicked());
				Calendar cal = Calendar.getInstance();
				specsTownhall[] spec2 = Specs.specsTownhall.values();
				Long caltime = cal.getTimeInMillis()/60/1000;
				Long cal2 = dataconf.getLong("townhall.1.upgrade");
				Long time1 = timeBetweenDates(cal2, caltime);
				int time2 = spec2[dataconf.getInt("townhall.1.level")].getUpgradeTime();
				Long time3 = time2 - time1;
				Resources re = new Resources();
				re.takeGems((Player)event.getWhoClicked(), (int)round(Math.ceil(time3),0));
				event.getWhoClicked().closeInventory();
				break;
			default: player.closeInventory();
				
			}
			
		}
	}
	private Long timeBetweenDates(Long earliestDate,Long latestDate){
		Long difference = latestDate - earliestDate;
		return difference;
	}
	private String LongtoSimpleString(long time){
		String simpledate;
		if(time>1440){
			int day = (int) round(Math.ceil(time/1440),0);
			simpledate = day+" Days";
			return simpledate;
		}else if(time>60){
			int hour = (int) round(Math.ceil(time/60),0);
			simpledate = hour+" Hours";
			return simpledate;
		}else{
			int minute = (int) round(Math.ceil(time),0);
			simpledate = minute+" Minutes";
			return simpledate;
		}
	}
	private double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.UP);
	    return bd.doubleValue();
	}
}
