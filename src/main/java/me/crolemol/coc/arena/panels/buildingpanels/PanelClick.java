package me.crolemol.coc.arena.panels.buildingpanels;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import me.crolemol.coc.Coc;
import me.crolemol.coc.arena.building.ArmyCamp;
import me.crolemol.coc.arena.building.DarkElixirDrill;
import me.crolemol.coc.arena.building.DarkElixirStorage;
import me.crolemol.coc.arena.building.ElixirCollector;
import me.crolemol.coc.arena.building.ElixirStorage;
import me.crolemol.coc.arena.building.GoldStorage;
import me.crolemol.coc.arena.building.Goldmine;
import me.crolemol.coc.arena.building.Townhall;
import me.crolemol.coc.arena.building.UpgradeBuilding;
import me.crolemol.coc.arena.building.interfaces.Building;
import me.crolemol.coc.arena.building.interfaces.BuildingSpecs;
import me.crolemol.coc.arena.events.BuildingInteractEvent;
import me.crolemol.coc.economy.Resources;
import me.crolemol.coc.utils.PanelUtils;
import me.crolemol.coc.utils.TimetoGemCalc;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class PanelClick implements Listener{
	Coc plugin  = Coc .getPlugin();
	public static Map<String,Building> staticbuilding = new HashMap<>();
	@EventHandler
	private void onInventoryClick(InventoryClickEvent event){
		if(event.getInventory().getName().equals("Townhall")){
			TownhallClickEvent(event);
		}else if(event.getInventory().getName().equals("Goldmine")){
			GoldminePanelClick(event);
		}else if(event.getInventory().getName().equals("Builder's hut")){
			BuildersHutPanelClick(event);
		}else if(event.getInventory().getName().equals("Goldstorage")){
			GoldStoragePanelClick(event);
		}else if(event.getInventory().getName().equals("Elixir Collector")){
			ElixirCollectorPanelClick(event);
		}else if(event.getInventory().getName().equals("Elixir Storage")){
			ElixirStoragePanelClick(event);
		}else if(event.getInventory().getName().equals("Dark Elixir Storage")){
			DarkElixirStoragePanelClick(event);
		}else if(event.getInventory().getName().equals("Dark Elixir Drill")){
			DarkElixirDrillPanelClick(event);
		}else if(event.getInventory().getName().equals("Army Camp")){
			ArmyCampPanelClick(event);
		}
			
		
	}
	private void TownhallClickEvent(InventoryClickEvent event){
		if(staticbuilding.get(event.getWhoClicked().getName()) == null){return;}
		Player player = (Player) event.getWhoClicked();
		Townhall building = (Townhall) staticbuilding.get(event.getWhoClicked().getName());
		FileConfiguration dataconf = plugin.getdataconffile(player);
		event.setCancelled(true);
		if(event.getCurrentItem()==null || !event.getCurrentItem().hasItemMeta()){
			return;
		}
		switch(event.getCurrentItem().getType()){
		case BOOK_AND_QUILL:
			break;
		case IRON_PICKAXE:
			BuildingSpecs[] spec = building.getBuildingSpecs();
			if(!dataconf.contains("townhall.1.upgrade") && dataconf.getInt("Gold") >= spec[dataconf.getInt("townhall.1.level")].getUpgradePrice().getAmount()){
			UpgradeBuilding upgrade = new UpgradeBuilding();
			upgrade.startNewUpgrade(building);

			event.getWhoClicked().closeInventory();}
			break;
		case EMERALD:
			Calendar cal = Calendar.getInstance();
			BuildingSpecs[] spec2 = building.getBuildingSpecs();
			Long caltime = cal.getTimeInMillis()/60/1000;
			Long cal2 = dataconf.getLong("townhall.1.upgrade");
			Long time1 = PanelUtils.timeBetweenDates(cal2, caltime);
			int time2 = spec2[dataconf.getInt("townhall.1.level")].getUpgradeTime();
			Long time3 = time2 - time1;
			TimetoGemCalc calc = new TimetoGemCalc();
			Resources.takeGems((Player)event.getWhoClicked(), calc.Calc(time3*60));
			UpgradeBuilding ub = new UpgradeBuilding();
			ub.FinishUpgrade(building);
			event.getWhoClicked().closeInventory();
			break;
		default: player.closeInventory();
			
			}
		}
	
	private void GoldminePanelClick(InventoryClickEvent event){
		if(staticbuilding.get(event.getWhoClicked().getName()) == null){return;}
		Player player = (Player) event.getWhoClicked();
		Goldmine building = (Goldmine) staticbuilding.get(event.getWhoClicked().getName());
		FileConfiguration dataconf = plugin.getdataconffile(player);
		event.setCancelled(true);
		if(event.getCurrentItem()==null || !event.getCurrentItem().hasItemMeta()){
			return;
		}
		switch(event.getCurrentItem().getType()){
		case BOOK_AND_QUILL:
			break;
		case GOLD_NUGGET:
			Goldmine goldmine = building;
			goldmine.Collect();
			event.getWhoClicked().closeInventory();
			break;
		case IRON_PICKAXE:
			BuildingSpecs[] spec = building.getBuildingSpecs();
			if(!dataconf.contains(building.getBuildingName()+"."+building.getBuildingID()+".upgrade") && dataconf.getInt("Elixir") >= spec[dataconf.getInt(building.getBuildingName()+"."+building.getBuildingID()+".level")].getUpgradePrice().getAmount()){
			if(dataconf.getInt("townhall.1.level") >= spec[dataconf.getInt(building.getBuildingName()+"."+building.getBuildingID()+".level")].getMinTownhallLevel()){
			UpgradeBuilding upgrade = new UpgradeBuilding();
			upgrade.startNewUpgrade(building);
			event.getWhoClicked().closeInventory();
			}else{
				player.sendMessage(ChatColor.RED+"[ClashofClans] You need to upgrade your townhall to upgrade this building!");
			}
				}
			break;
		case EMERALD:
			UpgradeBuilding ub = new UpgradeBuilding();
			Calendar cal = Calendar.getInstance();
			BuildingSpecs[] spec2 = building.getBuildingSpecs();
			Long caltime = cal.getTimeInMillis()/60/1000;
			Long cal2 = dataconf.getLong(building.getBuildingName()+"."+building.getBuildingID()+".upgrade");
			Long time1 = PanelUtils.timeBetweenDates(cal2, caltime);
			int time2 = spec2[dataconf.getInt(building.getBuildingName()+"."+building.getBuildingID()+".level")].getUpgradeTime();
			Long time3 = time2 - time1;
			TimetoGemCalc calc = new TimetoGemCalc();
			Resources.takeGems((Player)event.getWhoClicked(), calc.Calc(time3*60));
			ub.FinishUpgrade(building);
			event.getWhoClicked().closeInventory();
			break;
		default: player.closeInventory();
			
		}
		
	}
	
	private void GoldStoragePanelClick(InventoryClickEvent event){
		if(staticbuilding.get(event.getWhoClicked().getName()) == null){return;}
		Player player = (Player) event.getWhoClicked();
		GoldStorage building = (GoldStorage) staticbuilding.get(event.getWhoClicked().getName());
		FileConfiguration dataconf = plugin.getdataconffile(player);
		event.setCancelled(true);
		if(event.getCurrentItem()==null || !event.getCurrentItem().hasItemMeta()){
			return;
		}
		switch(event.getCurrentItem().getType()){
		case BOOK_AND_QUILL:
			break;
		case IRON_PICKAXE:
			BuildingSpecs[] spec = building.getBuildingSpecs();
			if(!dataconf.contains(building.getBuildingName()+"."+building.getBuildingID()+".upgrade") && dataconf.getInt("Elixir") >= spec[dataconf.getInt(building.getBuildingName()+"."+building.getBuildingID()+".level")].getUpgradePrice().getAmount()){
			if(dataconf.getInt("townhall.1.level") >= spec[dataconf.getInt(building.getBuildingName()+"."+building.getBuildingID()+".level")].getMinTownhallLevel()){
			UpgradeBuilding upgrade = new UpgradeBuilding();
			upgrade.startNewUpgrade(building);
			event.getWhoClicked().closeInventory();
				}else{
					player.sendMessage(ChatColor.RED+"[ClashofClans] You need to upgrade your townhall to upgrade this building!");
				}
			}
			break;
		case EMERALD:
			UpgradeBuilding ub = new UpgradeBuilding();
			Calendar cal = Calendar.getInstance();
			BuildingSpecs[] spec2 = building.getBuildingSpecs();
			Long caltime = cal.getTimeInMillis()/60/1000;
			Long cal2 = dataconf.getLong(building.getBuildingName()+"."+building.getBuildingID()+".upgrade");
			Long time1 = PanelUtils.timeBetweenDates(cal2, caltime);
			int time2 = spec2[dataconf.getInt(building.getBuildingName()+"."+building.getBuildingID()+".level")].getUpgradeTime();
			Long time3 = time2 - time1;
			TimetoGemCalc calc = new TimetoGemCalc();
			Resources.takeGems((Player)event.getWhoClicked(), calc.Calc(time3*60));
			ub.FinishUpgrade(building);
			event.getWhoClicked().closeInventory();
			break;
		default: player.closeInventory();
		}
		
	}
	
	@EventHandler
	private void BuildersHutPanelClick(InventoryClickEvent event){
		if(staticbuilding.get(event.getWhoClicked().getName()) == null){return;}
		Player player = (Player) event.getWhoClicked();
		event.setCancelled(true);
		if(event.getCurrentItem()==null || !event.getCurrentItem().hasItemMeta()){
			return;
		}
		switch(event.getCurrentItem().getType()){
		case BOOK_AND_QUILL:
			break;
		default: player.closeInventory();
			
		}
		
	}
	
	private void ElixirCollectorPanelClick(InventoryClickEvent event){
		if(staticbuilding.get(event.getWhoClicked().getName()) == null){return;}
		Player player = (Player) event.getWhoClicked();
		ElixirCollector building = (ElixirCollector) staticbuilding.get(event.getWhoClicked().getName());
		FileConfiguration dataconf = plugin.getdataconffile(player);
		event.setCancelled(true);
		if(event.getCurrentItem()==null || !event.getCurrentItem().hasItemMeta()){
			return;
		}
		switch(event.getCurrentItem().getType()){
		case BOOK_AND_QUILL:
			break;
		case GHAST_TEAR:
			building.Collect();
			event.getWhoClicked().closeInventory();
			break;
		case IRON_PICKAXE:
			BuildingSpecs[] spec = building.getBuildingSpecs();
			if(!dataconf.contains(building.getBuildingName()+"."+building.getBuildingID()+".upgrade") && dataconf.getInt("Elixir") >= spec[dataconf.getInt(building.getBuildingName()+"."+building.getBuildingID()+".level")].getUpgradePrice().getAmount()){
			if(dataconf.getInt("townhall.1.level") >= spec[dataconf.getInt(building.getBuildingName()+"."+building.getBuildingID()+".level")].getMinTownhallLevel()){
			UpgradeBuilding upgrade = new UpgradeBuilding();
			upgrade.startNewUpgrade(building);
			event.getWhoClicked().closeInventory();
			}else{
				player.sendMessage(ChatColor.RED+"[ClashofClans] You need to upgrade your townhall to upgrade this building!");
			}
				}
			break;
		case EMERALD:
			UpgradeBuilding ub = new UpgradeBuilding();
			Calendar cal = Calendar.getInstance();
			BuildingSpecs[] spec2 = building.getBuildingSpecs();
			Long caltime = cal.getTimeInMillis()/60/1000;
			Long cal2 = dataconf.getLong(building.getBuildingName()+"."+building.getBuildingID()+".upgrade");
			Long time1 = PanelUtils.timeBetweenDates(cal2, caltime);
			int time2 = spec2[dataconf.getInt(building.getBuildingName()+"."+building.getBuildingID()+".level")].getUpgradeTime();
			Long time3 = time2 - time1;
			TimetoGemCalc calc = new TimetoGemCalc();
			Resources.takeGems((Player)event.getWhoClicked(), calc.Calc(time3*60));
			ub.FinishUpgrade(building);
			event.getWhoClicked().closeInventory();
			break;
		default: player.closeInventory();
			
		}
		
		
	}
	
	private void DarkElixirStoragePanelClick(InventoryClickEvent event){
		if(staticbuilding.get(event.getWhoClicked().getName()) == null){return;}
		Player player = (Player) event.getWhoClicked();
		DarkElixirStorage building = (DarkElixirStorage) staticbuilding.get(event.getWhoClicked().getName());
		FileConfiguration dataconf = plugin.getdataconffile(player);
		event.setCancelled(true);
		if(event.getCurrentItem()==null || !event.getCurrentItem().hasItemMeta()){
			return;
		}
		switch(event.getCurrentItem().getType()){
		case BOOK_AND_QUILL:
			break;
		case IRON_PICKAXE:
			BuildingSpecs[] spec = building.getBuildingSpecs();
			if(!dataconf.contains(building.getBuildingName()+"."+building.getBuildingID()+".upgrade") && dataconf.getInt("Elixir") >= spec[dataconf.getInt(building.getBuildingName()+"."+building.getBuildingID()+".level")].getUpgradePrice().getAmount()){
			if(dataconf.getInt("townhall.1.level") >= spec[dataconf.getInt(building.getBuildingName()+"."+building.getBuildingID()+".level")].getMinTownhallLevel()){
			UpgradeBuilding upgrade = new UpgradeBuilding();
			upgrade.startNewUpgrade(building);
			event.getWhoClicked().closeInventory();
				}else{
					player.sendMessage(ChatColor.RED+"[ClashofClans] You need to upgrade your townhall to upgrade this building!");
				}
			}
			break;
		case EMERALD:
			UpgradeBuilding ub = new UpgradeBuilding();
			Calendar cal = Calendar.getInstance();
			BuildingSpecs[] spec2 = building.getBuildingSpecs();
			Long caltime = cal.getTimeInMillis()/60/1000;
			Long cal2 = dataconf.getLong(building.getBuildingName()+"."+building.getBuildingID()+".upgrade");
			Long time1 = PanelUtils.timeBetweenDates(cal2, caltime);
			int time2 = spec2[dataconf.getInt(building.getBuildingName()+"."+building.getBuildingID()+".level")].getUpgradeTime();
			Long time3 = time2 - time1;
			TimetoGemCalc calc = new TimetoGemCalc();
			Resources.takeGems((Player)event.getWhoClicked(), calc.Calc(time3*60));
			ub.FinishUpgrade(building);
			event.getWhoClicked().closeInventory();
			break;
		default: player.closeInventory();
		}
		
	}
	private void ElixirStoragePanelClick(InventoryClickEvent event){
		if(staticbuilding.get(event.getWhoClicked().getName()) == null){return;}
		Player player = (Player) event.getWhoClicked();
		ElixirStorage building = (ElixirStorage) staticbuilding.get(event.getWhoClicked().getName());
		FileConfiguration dataconf = plugin.getdataconffile(player);
		event.setCancelled(true);
		if(event.getCurrentItem()==null || !event.getCurrentItem().hasItemMeta()){
			return;
		}
		switch(event.getCurrentItem().getType()){
		case BOOK_AND_QUILL:
			break;
		case IRON_PICKAXE:
			BuildingSpecs[] spec = building.getBuildingSpecs();
			if(!dataconf.contains(building.getBuildingName()+"."+building.getBuildingID()+".upgrade") && dataconf.getInt("Gold") >= spec[dataconf.getInt(building.getBuildingName()+"."+building.getBuildingID()+".level")].getUpgradePrice().getAmount()){
			if(dataconf.getInt("townhall.1.level") >= spec[dataconf.getInt(building.getBuildingName()+"."+building.getBuildingID()+".level")].getMinTownhallLevel()){
			UpgradeBuilding upgrade = new UpgradeBuilding();
			upgrade.startNewUpgrade(building);
			event.getWhoClicked().closeInventory();
				}else{
					player.sendMessage(ChatColor.RED+"[ClashofClans] You need to upgrade your townhall to upgrade this building!");
				}
			}
			break;
		case EMERALD:
			UpgradeBuilding ub = new UpgradeBuilding();
			Calendar cal = Calendar.getInstance();
			BuildingSpecs[] spec2 = building.getBuildingSpecs();
			Long caltime = cal.getTimeInMillis()/60/1000;
			Long cal2 = dataconf.getLong(building.getBuildingName()+"."+building.getBuildingID()+".upgrade");
			Long time1 = PanelUtils.timeBetweenDates(cal2, caltime);
			int time2 = spec2[dataconf.getInt(building.getBuildingName()+"."+building.getBuildingID()+".level")].getUpgradeTime();
			Long time3 = time2 - time1;
			TimetoGemCalc calc = new TimetoGemCalc();
			Resources.takeGems((Player)event.getWhoClicked(), calc.Calc(time3*60));
			ub.FinishUpgrade(building);
			event.getWhoClicked().closeInventory();
			break;
		default: player.closeInventory();
		}
		
	}
	
	private void DarkElixirDrillPanelClick(InventoryClickEvent event){
		if(staticbuilding.get(event.getWhoClicked().getName()) == null){return;}
		Player player = (Player) event.getWhoClicked();
		DarkElixirDrill building = (DarkElixirDrill) staticbuilding.get(event.getWhoClicked().getName());
		FileConfiguration dataconf = plugin.getdataconffile(player);
		event.setCancelled(true);
		if(event.getCurrentItem()==null || !event.getCurrentItem().hasItemMeta()){
			return;
		}
		switch(event.getCurrentItem().getType()){
		case BOOK_AND_QUILL:
			break;
		case GHAST_TEAR:
			building.Collect();
			event.getWhoClicked().closeInventory();
			break;
		case IRON_PICKAXE:
			BuildingSpecs[] spec = building.getBuildingSpecs();
			if(!dataconf.contains(building.getBuildingName()+"."+building.getBuildingID()+".upgrade") && dataconf.getInt("Elixir") >= spec[dataconf.getInt(building.getBuildingName()+"."+building.getBuildingID()+".level")].getUpgradePrice().getAmount()){
			if(dataconf.getInt("townhall.1.level") >= spec[dataconf.getInt(building.getBuildingName()+"."+building.getBuildingID()+".level")].getMinTownhallLevel()){
			UpgradeBuilding upgrade = new UpgradeBuilding();
			upgrade.startNewUpgrade(building);
			event.getWhoClicked().closeInventory();
			}else{
				player.sendMessage(ChatColor.RED+"[ClashofClans] You need to upgrade your townhall to upgrade this building!");
			}
				}
			break;
		case EMERALD:
			UpgradeBuilding ub = new UpgradeBuilding();
			Calendar cal = Calendar.getInstance();
			BuildingSpecs[] spec2 = building.getBuildingSpecs();
			Long caltime = cal.getTimeInMillis()/60/1000;
			Long cal2 = dataconf.getLong(building.getBuildingName()+"."+building.getBuildingID()+".upgrade");
			Long time1 = PanelUtils.timeBetweenDates(cal2, caltime);
			int time2 = spec2[dataconf.getInt(building.getBuildingName()+"."+building.getBuildingID()+".level")].getUpgradeTime();
			Long time3 = time2 - time1;
			TimetoGemCalc calc = new TimetoGemCalc();
			Resources.takeGems((Player)event.getWhoClicked(), calc.Calc(time3*60));
			ub.FinishUpgrade(building);
			event.getWhoClicked().closeInventory();
			break;
		default: player.closeInventory();
			
		}		
	}
	
	private void ArmyCampPanelClick(InventoryClickEvent event){		
		if(staticbuilding.get(event.getWhoClicked().getName()) == null){return;}
		((CommandSender) event.getWhoClicked()).sendMessage("test1");
		Player player = (Player) event.getWhoClicked();

		ArmyCamp building = (ArmyCamp) staticbuilding.get(event.getWhoClicked().getName());
		FileConfiguration dataconf = plugin.getdataconffile(player);
		event.setCancelled(true);
		if(event.getCurrentItem()==null || !event.getCurrentItem().hasItemMeta()){
			return;
		}
		switch(event.getCurrentItem().getType()){
		case BOOK_AND_QUILL:
			break;
		case IRON_PICKAXE:
			BuildingSpecs[] spec = building.getBuildingSpecs();
			if(!dataconf.contains(building.getBuildingName()+".1.upgrade") && Resources.getElixir(player) >= spec[dataconf.getInt(building.getBuildingName()+".1.level")].getUpgradePrice().getAmount()){
			UpgradeBuilding upgrade = new UpgradeBuilding();
			upgrade.startNewUpgrade(building);

			event.getWhoClicked().closeInventory();}
			break;
		case EMERALD:
			Calendar cal = Calendar.getInstance();
			BuildingSpecs[] spec2 = building.getBuildingSpecs();
			Long caltime = cal.getTimeInMillis()/60/1000;
			Long cal2 = dataconf.getLong(building.getBuildingName()+".1.upgrade");
			Long time1 = PanelUtils.timeBetweenDates(cal2, caltime);
			int time2 = spec2[dataconf.getInt(building.getBuildingName()+".1.level")].getUpgradeTime();
			Long time3 = time2 - time1;
			TimetoGemCalc calc = new TimetoGemCalc();
			Resources.takeGems(player, calc.Calc(time3*60));
			UpgradeBuilding ub = new UpgradeBuilding();
			ub.FinishUpgrade(building);
			event.getWhoClicked().closeInventory();
			break;
		default: player.closeInventory();
			
			}
		}
	
	@EventHandler
	private void onInventoryClose(InventoryCloseEvent event){
		staticbuilding.remove(event.getPlayer().getUniqueId());
	}
	@EventHandler
	private void onBuildingInteract(BuildingInteractEvent event){
		staticbuilding.put(event.getPlayer().getName(), event.getBuilding());
	}
}
