package me.crolemol.coc.arena.building;

import java.util.Calendar;

import me.crolemol.coc.Coc;
import me.crolemol.coc.arena.Base;
import me.crolemol.coc.arena.building.interfaces.Building;
import me.crolemol.coc.arena.building.interfaces.BuildingSpecs;
import me.crolemol.coc.arena.building.interfaces.ResourceBuilding;
import me.crolemol.coc.economy.DarkElixir;
import me.crolemol.coc.economy.Elixir;
import me.crolemol.coc.economy.Gold;
import me.crolemol.coc.economy.Resources;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class UpgradeBuilding{
	Coc plugin  = Coc.getPlugin();
	public void startNewUpgrade(final Building building){
		((Player) building.getOwner()).sendMessage("check 1");
		final String BuildingName = building.getBuildingName();
		final BuildingSpecs[] spec = building.getBuildingSpecs();
		final int BuildingID = building.getBuildingID();
		OfflinePlayer BuildingOwner = building.getOwner();
		final FileConfiguration dataconf = plugin.getdataconffile(BuildingOwner);
		
		if(spec[building.getLevel()].getUpgradePrice() instanceof Gold){
		if(	Resources.getGold(building.getOwner()) < spec[building.getLevel()].getUpgradePrice().getAmount()){
			return;
		}
		}else if(spec[building.getLevel()].getUpgradePrice() instanceof Elixir){
			if(	Resources.getElixir(building.getOwner()) < spec[building.getLevel()].getUpgradePrice().getAmount()){
				return;
			}
		}else if(spec[building.getLevel()].getUpgradePrice() instanceof DarkElixir){
			if(	Resources.getElixir(building.getOwner()) < spec[building.getLevel()].getUpgradePrice().getAmount()){
				return;
			}
		}
		Calendar cal = Calendar.getInstance();
		dataconf.set(BuildingName+"."+BuildingID+".upgrade", cal.getTimeInMillis()/60/1000);
		Resources.Take(spec[building.getLevel()].getUpgradePrice(), building.getOwner());
		new BukkitRunnable(){

			@Override
			public void run() {	
				Calendar cal = Calendar.getInstance();
				Long caltime = cal.getTimeInMillis()/60/1000;
				Long cal2 = dataconf.getLong(BuildingName+"."+BuildingID+".upgrade");
				Long time1 = timeBetweenDates(cal2, caltime);
				int time2 = spec[dataconf.getInt(BuildingName+"."+BuildingID+".level")].getUpgradeTime();
				Long time3 = time2 - time1;
				if(time3 <=0){
					FinishUpgrade(building);
					this.cancel();
				}
				
			}
			}.runTaskTimer(plugin, 0, 1200L);
	}
	public void FinishUpgrade(Building building){
		if(building.isUpgrading() == false){return;}
		OfflinePlayer BuildingOwner = building.getOwner();
		String BuildingName = building.getBuildingName();
		int buildingID = building.getBuildingID();
		FileConfiguration dataconf = plugin.getdataconffile(BuildingOwner);
		dataconf.set(BuildingName+"."+buildingID+".upgrade", null);
		building.setLevel(building.getLevel()+1);
		if (building instanceof ResourceBuilding){
			((ResourceBuilding) building).setCollectable(0);
		}
		((Player) building.getOwner()).sendMessage(building.getBuildingID()+"");
		plugin.saveDataconf(building.getOwner());
		Base base = Base.getBase(BuildingOwner);
		base.Rebuild();
	}
	private Long timeBetweenDates(Long earliestDate,Long latestDate){
		Long difference = latestDate - earliestDate;
		return difference;
	}


}
