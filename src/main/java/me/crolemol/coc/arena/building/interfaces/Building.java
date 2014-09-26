package me.crolemol.coc.arena.building.interfaces;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import me.crolemol.coc.Coc;
import me.crolemol.coc.utils.PanelUtils;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;


public abstract class Building{
	private int level;
	private int buildingID;
	private boolean isRealBuilding;
	private Location loc;
	private OfflinePlayer owner;
	
	public Building(int level){
		this.level = level;
		this.buildingID = 0;
		this.isRealBuilding = false;
	}
	protected Building(OfflinePlayer owner,Location loc,int level,int BuildingID,boolean isreal){
		this.owner = owner;
		this.loc = loc;
		this.level = level;
		this.buildingID = BuildingID;
		this.isRealBuilding = isreal;
	}
	
	public int getBuildingID(){
		return this.buildingID;
	}
	public int getLevel(){
		return this.level;
	}
	public Location getLocation(){
		return this.loc;
	}
	public OfflinePlayer getOwner(){
		return this.owner;
	}
	public abstract String getBuildingName();
	public abstract BuildingSpecs[] getBuildingSpecs();
	public void setLevel(int level){
		this.level = level;
		if(isRealBuilding() == false){return;}
		Coc.getPlugin().getDataBase().query("UPDATE Buildings SET Level="+level+" WHERE owner = '"
						+ owner.getUniqueId()
						+ "' AND BuildingID = "+ buildingID
						+ " AND BuildingName = '"+getBuildingName()+"'");
	}
	public void setLocation(Location location){
		this.loc = location;
		if(isRealBuilding() == false){return;}
		Coc.getPlugin().getDataBase().query("UPDATE Buildings SET Location_x="+location.getBlockX()+",Location_y=65,Location_z="+location.getBlockZ()+" WHERE owner = '"
						+ owner.getUniqueId()
						+ "' AND BuildingID = "+ buildingID
						+ " AND BuildingName = '"+getBuildingName()+"'");
	}
	public abstract BuildingPanel getBuildingPanel();
	public boolean isUpgrading(){
		ResultSet result = Coc.getPlugin().getDataBase().query("SELECT Upgrade FROM Buildings WHERE owner = '"
						+ owner.getUniqueId()
						+ "' AND BuildingID = "+ buildingID
						+ " AND BuildingName = '"+getBuildingName()+"'");
		try {
			if(result.getObject("Upgrade") != null){
				return true;
			}else{
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	public boolean isRealBuilding(){
		return this.isRealBuilding;
	}
	public Long getUpgradeTimeRemain(){
		if(isUpgrading() == false){
			return (long) 0;
		}
		Calendar cal = Calendar.getInstance();
		Long caltime = cal.getTimeInMillis()/60/1000;
		ResultSet result = Coc.getPlugin().getDataBase().query("SELECT Upgrade FROM Buildings WHERE owner = '"
				+ getOwner().getUniqueId()
				+ "' AND BuildingID = "+ getBuildingID()
				+ " AND BuildingName = '"+getBuildingName()+"'");
		Long cal2 = (long) 0;
		try {
			cal2 = result.getLong("Upgrade");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Long time1 = PanelUtils.timeBetweenDates(cal2, caltime);
		int time2 = getBuildingSpecs()[getLevel()].getUpgradeTime();
		Long time3 = time2 - time1;
		return time3;
	}
}
