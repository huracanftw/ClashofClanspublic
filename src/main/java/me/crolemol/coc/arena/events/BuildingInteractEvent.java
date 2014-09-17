package me.crolemol.coc.arena.events;

import me.crolemol.coc.arena.building.interfaces.Building;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BuildingInteractEvent extends Event {
	private static final HandlerList handlers = new HandlerList();
	Building building2;
	Player player;
	public BuildingInteractEvent(Building building,Player player){
		building2 = building;
		this.player = player;
	}
	 public Building getBuilding(){
		 return building2;
	 }
	 public Player getPlayer(){
		 return player;
	 }
	public HandlerList getHandlers() {
	    return handlers;
	}
	 
	public static HandlerList getHandlerList() {
	    return handlers;
	}
}
