package me.crolemol.coc.arena.events;

import me.crolemol.coc.arena.building.interfaces.Building;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BuildingInteractEvent extends Event {
	private static final HandlerList handlers = new HandlerList();
	Building building2;
	public BuildingInteractEvent(Building building){
		building2 = building;
	}
	 public Building getBuilding(){
		 return building2;
	 }

	public HandlerList getHandlers() {
	    return handlers;
	}
	 
	public static HandlerList getHandlerList() {
	    return handlers;
	}
}
