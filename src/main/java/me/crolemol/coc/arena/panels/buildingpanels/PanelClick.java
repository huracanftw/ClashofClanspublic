package me.crolemol.coc.arena.panels.buildingpanels;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import me.crolemol.coc.Coc;
import me.crolemol.coc.arena.building.ArmyCamp;
import me.crolemol.coc.arena.building.Barracks;
import me.crolemol.coc.arena.building.DarkElixirDrill;
import me.crolemol.coc.arena.building.ElixirCollector;
import me.crolemol.coc.arena.building.Goldmine;
import me.crolemol.coc.arena.building.Townhall;
import me.crolemol.coc.arena.building.UpgradeBuilding;
import me.crolemol.coc.arena.building.interfaces.Building;
import me.crolemol.coc.arena.building.interfaces.BuildingSpecs;
import me.crolemol.coc.arena.events.BuildingInteractEvent;
import me.crolemol.coc.army.troops.Soldier;
import me.crolemol.coc.army.troops.troops.Barbarian;
import me.crolemol.coc.economy.PlayerData;
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
	private boolean dostaticbuildingremove = true;
	@EventHandler
	private void onInventoryClick(InventoryClickEvent event){
		if(event.getInventory().getName().equals("Goldmine")){
			GoldminePanelClick(event);
		}else if(event.getInventory().getName().equals("Elixir Collector")){
			ElixirCollectorPanelClick(event);
		}else if(event.getInventory().getName().equals("Dark Elixir Drill")){
			DarkElixirDrillPanelClick(event);
		}else if(event.getInventory().getName().equals("Barracks")){
			BarracksPanelClick(event);
		}else if(event.getInventory().getName().equals("Train Troops")){
			TrainTroopsPanelClick(event);
		}else{
			Player player = (Player) event.getWhoClicked();
			if(staticbuilding.get(player.getUniqueId().toString()) == null){return;}
			normalBuildingPanelClick(event);
		}
			
		
	}
	
	private void GoldminePanelClick(InventoryClickEvent event){
		Player player = (Player) event.getWhoClicked();
		if(staticbuilding.get(player.getUniqueId().toString()) == null){return;}
		Goldmine building = (Goldmine) staticbuilding.get(player.getUniqueId().toString());
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
			if(building.isUpgrading() == false && PlayerData.getElixir(player) >= spec[building.getLevel()].getUpgradePrice().getAmount()){
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
			Long cal2 = building.getUpgradeTimeRemain();
			Long time1 = PanelUtils.timeBetweenDates(cal2, caltime);
			int time2 = spec2[building.getLevel()].getUpgradeTime();
			Long time3 = time2 - time1;
			TimetoGemCalc calc = new TimetoGemCalc();
			PlayerData.takeGems((Player)event.getWhoClicked(), calc.Calc(time3*60));
			ub.FinishUpgrade(building);
			event.getWhoClicked().closeInventory();
			break;
		default: player.closeInventory();
			
		}
		
	}
	
	private void ElixirCollectorPanelClick(InventoryClickEvent event){
		Player player = (Player) event.getWhoClicked();
		if(staticbuilding.get(player.getUniqueId().toString()) == null){return;}		
		ElixirCollector building = (ElixirCollector) staticbuilding.get(player.getUniqueId().toString());
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
			if(building.isUpgrading() == false && PlayerData.getGold(player) >= spec[building.getLevel()].getUpgradePrice().getAmount()){
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
			Long cal2 = building.getUpgradeTimeRemain();
			Long time1 = PanelUtils.timeBetweenDates(cal2, caltime);
			int time2 = spec2[building.getLevel()].getUpgradeTime();
			Long time3 = time2 - time1;
			TimetoGemCalc calc = new TimetoGemCalc();
			PlayerData.takeGems((Player)event.getWhoClicked(), calc.Calc(time3*60));
			ub.FinishUpgrade(building);
			event.getWhoClicked().closeInventory();
			break;
		default: player.closeInventory();
			
		}
		
		
	}
	
	private void DarkElixirDrillPanelClick(InventoryClickEvent event){
		Player player = (Player) event.getWhoClicked();
		if(staticbuilding.get(player.getUniqueId().toString()) == null){return;}	
		DarkElixirDrill building = (DarkElixirDrill) staticbuilding.get(player.getUniqueId().toString());
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
			if(building.isUpgrading() == false && PlayerData.getElixir(player) >= spec[building.getLevel()].getUpgradePrice().getAmount()){
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
			Long cal2 = building.getUpgradeTimeRemain();
			Long time1 = PanelUtils.timeBetweenDates(cal2, caltime);
			int time2 = spec2[building.getLevel()].getUpgradeTime();
			Long time3 = time2 - time1;
			TimetoGemCalc calc = new TimetoGemCalc();
			PlayerData.takeGems((Player)event.getWhoClicked(), calc.Calc(time3*60));
			ub.FinishUpgrade(building);
			event.getWhoClicked().closeInventory();
			break;
		default: player.closeInventory();
			
		}		
	}
	
	private void BarracksPanelClick(InventoryClickEvent event){
		Player player = (Player) event.getWhoClicked();
		if(staticbuilding.get(player.getUniqueId().toString()) == null){return;}	
		Barracks building = (Barracks) staticbuilding.get(player.getUniqueId().toString());
		event.setCancelled(true);
		if(event.getCurrentItem()==null || !event.getCurrentItem().hasItemMeta()){
			return;
		}
		switch(event.getCurrentItem().getType()){
		case BOOK_AND_QUILL:
			break;
		case IRON_PICKAXE:
			BuildingSpecs[] spec = building.getBuildingSpecs();
			if(building.isUpgrading() == false && PlayerData.getElixir(player) >= spec[building.getLevel()].getUpgradePrice().getAmount()){
			UpgradeBuilding upgrade = new UpgradeBuilding();
			upgrade.startNewUpgrade(building);

			event.getWhoClicked().closeInventory();}
			break;
		case EMERALD:
			TimetoGemCalc calc = new TimetoGemCalc();
			PlayerData.takeGems(player, calc.Calc(building.getUpgradeTimeRemain()*60));
			UpgradeBuilding ub = new UpgradeBuilding();
			ub.FinishUpgrade(building);
			event.getWhoClicked().closeInventory();
			break;
		case STONE_SWORD:
			TrainTroopsPanel panel = new TrainTroopsPanel(building);
			this.dostaticbuildingremove  = false;
			event.getWhoClicked().openInventory(panel.getInventory());
			break;
		default: player.closeInventory();
			
			}
	}
	private void TrainTroopsPanelClick(InventoryClickEvent event){
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		if(staticbuilding.get(player.getUniqueId().toString()) == null){return;}
		if(event.getCurrentItem()==null || !event.getCurrentItem().hasItemMeta()){
			return;}
		Barracks building = (Barracks) staticbuilding.get(player.getUniqueId().toString());

		switch(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())){
		case "Barbarian":
			Soldier soldier = Barbarian.spawnBarbarian(building.getLocation().add(3, 1, 0), player, 1);
			ArmyCamp armycamp = ArmyCamp.getArmyCamp(1, player);
			soldier.getNPC().lookAt(armycamp.getLocation());
			soldier.addToArmyCamps();
			break;
		default:
			player.closeInventory();
			break;
		}
	}
	
	private void normalBuildingPanelClick(InventoryClickEvent event){
		Player player = (Player) event.getWhoClicked();
		Building building = staticbuilding.get(player.getUniqueId().toString());
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
			if(building.isUpgrading() == false && PlayerData.getResource(spec[building.getLevel()].getUpgradePrice().getResourceType(), player) >= spec[building.getLevel()].getUpgradePrice().getAmount()){
				if(townhall.getLevel() >= spec[building.getLevel()].getMinTownhallLevel()){
					UpgradeBuilding upgrade = new UpgradeBuilding();
					upgrade.startNewUpgrade(building);
					event.getWhoClicked().closeInventory();
						}else{
							player.sendMessage(ChatColor.RED+"[ClashofClans] You need to upgrade your Townhall to upgrade this building!");
						}
			event.getWhoClicked().closeInventory();}
			break;
		case EMERALD:
			Calendar cal = Calendar.getInstance();
			BuildingSpecs[] spec2 = building.getBuildingSpecs();
			Long caltime = cal.getTimeInMillis()/60/1000;
			Long cal2 = building.getUpgradeTimeRemain();
			Long time1 = PanelUtils.timeBetweenDates(cal2, caltime);
			int time2 = spec2[building.getLevel()].getUpgradeTime();
			Long time3 = time2 - time1;
			TimetoGemCalc calc = new TimetoGemCalc();
			PlayerData.takeGems(player, calc.Calc(time3*60));
			UpgradeBuilding ub = new UpgradeBuilding();
			ub.FinishUpgrade(building);
			event.getWhoClicked().closeInventory();
			break;
		default: player.closeInventory();
	}
		}
	@EventHandler
	private void onInventoryClose(InventoryCloseEvent event){
		if(dostaticbuildingremove == true){
		staticbuilding.remove(event.getPlayer().getUniqueId().toString());
		}else{
			this.dostaticbuildingremove = true;
		}
	}
	@EventHandler
	private void onBuildingInteract(BuildingInteractEvent event){
		staticbuilding.put(event.getPlayer().getUniqueId().toString(), event.getBuilding());
	}
}
