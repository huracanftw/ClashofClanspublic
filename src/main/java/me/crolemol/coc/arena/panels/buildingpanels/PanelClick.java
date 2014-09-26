package me.crolemol.coc.arena.panels.buildingpanels;

import java.sql.ResultSet;
import java.sql.SQLException;
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
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class PanelClick implements Listener{
	Coc plugin  = Coc.getPlugin();
	private static Map<String,Building> staticbuilding = new HashMap<>();
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
		event.setCancelled(true);
		if(event.getCurrentItem()==null || !event.getCurrentItem().hasItemMeta()){
			return;
		}
		Townhall townhall = Townhall.getTownhall(player);
		switch(event.getCurrentItem().getType()){
		case BOOK_AND_QUILL:
			break;
		case IRON_PICKAXE:
			BuildingSpecs[] spec = building.getBuildingSpecs();
			if(townhall.isUpgrading() == false && Resources.getGold(player) >= spec[building.getLevel()].getUpgradePrice().getAmount()){
			UpgradeBuilding upgrade = new UpgradeBuilding();
			upgrade.startNewUpgrade(building);

			event.getWhoClicked().closeInventory();}
			break;
		case EMERALD:
			Calendar cal = Calendar.getInstance();
			BuildingSpecs[] spec2 = building.getBuildingSpecs();
			Long caltime = cal.getTimeInMillis()/60/1000;
			ResultSet result = Coc.getPlugin().getDataBase().query("SELECT Upgrade FROM Buildings WHERE owner = '"
					+ building.getOwner().getUniqueId()
					+ "' AND BuildingID = "+ building.getBuildingID()
					+ " AND BuildingName = '"+building.getBuildingName()+"'");
			Long cal2 = (long) 0;
			try {
				cal2 = result.getLong("Upgrade");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			Long time1 = PanelUtils.timeBetweenDates(cal2, caltime);
			int time2 = spec2[building.getLevel()].getUpgradeTime();
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
		Townhall townhall = Townhall.getTownhall(player);
		event.setCancelled(true);
		if(event.getCurrentItem()==null || !event.getCurrentItem().hasItemMeta()){
			return;
		}
		switch(event.getCurrentItem().getType()){
		case BOOK_AND_QUILL:
			break;
		case GOLD_NUGGET:
			building.Collect();
			event.getWhoClicked().closeInventory();
			break;
		case IRON_PICKAXE:
			BuildingSpecs[] spec = building.getBuildingSpecs();
			if(building.isUpgrading() == false && Resources.getElixir(player) >= spec[building.getLevel()].getUpgradePrice().getAmount()){
			if(townhall.getLevel() >= spec[building.getLevel()].getMinTownhallLevel()){
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
			ResultSet result = Coc.getPlugin().getDataBase().query("SELECT Upgrade FROM Buildings WHERE owner = '"
					+ building.getOwner().getUniqueId()
					+ "' AND BuildingID = "+ building.getBuildingID()
					+ " AND BuildingName = '"+building.getBuildingName()+"'");
			Long cal2 = (long) 0;
			try {
				cal2 = result.getLong("Upgrade");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			Long time1 = PanelUtils.timeBetweenDates(cal2, caltime);
			int time2 = spec2[building.getLevel()].getUpgradeTime();
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
		event.setCancelled(true);
		Townhall townhall = Townhall.getTownhall(player);
		if(event.getCurrentItem()==null || !event.getCurrentItem().hasItemMeta()){
			return;
		}
		switch(event.getCurrentItem().getType()){
		case BOOK_AND_QUILL:
			break;
		case IRON_PICKAXE:
			BuildingSpecs[] spec = building.getBuildingSpecs();
			if(building.isUpgrading() && Resources.getElixir(player) >= spec[building.getLevel()].getUpgradePrice().getAmount()){
			if(townhall.getLevel() >= spec[building.getLevel()].getMinTownhallLevel()){
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
			ResultSet result = Coc.getPlugin().getDataBase().query("SELECT Upgrade FROM Buildings WHERE owner = '"
					+ building.getOwner().getUniqueId()
					+ "' AND BuildingID = "+ building.getBuildingID()
					+ " AND BuildingName = '"+building.getBuildingName()+"'");
			Long cal2 = (long) 0;
			try {
				cal2 = result.getLong("Upgrade");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			Long time1 = PanelUtils.timeBetweenDates(cal2, caltime);
			int time2 = spec2[building.getLevel()].getUpgradeTime();
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
			if(building.isUpgrading() == false && Resources.getGold(player) >= spec[building.getLevel()].getUpgradePrice().getAmount()){
			if(Townhall.getTownhall(player).getLevel() >= spec[building.getLevel()].getMinTownhallLevel()){
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
			ResultSet result = Coc.getPlugin().getDataBase().query("SELECT Upgrade FROM Buildings WHERE owner = '"
					+ building.getOwner().getUniqueId()
					+ "' AND BuildingID = "+ building.getBuildingID()
					+ " AND BuildingName = '"+building.getBuildingName()+"'");
			Long cal2 = (long) 0;
			try {
				cal2 = result.getLong("Upgrade");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			Long time1 = PanelUtils.timeBetweenDates(cal2, caltime);
			int time2 = spec2[building.getLevel()].getUpgradeTime();
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
		event.setCancelled(true);
		if(event.getCurrentItem()==null || !event.getCurrentItem().hasItemMeta()){
			return;
		}
		switch(event.getCurrentItem().getType()){
		case BOOK_AND_QUILL:
			break;
		case IRON_PICKAXE:
			BuildingSpecs[] spec = building.getBuildingSpecs();
			if(building.isUpgrading() == false && Resources.getElixir(player) >= spec[building.getLevel()].getUpgradePrice().getAmount()){
			if(Townhall.getTownhall(player).getLevel() >= spec[building.getLevel()].getMinTownhallLevel()){
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
			ResultSet result = Coc.getPlugin().getDataBase().query("SELECT Upgrade FROM Buildings WHERE owner = '"
					+ building.getOwner().getUniqueId()
					+ "' AND BuildingID = "+ building.getBuildingID()
					+ " AND BuildingName = '"+building.getBuildingName()+"'");
			Long cal2 = (long) 0;
			try {
				cal2 = result.getLong("Upgrade");
			} catch (SQLException e) {
				e.printStackTrace();
			}			Long time1 = PanelUtils.timeBetweenDates(cal2, caltime);
			int time2 = spec2[building.getLevel()].getUpgradeTime();
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
		event.setCancelled(true);
		if(event.getCurrentItem()==null || !event.getCurrentItem().hasItemMeta()){
			return;
		}
		switch(event.getCurrentItem().getType()){
		case BOOK_AND_QUILL:
			break;
		case IRON_PICKAXE:
			BuildingSpecs[] spec = building.getBuildingSpecs();
			if(building.isUpgrading() == false && Resources.getGold(player) >= spec[building.getLevel()].getUpgradePrice().getAmount()){
			if(Townhall.getTownhall(player).getLevel() >= spec[building.getLevel()].getMinTownhallLevel()){
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
			ResultSet result = Coc.getPlugin().getDataBase().query("SELECT Upgrade FROM Buildings WHERE owner = '"
					+ building.getOwner().getUniqueId()
					+ "' AND BuildingID = "+ building.getBuildingID()
					+ " AND BuildingName = '"+building.getBuildingName()+"'");
			Long cal2 = (long) 0;
			try {
				cal2 = result.getLong("Upgrade");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			Long time1 = PanelUtils.timeBetweenDates(cal2, caltime);
			int time2 = spec2[building.getLevel()].getUpgradeTime();
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
			if(building.isUpgrading() == false && Resources.getElixir(player) >= spec[building.getLevel()].getUpgradePrice().getAmount()){
			if(Townhall.getTownhall(player).getLevel() >= spec[building.getLevel()].getMinTownhallLevel()){
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
			ResultSet result = Coc.getPlugin().getDataBase().query("SELECT Upgrade FROM Buildings WHERE owner = '"
					+ building.getOwner().getUniqueId()
					+ "' AND BuildingID = "+ building.getBuildingID()
					+ " AND BuildingName = '"+building.getBuildingName()+"'");
			Long cal2 = (long) 0;
			try {
				cal2 = result.getLong("Upgrade");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			Long time1 = PanelUtils.timeBetweenDates(cal2, caltime);
			int time2 = spec2[building.getLevel()].getUpgradeTime();
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
		Player player = (Player) event.getWhoClicked();

		ArmyCamp building = (ArmyCamp) staticbuilding.get(event.getWhoClicked().getName());
		event.setCancelled(true);
		if(event.getCurrentItem()==null || !event.getCurrentItem().hasItemMeta()){
			return;
		}
		switch(event.getCurrentItem().getType()){
		case BOOK_AND_QUILL:
			break;
		case IRON_PICKAXE:
			BuildingSpecs[] spec = building.getBuildingSpecs();
			if(building.isUpgrading() == false && Resources.getElixir(player) >= spec[building.getLevel()].getUpgradePrice().getAmount()){
			UpgradeBuilding upgrade = new UpgradeBuilding();
			upgrade.startNewUpgrade(building);

			event.getWhoClicked().closeInventory();}
			break;
		case EMERALD:
			Calendar cal = Calendar.getInstance();
			BuildingSpecs[] spec2 = building.getBuildingSpecs();
			Long caltime = cal.getTimeInMillis()/60/1000;
			ResultSet result = Coc.getPlugin().getDataBase().query("SELECT Upgrade FROM Buildings WHERE owner = '"
					+ building.getOwner().getUniqueId()
					+ "' AND BuildingID = "+ building.getBuildingID()
					+ " AND BuildingName = '"+building.getBuildingName()+"'");
			Long cal2 = (long) 0;
			try {
				cal2 = result.getLong("Upgrade");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			Long time1 = PanelUtils.timeBetweenDates(cal2, caltime);
			int time2 = spec2[building.getLevel()].getUpgradeTime();
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
