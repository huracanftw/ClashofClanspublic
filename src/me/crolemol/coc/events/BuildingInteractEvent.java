package me.crolemol.coc.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BuildingInteractEvent extends Event {
	private static final HandlerList handlers = new HandlerList();
	String BuildingName2;
	int BuildingNumber;
	Player whoclicked2;
	public BuildingInteractEvent(Object BuildingName, Object BuildingID, Player whoclicked){
		if(BuildingName instanceof String){
		BuildingName2 = (String) BuildingName;
		}else{
			throw new IllegalArgumentException("Buildingname must be a string");
		}
		if(BuildingID instanceof Integer){
			BuildingNumber = (Integer) BuildingID;
		}else{
			throw new IllegalArgumentException("BuildingID must be an Integer");
		}
		whoclicked2 = whoclicked;
	}
	 

	public String getBuildingName(){
		return BuildingName2;
	}
	public int getBuildingID(){
		return BuildingNumber;
	}
	public Player getWhoClicked(){
		return whoclicked2;
	}
	public HandlerList getHandlers() {
	    return handlers;
	}
	 
	public static HandlerList getHandlerList() {
	    return handlers;
	}
}
