package me.crolemol.coc.arena.panels;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.crolemol.coc.Coc;
<<<<<<< HEAD
import me.crolemol.coc.arena.building.Goldmine;
import me.crolemol.coc.arena.building.Townhall;
import me.crolemol.coc.arena.building.UpgradeBuilding;
import me.crolemol.coc.arena.building.interfaces.Building;
import me.crolemol.coc.arena.events.BuildingInteractEvent;
import me.crolemol.coc.arena.panels.Specs.specsGoldMine;
import me.crolemol.coc.arena.panels.Specs.specsTownhall;
import me.crolemol.coc.economy.Resources;
=======
import me.crolemol.coc.arena.UpgradeBuilding;
import me.crolemol.coc.arena.panels.Specs.specsGoldMine;
import me.crolemol.coc.arena.panels.Specs.specsTownhall;
import me.crolemol.coc.economy.Resources;
import me.crolemol.coc.events.BuildingInteractEvent;
>>>>>>> origin/master
import me.crolemol.coc.utils.TimetoGemCalc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static com.google.common.base.Preconditions.checkNotNull;

public class BuildingPanels implements Listener{
	private Map<String,Integer> currentBuildingID = new HashMap<>();
<<<<<<< HEAD
	public void openBuildingPanel(Building building){
		if(building.getBuildingName().equals("elixir_collector")){
			return;
		}
		if(building instanceof Townhall){
			openTownhallPanel((Townhall) building);
			return;
		}
		if(building instanceof Goldmine){
			openGoldMinePanel((Goldmine) building);
=======
	public void openBuildingPanel(String BuildingName, int buildingID,Player owner){
		if(BuildingName.equals("elixir_collector") || BuildingName.equals("gold_mine")){
			return;
		}
		if(BuildingName.equals("townhall")){
			openTownhallPanel(owner);
			return;
		}
		if(BuildingName.equals("goldmine")){
			openGoldMinePanel(buildingID, owner);
>>>>>>> origin/master
			return;
		}
		
	}
<<<<<<< HEAD
	private void openGoldMinePanel(Goldmine building){
		specsGoldMine[] spec = Specs.specsGoldMine.values();
		Goldmine goldmine = Goldmine.getGoldmine(building.getBuildingID(), building.getOwner());
		Coc.getPlugin();
		FileConfiguration dataconf = Coc.getdataconffile(building.getOwner());
=======
	private void openGoldMinePanel(int BuildingNumber, Player Buildingowner){
		specsGoldMine[] spec = Specs.specsGoldMine.values();
		Coc.getPlugin();
		FileConfiguration dataconf = Coc.getdataconffile(Buildingowner);
>>>>>>> origin/master
		Inventory inv = Bukkit.createInventory(null, 9, "Goldmine");
		
		ItemStack Info = new ItemStack(Material.BOOK_AND_QUILL);
		ItemMeta InfoMeta = Info.getItemMeta();
		InfoMeta.setDisplayName(ChatColor.LIGHT_PURPLE+"Info");
		List<String> list = new ArrayList<String>();
		list.add("a recoursebuilding who lets you collect gold,");
		list.add("upgrade your goldmine to gain more gold per hour");
<<<<<<< HEAD
		list.add("Health: "+ spec[dataconf.getInt("goldmine."+building.getBuildingID()+".level")-1].getHealth());
		list.add("Production per hour: "+ spec[dataconf.getInt("goldmine."+building.getBuildingID()+".level")-1].getProduction());
		list.add("Capacity: "+ spec[dataconf.getInt("goldmine."+building.getBuildingID()+".level")-1].getCapacity());
		InfoMeta.setLore(list);
		Info.setItemMeta(InfoMeta);
		
		ItemStack Collect = new ItemStack(Material.GOLD_NUGGET);
		ItemMeta CollectMeta = Collect.getItemMeta();
		CollectMeta.setDisplayName(ChatColor.LIGHT_PURPLE+"Collect");
		List<String> list4 = new ArrayList<String>();
		list4.add("Collectable gold: " + goldmine.getCollectable());
		CollectMeta.setLore(list4);
		Collect.setItemMeta(CollectMeta);
		
=======
		list.add("Health: "+ spec[dataconf.getInt("goldmine."+BuildingNumber+".level")-1].getHealth());
		list.add("Production per hour: "+ spec[dataconf.getInt("goldmine."+BuildingNumber+".level")-1].getProduction());
		list.add("Capacity: "+ spec[dataconf.getInt("goldmine."+BuildingNumber+".level")-1].getCapacity());
		InfoMeta.setLore(list);
		Info.setItemMeta(InfoMeta);
		
>>>>>>> origin/master
		ItemStack Upgrade = new ItemStack(Material.IRON_PICKAXE);
		ItemMeta UpgradeMeta = Upgrade.getItemMeta();
		UpgradeMeta.setDisplayName(ChatColor.LIGHT_PURPLE+"Upgrade");
		List<String> list2 = new ArrayList<String>();
<<<<<<< HEAD
		if(!dataconf.contains("goldmine."+building.getBuildingID()+".upgrade")){
		list2.add("Upgrade your goldmine to");
		list2.add("increase the production and capacity,");
		list2.add("Costs:");
		list2.add("Elixir: "+ spec[dataconf.getInt("goldmine."+building.getBuildingID()+".level")].getElixirCost());
		list2.add("Time: " + LongtoSimpleString(spec[dataconf.getInt("goldmine."+building.getBuildingID()+".level")].getUpgradeTime()));
		}else{
			Calendar cal = Calendar.getInstance();
			Long caltime = cal.getTimeInMillis()/60/1000;
			Long cal2 = dataconf.getLong("goldmine."+building.getBuildingID()+".upgrade");
			Long time1 = timeBetweenDates(cal2, caltime);
			int time2 = spec[dataconf.getInt("goldmine."+building.getBuildingID()+".level")].getUpgradeTime();
=======
		if(!dataconf.contains("goldmine."+BuildingNumber+".upgrade")){
		list2.add("Upgrade your goldmine to");
		list2.add("increase the production and capacity,");
		list2.add("Costs:");
		list2.add("Elixir: "+ spec[dataconf.getInt("goldmine."+BuildingNumber+".level")].getElixirCost());
		}else{
			Calendar cal = Calendar.getInstance();
			Long caltime = cal.getTimeInMillis()/60/1000;
			Long cal2 = dataconf.getLong("goldmine."+BuildingNumber+".upgrade");
			Long time1 = timeBetweenDates(cal2, caltime);
			int time2 = spec[dataconf.getInt("goldmine."+BuildingNumber+".level")].getUpgradeTime();
>>>>>>> origin/master
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
<<<<<<< HEAD
		Long cal2 = dataconf.getLong("goldmine."+building.getBuildingID()+".upgrade");
		Long time1 = timeBetweenDates(cal2, caltime);
		int time2 = spec[dataconf.getInt("goldmine."+building.getBuildingID()+".level")].getUpgradeTime();
=======
		Long cal2 = dataconf.getLong("goldmine."+BuildingNumber+".upgrade");
		Long time1 = timeBetweenDates(cal2, caltime);
		int time2 = spec[dataconf.getInt("goldmine."+BuildingNumber+".level")].getUpgradeTime();
>>>>>>> origin/master
		Long time3 = time2 - time1;
		List<String> list3 = new ArrayList<String>();
		list3.add("Speed up the building progress,");
		list3.add("Cost:");
		TimetoGemCalc calc = new TimetoGemCalc();
		list3.add(calc.Calc(time3*60)+" Gems");
		SpeedMeta.setLore(list3);
		Speed.setItemMeta(SpeedMeta);
		
		inv.setItem(0, Info);
<<<<<<< HEAD
		inv.setItem(1, Collect);
		if(dataconf.getInt("goldmine."+building.getBuildingID()+".level")!=11){
		inv.setItem(8, Upgrade);
		}
		if(dataconf.contains("goldmine."+building.getBuildingID()+".upgrade")){
			inv.setItem(7, Speed);
		}
		building.getOwner().openInventory(inv);
		
		
	}
	private void openTownhallPanel(Townhall building){
		specsTownhall[] spec = Specs.specsTownhall.values();
		Coc.getPlugin();
		FileConfiguration dataconf = Coc.getdataconffile(building.getOwner());
=======
		inv.setItem(8, Upgrade);
		if(dataconf.contains("goldmine."+BuildingNumber+".upgrade")){
			inv.setItem(7, Speed);
		}
		Buildingowner.openInventory(inv);
		
		
	}
	private void openTownhallPanel(Player owner){
		specsTownhall[] spec = Specs.specsTownhall.values();
		Coc.getPlugin();
		FileConfiguration dataconf = Coc.getdataconffile(owner);
>>>>>>> origin/master
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
<<<<<<< HEAD
		list2.add("Time: " + LongtoSimpleString(spec[dataconf.getInt("townhall.1.level")].getUpgradeTime()));
=======
>>>>>>> origin/master
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
		TimetoGemCalc calc = new TimetoGemCalc();
		list3.add(calc.Calc(time3*60)+" Gems");
		SpeedMeta.setLore(list3);
		Speed.setItemMeta(SpeedMeta);
		
		inv.setItem(0, Info);
		inv.setItem(8, Upgrade);
		if(dataconf.contains("townhall.1.upgrade")){
			inv.setItem(7, Speed);
		}
<<<<<<< HEAD
		building.getOwner().openInventory(inv);
=======
		owner.openInventory(inv);
>>>>>>> origin/master
		
	}
	
	@EventHandler
	public void OnInventoryClick(InventoryClickEvent event){
		if(ChatColor.stripColor(event.getInventory().getName()).equalsIgnoreCase("Townhall")){
			TownhallPanelClick(event);
	}
		if(ChatColor.stripColor(event.getInventory().getName()).equalsIgnoreCase("Goldmine")){
			GoldminePanelClick(event);
	}
		}
	private void GoldminePanelClick(InventoryClickEvent event){
		Player player = (Player) event.getWhoClicked();
		FileConfiguration dataconf = Coc.getdataconffile(player);
		event.setCancelled(true);
		if(event.getCurrentItem()==null || !event.getCurrentItem().hasItemMeta()){
			return;
		}
		switch(event.getCurrentItem().getType()){
		case BOOK_AND_QUILL:
			break;
<<<<<<< HEAD
		case GOLD_NUGGET:
			Goldmine goldmine = Goldmine.getGoldmine(currentBuildingID.get(event.getWhoClicked().getName()), (Player)event.getWhoClicked());
			goldmine.Collect();
			event.getWhoClicked().closeInventory();
			break;
=======
>>>>>>> origin/master
		case IRON_PICKAXE:
			specsGoldMine[] spec = Specs.specsGoldMine.values();
			if(!dataconf.contains("goldmine."+currentBuildingID.get(event.getWhoClicked().getName())+".upgrade") && dataconf.getInt("Elixir") >= spec[dataconf.getInt("goldmine."+currentBuildingID.get(event.getWhoClicked().getName())+".level")].getElixirCost()){
			UpgradeBuilding upgrade = new UpgradeBuilding();
			upgrade.startNewUpgrade("goldmine", currentBuildingID.get(event.getWhoClicked().getName()), (Player)event.getWhoClicked());
<<<<<<< HEAD

			Resources.takeElixir((Player)event.getWhoClicked(), spec[dataconf.getInt("goldmine."+currentBuildingID.get(event.getWhoClicked().getName())+".level")].getElixirCost());
=======
			Resources rc = new Resources();

			rc.takeElixir((Player)event.getWhoClicked(), spec[dataconf.getInt("goldmine."+currentBuildingID.get(event.getWhoClicked().getName())+".level")].getElixirCost());
>>>>>>> origin/master
			event.getWhoClicked().closeInventory();}
			break;
		case EMERALD:
			UpgradeBuilding ub = new UpgradeBuilding();
			ub.FinishUpgrade("goldmine", currentBuildingID.get(event.getWhoClicked().getName()), (Player)event.getWhoClicked());
			Calendar cal = Calendar.getInstance();
			specsGoldMine[] spec2 = Specs.specsGoldMine.values();
			Long caltime = cal.getTimeInMillis()/60/1000;
			Long cal2 = dataconf.getLong("goldmine."+currentBuildingID.get(event.getWhoClicked().getName())+".upgrade");
			Long time1 = timeBetweenDates(cal2, caltime);
			int time2 = spec2[dataconf.getInt("goldmine."+currentBuildingID.get(event.getWhoClicked().getName())+".level")].getUpgradeTime();
			Long time3 = time2 - time1;
<<<<<<< HEAD
			new Resources();
			TimetoGemCalc calc = new TimetoGemCalc();
			Resources.takeGems((Player)event.getWhoClicked(), calc.Calc(time3*60));
=======
			Resources re = new Resources();
			TimetoGemCalc calc = new TimetoGemCalc();
			re.takeGems((Player)event.getWhoClicked(), calc.Calc(time3*60));
>>>>>>> origin/master
			event.getWhoClicked().closeInventory();
			break;
		default: player.closeInventory();
			
		}
		
	}
	private void TownhallPanelClick(InventoryClickEvent event){
		Player player = (Player) event.getWhoClicked();
		FileConfiguration dataconf = Coc.getdataconffile(player);
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
			UpgradeBuilding upgrade = new UpgradeBuilding();
			upgrade.startNewUpgrade("townhall", 1, (Player)event.getWhoClicked());
<<<<<<< HEAD
			new Resources();

			Resources.takeGold((Player)event.getWhoClicked(), spec[dataconf.getInt("townhall.1.level")].getGoldPrice());
=======
			Resources rc = new Resources();

			rc.takeGold((Player)event.getWhoClicked(), spec[dataconf.getInt("townhall.1.level")].getGoldPrice());
>>>>>>> origin/master
			event.getWhoClicked().closeInventory();}
			break;
		case EMERALD:
			UpgradeBuilding ub = new UpgradeBuilding();
			ub.FinishUpgrade("townhall", 1, (Player)event.getWhoClicked());
			Calendar cal = Calendar.getInstance();
			specsTownhall[] spec2 = Specs.specsTownhall.values();
			Long caltime = cal.getTimeInMillis()/60/1000;
			Long cal2 = dataconf.getLong("townhall.1.upgrade");
			Long time1 = timeBetweenDates(cal2, caltime);
			int time2 = spec2[dataconf.getInt("townhall.1.level")].getUpgradeTime();
			Long time3 = time2 - time1;
<<<<<<< HEAD
			new Resources();
			TimetoGemCalc calc = new TimetoGemCalc();
			Resources.takeGems((Player)event.getWhoClicked(), calc.Calc(time3*60));
=======
			Resources re = new Resources();
			TimetoGemCalc calc = new TimetoGemCalc();
			re.takeGems((Player)event.getWhoClicked(), calc.Calc(time3*60));
>>>>>>> origin/master
			event.getWhoClicked().closeInventory();
			break;
		default: player.closeInventory();
			
		}
		
	}
	@EventHandler
	private void onBuildingInteract(BuildingInteractEvent event){
<<<<<<< HEAD
		if(event.getBuilding().getBuildingID() == 0){return;}
		if(checkNotNull(event.getBuilding().getOwner()).equals(false)){return;}
		openBuildingPanel(event.getBuilding());
		currentBuildingID.put(event.getBuilding().getOwner().getName(), event.getBuilding().getBuildingID());
=======
		if(checkNotNull(event.getBuildingID()).equals(false)){return;}
		if(checkNotNull(event.getBuildingName()).equals(false)){return;}
		if(checkNotNull(event.getWhoClicked()).equals(false)){return;}
		openBuildingPanel(event.getBuildingName(), event.getBuildingID(), event.getWhoClicked());
		currentBuildingID.put(event.getWhoClicked().getName(), event.getBuildingID());
>>>>>>> origin/master
	} 
	@EventHandler
	private void onInventoryCloseEvent(InventoryCloseEvent event){
		if(currentBuildingID.containsKey(event.getPlayer())){
			currentBuildingID.remove(event.getPlayer().getName());
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
