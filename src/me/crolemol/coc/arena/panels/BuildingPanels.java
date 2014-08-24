package me.crolemol.coc.arena.panels;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.crolemol.coc.Coc;
import me.crolemol.coc.arena.Buildingspecs;
import me.crolemol.coc.arena.building.ElixirCollector;
import me.crolemol.coc.arena.building.GoldStorage;
import me.crolemol.coc.arena.building.Goldmine;
import me.crolemol.coc.arena.building.Townhall;
import me.crolemol.coc.arena.building.UpgradeBuilding;
import me.crolemol.coc.arena.building.interfaces.Building;
import me.crolemol.coc.arena.building.interfaces.BuildingSpecs;
import me.crolemol.coc.arena.building.interfaces.ResourceBuilding;
import me.crolemol.coc.arena.building.interfaces.ResourceBuildingSpecs;
import me.crolemol.coc.arena.building.interfaces.StorageBuildingSpecs;
import me.crolemol.coc.arena.events.BuildingInteractEvent;
import me.crolemol.coc.economy.Resources;
import me.crolemol.coc.utils.TimetoGemCalc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.HumanEntity;
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
	private Coc plugin  = Coc.getPlugin();
	private Map<String,Building> currentBuilding = new HashMap<>();
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
			return;
		}
		if(building instanceof GoldStorage){
			openGoldStoragePanel((GoldStorage) building);
			return;
		}
		if(building instanceof ElixirCollector){
			return;
		}
		
	}
	private void openElixirCollectorPanel(ResourceBuilding building){
		ResourceBuildingSpecs[] spec = building.getBuildingSpecs();
		FileConfiguration dataconf = plugin.getdataconffile(building.getOwner());
		Inventory inv = Bukkit.createInventory(null, 9, "Goldmine");
		
		ItemStack Info = new ItemStack(Material.BOOK_AND_QUILL);
		ItemMeta InfoMeta = Info.getItemMeta();
		InfoMeta.setDisplayName(ChatColor.LIGHT_PURPLE+"Info");
		List<String> list = new ArrayList<String>();
		list.add("Elixir is pumped from the Ley Lines coursing");
		list.add(" underneath your village. Upgrade your Elixir Collectors to ");
		list.add("maximize elixir production.");
		if(building.getLevel() != 0){
		list.add("Health: "+ spec[dataconf.getInt(building.getBuildingName()+"."+building.getBuildingID()+".level")-1].getHealth());
		list.add("Production per hour: "+ spec[dataconf.getInt(building.getBuildingName()+"."+building.getBuildingID()+".level")-1].getProduction());
		list.add("Capacity: "+ spec[dataconf.getInt(building.getBuildingName()+"."+building.getBuildingID()+".level")-1].getCapacity());
		}
		InfoMeta.setLore(list);
		Info.setItemMeta(InfoMeta);
		
		ItemStack Collect = new ItemStack(Material.GHAST_TEAR);
		Collect.addUnsafeEnchantment(null, 0);
		ItemMeta CollectMeta = Collect.getItemMeta();
		CollectMeta.setDisplayName(ChatColor.LIGHT_PURPLE+"Collect");
		List<String> list4 = new ArrayList<String>();
		list4.add("Collectable Elixir: " + building.getCollectable());
		CollectMeta.setLore(list4);
		Collect.setItemMeta(CollectMeta);
		
		ItemStack Upgrade = new ItemStack(Material.IRON_PICKAXE);
		ItemMeta UpgradeMeta = Upgrade.getItemMeta();
		UpgradeMeta.setDisplayName(ChatColor.LIGHT_PURPLE+"Upgrade");
		List<String> list2 = new ArrayList<String>();
		if(!dataconf.contains(building.getBuildingName()+"."+building.getBuildingID()+".upgrade")){
		list2.add("Upgrade your Elixir Collector to");
		list2.add("increase the production and capacity,");
		list2.add("Costs:");
		list2.add("Gold: "+ spec[dataconf.getInt(building.getBuildingName()+"."+building.getBuildingID()+".level")].getUpgradePrice().getAmount());
		list2.add("Time: " + LongtoSimpleString(spec[dataconf.getInt(building.getBuildingName()+"."+building.getBuildingID()+".level")].getUpgradeTime()));
		}else{
			Calendar cal = Calendar.getInstance();
			Long caltime = cal.getTimeInMillis()/60/1000;
			Long cal2 = dataconf.getLong(building.getBuildingName()+"."+building.getBuildingID()+".upgrade");
			Long time1 = timeBetweenDates(cal2, caltime);
			int time2 = spec[dataconf.getInt(building.getBuildingName()+"."+building.getBuildingID()+".level")].getUpgradeTime();
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
		Long cal2 = dataconf.getLong(building.getBuildingName()+"."+building.getBuildingID()+".upgrade");
		Long time1 = timeBetweenDates(cal2, caltime);
		int time2 = spec[dataconf.getInt(building.getBuildingName()+"."+building.getBuildingID()+".level")].getUpgradeTime();
		Long time3 = time2 - time1;
		List<String> list3 = new ArrayList<String>();
		list3.add("Speed up the building progress,");
		list3.add("Cost:");
		TimetoGemCalc calc = new TimetoGemCalc();
		list3.add(calc.Calc(time3*60)+" Gems");
		SpeedMeta.setLore(list3);
		Speed.setItemMeta(SpeedMeta);
		
		inv.setItem(0, Info);
		if(building.getLevel() != 0){
		inv.setItem(1, Collect);
		}
		if(dataconf.getInt(building.getBuildingName()+"."+building.getBuildingID()+".level")!=Buildingspecs.goldmine.getMaxLevel()){
		inv.setItem(8, Upgrade);
		}
		if(dataconf.contains(building.getBuildingName()+"."+building.getBuildingID()+".upgrade")){
			inv.setItem(7, Speed);
		}
		((HumanEntity) building.getOwner()).openInventory(inv);
		
	}
	private void openGoldMinePanel(Goldmine building){
		ResourceBuildingSpecs[] spec = building.getBuildingSpecs();
		Goldmine goldmine = Goldmine.getGoldmine(building.getBuildingID(), building.getOwner());
		FileConfiguration dataconf = plugin.getdataconffile(building.getOwner());
		Inventory inv = Bukkit.createInventory(null, 9, "Goldmine");
		
		ItemStack Info = new ItemStack(Material.BOOK_AND_QUILL);
		ItemMeta InfoMeta = Info.getItemMeta();
		InfoMeta.setDisplayName(ChatColor.LIGHT_PURPLE+"Info");
		List<String> list = new ArrayList<String>();
		list.add("a recoursebuilding who lets you collect gold,");
		list.add("upgrade your goldmine to gain more gold per hour");
		if(building.getLevel() != 0){
		list.add("Level: "+building.getLevel());
		list.add("Health: "+ spec[dataconf.getInt("goldmine."+building.getBuildingID()+".level")-1].getHealth());
		list.add("Production per hour: "+ spec[dataconf.getInt("goldmine."+building.getBuildingID()+".level")-1].getProduction());
		list.add("Capacity: "+ spec[dataconf.getInt("goldmine."+building.getBuildingID()+".level")-1].getCapacity());
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
		if(!dataconf.contains("goldmine."+building.getBuildingID()+".upgrade")){
		list2.add("Upgrade your goldmine to");
		list2.add("increase the production and capacity,");
		list2.add("Costs:");
		list2.add("Elixir: "+ spec[dataconf.getInt("goldmine."+building.getBuildingID()+".level")].getUpgradePrice().getAmount());
		list2.add("Time: " + LongtoSimpleString(spec[dataconf.getInt("goldmine."+building.getBuildingID()+".level")].getUpgradeTime()));
		}else{
			Calendar cal = Calendar.getInstance();
			Long caltime = cal.getTimeInMillis()/60/1000;
			Long cal2 = dataconf.getLong("goldmine."+building.getBuildingID()+".upgrade");
			Long time1 = timeBetweenDates(cal2, caltime);
			int time2 = spec[dataconf.getInt("goldmine."+building.getBuildingID()+".level")].getUpgradeTime();
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
		Long cal2 = dataconf.getLong("goldmine."+building.getBuildingID()+".upgrade");
		Long time1 = timeBetweenDates(cal2, caltime);
		int time2 = spec[dataconf.getInt("goldmine."+building.getBuildingID()+".level")].getUpgradeTime();
		Long time3 = time2 - time1;
		List<String> list3 = new ArrayList<String>();
		list3.add("Speed up the building progress,");
		list3.add("Cost:");
		TimetoGemCalc calc = new TimetoGemCalc();
		list3.add(calc.Calc(time3*60)+" Gems");
		SpeedMeta.setLore(list3);
		Speed.setItemMeta(SpeedMeta);
		
		inv.setItem(0, Info);
		if(building.getLevel() != 0){
		inv.setItem(1, Collect);}
		if(dataconf.getInt("goldmine."+building.getBuildingID()+".level")!=Buildingspecs.goldmine.getMaxLevel()){
		inv.setItem(8, Upgrade);
		}
		if(dataconf.contains("goldmine."+building.getBuildingID()+".upgrade")){
			inv.setItem(7, Speed);
		}
		((HumanEntity) building.getOwner()).openInventory(inv);
		
		
	}
	private void openGoldStoragePanel(GoldStorage building){
		StorageBuildingSpecs[] spec = building.getBuildingSpecs();
		FileConfiguration dataconf = plugin.getdataconffile(building.getOwner());
		Inventory inv = Bukkit.createInventory(null, 9, "Goldstorage");
		
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
		list2.add("Time: " + LongtoSimpleString(spec[dataconf.getInt("goldstorage."+building.getBuildingID()+".level")].getUpgradeTime()));
		}else{
			Calendar cal = Calendar.getInstance();
			Long caltime = cal.getTimeInMillis()/60/1000;
			Long cal2 = dataconf.getLong("goldstorage."+building.getBuildingID()+".upgrade");
			Long time1 = timeBetweenDates(cal2, caltime);
			int time2 = spec[dataconf.getInt("goldstorage."+building.getBuildingID()+".level")].getUpgradeTime();
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
		Long cal2 = dataconf.getLong("goldstorage."+building.getBuildingID()+".upgrade");
		Long time1 = timeBetweenDates(cal2, caltime);
		int time2 = spec[dataconf.getInt("goldstorage."+building.getBuildingID()+".level")].getUpgradeTime();
		Long time3 = time2 - time1;
		List<String> list3 = new ArrayList<String>();
		list3.add("Speed up the building progress,");
		list3.add("Cost:");
		TimetoGemCalc calc = new TimetoGemCalc();
		list3.add(calc.Calc(time3*60)+" Gems");
		SpeedMeta.setLore(list3);
		Speed.setItemMeta(SpeedMeta);
		
		inv.setItem(0, Info);
		if(dataconf.getInt("goldstorage."+building.getBuildingID()+".level")!=Buildingspecs.goldstorage.getMaxLevel()){
		inv.setItem(8, Upgrade);
		}
		if(dataconf.contains("goldstorage."+building.getBuildingID()+".upgrade")){
			inv.setItem(7, Speed);
		}
		((HumanEntity) building.getOwner()).openInventory(inv);
		

		
		
	}
	
	private void openTownhallPanel(Townhall building){
		BuildingSpecs[] spec = building.getBuildingSpecs();
		Coc.getPlugin();
		FileConfiguration dataconf = plugin.getdataconffile(building.getOwner());
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
		list2.add("Gold: "+ spec[dataconf.getInt("townhall.1.level")].getUpgradePrice().getAmount());
		list2.add("Time: " + LongtoSimpleString(spec[dataconf.getInt("townhall.1.level")].getUpgradeTime()));
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
		((HumanEntity) building.getOwner()).openInventory(inv);
		
	}
	
	@EventHandler
	public void OnInventoryClick(InventoryClickEvent event){
		if(!event.getWhoClicked().getLocation().getWorld().getName().equals("coc")){return;}
		if(ChatColor.stripColor(event.getInventory().getName()).equalsIgnoreCase("Townhall")){
			TownhallPanelClick(event);
			return;
	}
		if(ChatColor.stripColor(event.getInventory().getName()).equalsIgnoreCase("Goldmine")){
			GoldminePanelClick(event);
			return;
	}
		if(ChatColor.stripColor(event.getInventory().getName()).equalsIgnoreCase("Goldstorage")){
			GoldStoragePanelClick(event);
			return;
	}
		}
	private void GoldStoragePanelClick(InventoryClickEvent event){
		Player player = (Player) event.getWhoClicked();
		FileConfiguration dataconf = plugin.getdataconffile(player);
		event.setCancelled(true);
		if(event.getCurrentItem()==null || !event.getCurrentItem().hasItemMeta()){
			return;
		}
		switch(event.getCurrentItem().getType()){
		case BOOK_AND_QUILL:
			break;
		case IRON_PICKAXE:
			BuildingSpecs[] spec = currentBuilding.get(event.getWhoClicked().getName()).getBuildingSpecs();
			if(!dataconf.contains("goldstorage."+currentBuilding.get(event.getWhoClicked().getName()).getBuildingID()+".upgrade") && dataconf.getInt("Elixir") >= spec[dataconf.getInt("goldstorage."+currentBuilding.get(event.getWhoClicked().getName()).getBuildingID()+".level")].getUpgradePrice().getAmount()){
			if(dataconf.getInt("townhall.1.level") >= spec[dataconf.getInt("goldstorage."+currentBuilding.get(event.getWhoClicked().getName()).getBuildingID()+".level")].getMinTownhallLevel()){
			UpgradeBuilding upgrade = new UpgradeBuilding();
			upgrade.startNewUpgrade(currentBuilding.get(event.getWhoClicked().getName()));

			Resources.takeElixir((Player)event.getWhoClicked(), spec[dataconf.getInt("goldstorage."+currentBuilding.get(event.getWhoClicked().getName()).getBuildingID()+".level")].getUpgradePrice().getAmount());
			event.getWhoClicked().closeInventory();
				}else{
					player.sendMessage(ChatColor.RED+"[ClashofClans] You need to upgrade your townhall to upgrade this building!");
				}
			}
			break;
		case EMERALD:
			UpgradeBuilding ub = new UpgradeBuilding();
			ub.FinishUpgrade(currentBuilding.get(event.getWhoClicked().getName()));
			Calendar cal = Calendar.getInstance();
			BuildingSpecs[] spec2 = currentBuilding.get(event.getWhoClicked().getName()).getBuildingSpecs();
			Long caltime = cal.getTimeInMillis()/60/1000;
			Long cal2 = dataconf.getLong("goldstorage."+currentBuilding.get(event.getWhoClicked().getName()).getBuildingID()+".upgrade");
			Long time1 = timeBetweenDates(cal2, caltime);
			int time2 = spec2[dataconf.getInt("goldstorage."+currentBuilding.get(event.getWhoClicked().getName()).getBuildingID()+".level")].getUpgradeTime();
			Long time3 = time2 - time1;
			new Resources();
			TimetoGemCalc calc = new TimetoGemCalc();
			Resources.takeGems((Player)event.getWhoClicked(), calc.Calc(time3*60));
			event.getWhoClicked().closeInventory();
			break;
		default: player.closeInventory();
		}
	}
	private void GoldminePanelClick(InventoryClickEvent event){
		Player player = (Player) event.getWhoClicked();
		FileConfiguration dataconf = plugin.getdataconffile(player);
		event.setCancelled(true);
		if(event.getCurrentItem()==null || !event.getCurrentItem().hasItemMeta()){
			return;
		}
		switch(event.getCurrentItem().getType()){
		case BOOK_AND_QUILL:
			break;
		case GOLD_NUGGET:
			Goldmine goldmine = (Goldmine) currentBuilding.get(event.getWhoClicked().getName());
			goldmine.Collect();
			event.getWhoClicked().closeInventory();
			break;
		case IRON_PICKAXE:
			BuildingSpecs[] spec = currentBuilding.get(event.getWhoClicked().getName()).getBuildingSpecs();
			if(!dataconf.contains("goldmine."+currentBuilding.get(event.getWhoClicked().getName()).getBuildingID()+".upgrade") && dataconf.getInt("Elixir") >= spec[dataconf.getInt("goldmine."+currentBuilding.get(event.getWhoClicked().getName()).getBuildingID()+".level")].getUpgradePrice().getAmount()){
			if(dataconf.getInt("townhall.1.level") >= spec[dataconf.getInt("goldmine."+currentBuilding.get(event.getWhoClicked().getName()).getBuildingID()+".level")].getMinTownhallLevel()){
			UpgradeBuilding upgrade = new UpgradeBuilding();
			upgrade.startNewUpgrade(currentBuilding.get(event.getWhoClicked().getName()));

			Resources.takeElixir((Player)event.getWhoClicked(), spec[dataconf.getInt("goldmine."+currentBuilding.get(event.getWhoClicked().getName()).getBuildingID()+".level")].getUpgradePrice().getAmount());
			event.getWhoClicked().closeInventory();
			}else{
				player.sendMessage(ChatColor.RED+"[ClashofClans] You need to upgrade your townhall to upgrade this building!");
			}
				}
			break;
		case EMERALD:
			UpgradeBuilding ub = new UpgradeBuilding();
			ub.FinishUpgrade(currentBuilding.get(event.getWhoClicked().getName()));
			Calendar cal = Calendar.getInstance();
			BuildingSpecs[] spec2 = currentBuilding.get(event.getWhoClicked().getName()).getBuildingSpecs();
			Long caltime = cal.getTimeInMillis()/60/1000;
			Long cal2 = dataconf.getLong("goldmine."+currentBuilding.get(event.getWhoClicked().getName()).getBuildingID()+".upgrade");
			Long time1 = timeBetweenDates(cal2, caltime);
			int time2 = spec2[dataconf.getInt("goldmine."+currentBuilding.get(event.getWhoClicked().getName()).getBuildingID()+".level")].getUpgradeTime();
			Long time3 = time2 - time1;
			TimetoGemCalc calc = new TimetoGemCalc();
			Resources.takeGems((Player)event.getWhoClicked(), calc.Calc(time3*60));
			event.getWhoClicked().closeInventory();
			break;
		default: player.closeInventory();
			
		}
		
	}
	private void TownhallPanelClick(InventoryClickEvent event){
		Player player = (Player) event.getWhoClicked();
		FileConfiguration dataconf = plugin.getdataconffile(player);
		event.setCancelled(true);
		if(event.getCurrentItem()==null || !event.getCurrentItem().hasItemMeta()){
			return;
		}
		switch(event.getCurrentItem().getType()){
		case BOOK_AND_QUILL:
			break;
		case IRON_PICKAXE:
			BuildingSpecs[] spec = currentBuilding.get(event.getWhoClicked().getName()).getBuildingSpecs();
			if(!dataconf.contains("townhall.1.upgrade") && dataconf.getInt("Gold") >= spec[dataconf.getInt("townhall.1.level")].getUpgradePrice().getAmount()){
			UpgradeBuilding upgrade = new UpgradeBuilding();
			upgrade.startNewUpgrade(currentBuilding.get(event.getWhoClicked().getName()));
			new Resources();

			Resources.takeGold((Player)event.getWhoClicked(), spec[dataconf.getInt("townhall.1.level")].getUpgradePrice().getAmount());
			event.getWhoClicked().closeInventory();}
			break;
		case EMERALD:
			Calendar cal = Calendar.getInstance();
			BuildingSpecs[] spec2 = currentBuilding.get(event.getWhoClicked().getName()).getBuildingSpecs();
			Long caltime = cal.getTimeInMillis()/60/1000;
			Long cal2 = dataconf.getLong("townhall.1.upgrade");
			Long time1 = timeBetweenDates(cal2, caltime);
			int time2 = spec2[dataconf.getInt("townhall.1.level")].getUpgradeTime();
			Long time3 = time2 - time1;
			TimetoGemCalc calc = new TimetoGemCalc();
			Resources.takeGems((Player)event.getWhoClicked(), calc.Calc(time3*60));
			UpgradeBuilding ub = new UpgradeBuilding();
			ub.FinishUpgrade(currentBuilding.get(event.getWhoClicked().getName()));
			event.getWhoClicked().closeInventory();
			break;
		default: player.closeInventory();
			
		}
		
	}
	@EventHandler
	private void onBuildingInteract(BuildingInteractEvent event){
		if(event.getBuilding().getBuildingID() == 0){return;}
		if(checkNotNull(event.getBuilding().getOwner()).equals(false)){return;}
				currentBuilding.put(event.getBuilding().getOwner().getName(), event.getBuilding());
				openBuildingPanel(event.getBuilding());
	} 
	@EventHandler
	private void onInventoryCloseEvent(InventoryCloseEvent event){
		if(currentBuilding.containsKey(event.getPlayer())){
			currentBuilding.remove(event.getPlayer().getName());
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
