package me.crolemol.coc.arena.building;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import me.crolemol.coc.Coc;
import me.crolemol.coc.arena.Base;
import me.crolemol.coc.arena.building.interfaces.Building;
import me.crolemol.coc.arena.building.interfaces.BuildingSpecs;
import me.crolemol.coc.arena.building.interfaces.ResourceBuilding;
import me.crolemol.coc.economy.DarkElixir;
import me.crolemol.coc.economy.Elixir;
import me.crolemol.coc.economy.Gold;
import me.crolemol.coc.economy.PlayerData;

import org.bukkit.OfflinePlayer;
import org.bukkit.scheduler.BukkitRunnable;

public class UpgradeBuilding {
	Coc plugin = Coc.getPlugin();

	public void startNewUpgrade(final Building building){
		final BuildingSpecs[] spec = building.getBuildingSpecs();
		final int BuildingID = building.getBuildingID();
		
		if(spec[building.getLevel()].getUpgradePrice() instanceof Gold){
		if(	PlayerData.getGold(building.getOwner()) < spec[building.getLevel()].getUpgradePrice().getAmount()){
			return;
		}
		}else if(spec[building.getLevel()].getUpgradePrice() instanceof Elixir){
			if(	PlayerData.getElixir(building.getOwner()) < spec[building.getLevel()].getUpgradePrice().getAmount()){
				return;
			}
		}else if(spec[building.getLevel()].getUpgradePrice() instanceof DarkElixir){
			if(	PlayerData.getElixir(building.getOwner()) < spec[building.getLevel()].getUpgradePrice().getAmount()){
				return;
			}
		}
		Calendar cal = Calendar.getInstance();
		plugin.getDataBase().query("UPDATE Buildings SET Upgrade="+(cal.getTimeInMillis()/60/1000)+" WHERE owner = '"
						+ building.getOwner().getUniqueId()
						+ "' AND BuildingID = "+ BuildingID
						+ " AND BuildingName = '"+building.getBuildingName()+"'");
		PlayerData.Take(spec[building.getLevel()].getUpgradePrice(), building.getOwner());
		try {
			plugin.getDataBase().getConnection().commit();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		new BukkitRunnable(){

			@Override
			public void run() {	
				Calendar cal = Calendar.getInstance();
				Long caltime = cal.getTimeInMillis()/60/1000;
				ResultSet result = plugin.getDataBase().query("SELECT Upgrade FROM Buildings WHERE owner = '"
						+ building.getOwner().getUniqueId()
						+ "' AND BuildingID = "+ BuildingID
						+ " AND BuildingName = '"+building.getBuildingName()+"'");
				Long cal2 = (long) 0;
				try {
					cal2 = result.getLong("Upgrade");
				} catch (SQLException e) {
					e.printStackTrace();
				}
				Long time1 = timeBetweenDates(cal2, caltime);
				int time2 = spec[building.getLevel()].getUpgradeTime();
				Long time3 = time2 - time1;
				if(time3 <=0){
					FinishUpgrade(building);
					this.cancel();
				}
				
			}
			}.runTaskTimer(plugin, 0, 1200L);
	}

	public void FinishUpgrade(Building building) {
		if (building.isUpgrading() == false) {
			return;
		}
		OfflinePlayer BuildingOwner = building.getOwner();
		plugin.getDataBase().query("UPDATE Buildings SET Upgrade = NULL WHERE owner = '"
				+ building.getOwner().getUniqueId()
				+ "' AND BuildingID = "+ building.getBuildingID()
				+ " AND BuildingName = '"+building.getBuildingName()+"'");
		building.setLevel(building.getLevel() + 1);
		if (building instanceof ResourceBuilding) {
			((ResourceBuilding) building).setCollectable(0);
		}
		Base base = Base.getBase(BuildingOwner);
		base.Rebuild();
		try {
			plugin.getDataBase().getConnection().commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private Long timeBetweenDates(Long earliestDate, Long latestDate) {
		Long difference = latestDate - earliestDate;
		return difference;
	}

}
