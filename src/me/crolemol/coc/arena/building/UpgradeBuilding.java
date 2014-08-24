package me.crolemol.coc.arena.building;

import java.util.Calendar;

import me.crolemol.coc.Coc;
import me.crolemol.coc.arena.Base;
import me.crolemol.coc.arena.building.interfaces.Building;
import me.crolemol.coc.arena.building.interfaces.BuildingSpecs;
import me.crolemol.coc.arena.building.interfaces.ResourceBuilding;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class UpgradeBuilding{
	Coc plugin  = Coc.getPlugin();
	public void startNewUpgrade(final Building building){
		final String BuildingName = building.getBuildingName();
		final int BuildingID = building.getBuildingID();
		OfflinePlayer BuildingOwner = building.getOwner();
		final FileConfiguration dataconf = plugin.getdataconffile(BuildingOwner);
		
		Calendar cal = Calendar.getInstance();
		dataconf.set(BuildingName+"."+BuildingID+".upgrade", cal.getTimeInMillis()/60/1000);
		new BukkitRunnable(){

			@Override
			public void run() {	
				final BuildingSpecs[] spec = building.getBuildingSpecs();
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
		OfflinePlayer BuildingOwner = building.getOwner();
		String BuildingName = building.getBuildingName();
		int buildingID = building.getBuildingID();
		FileConfiguration dataconf = plugin.getdataconffile(BuildingOwner);
		dataconf.set(BuildingName+"."+buildingID+".level", dataconf.getInt(BuildingName+"."+buildingID+".level")+1);
		dataconf.set(BuildingName+"."+buildingID+".upgrade", null);
		if (building instanceof ResourceBuilding){
			((Player)building.getOwner()).sendMessage(building+"");
			((ResourceBuilding) building).setCollectable(0);
		}
		plugin.saveDataconf(building.getOwner());
		Base base = Base.getBase(BuildingOwner);
		base.Rebuild();
	}
	private Long timeBetweenDates(Long earliestDate,Long latestDate){
		Long difference = latestDate - earliestDate;
		return difference;
	}


}
