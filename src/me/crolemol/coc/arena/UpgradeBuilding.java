package me.crolemol.coc.arena;

import java.io.IOException;
import java.util.Calendar;

import me.crolemol.coc.Coc;
import me.crolemol.coc.arena.panels.Specs;
import me.crolemol.coc.arena.panels.Specs.specsTownhall;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class UpgradeBuilding{
	Coc plugin  = Coc.getPlugin();
	public void startNewUpgrade(final String BuildingName,final String buildingID, final Player BuildingOwner){
		final FileConfiguration dataconf = plugin.getdataconffile(BuildingOwner);
		final specsTownhall[] spec = Specs.specsTownhall.values();
		Calendar cal = Calendar.getInstance();
		dataconf.set(BuildingName+"."+buildingID+".upgrade", cal.getTimeInMillis()/60/1000);
		try {
			dataconf.save(plugin.getdatafile(BuildingOwner));
		} catch (IOException e) {
			e.printStackTrace();
		}
		new BukkitRunnable(){

			@Override
			public void run() {
				Calendar cal = Calendar.getInstance();
				Long caltime = cal.getTimeInMillis()/60/1000;
				Long cal2 = dataconf.getLong(BuildingName+"."+buildingID+".upgrade");
				Long time1 = timeBetweenDates(cal2, caltime);
				int time2 = spec[dataconf.getInt(BuildingName+"."+buildingID+".level")].getUpgradeTime();
				Long time3 = time2 - time1;
				if(time3 <=0){
					FinishUpgrade(BuildingName, buildingID, BuildingOwner);
					this.cancel();
				}
				
			}
			}.runTaskTimer(plugin, 0, 1200L);
	}
	public void FinishUpgrade(String BuildingName,String buildingID, Player BuildingOwner){
		FileConfiguration dataconf = plugin.getdataconffile(BuildingOwner);
		dataconf.set(BuildingName+"."+buildingID+".level", dataconf.getInt(BuildingName+"."+buildingID+".level")+1);
		dataconf.set(BuildingName+"."+buildingID+".upgrade", null);
		try {
			dataconf.save(plugin.getdatafile(BuildingOwner));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Base base = new Base();
		base.Rebuild(BuildingOwner);
	}
	private Long timeBetweenDates(Long earliestDate,Long latestDate){
		Long difference = latestDate - earliestDate;
		return difference;
	}


}
