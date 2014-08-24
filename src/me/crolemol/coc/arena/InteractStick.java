package me.crolemol.coc.arena;

import java.util.ArrayList;
import java.util.List;

import me.crolemol.coc.Coc;
import me.crolemol.coc.arena.events.BuildingInteractEvent;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InteractStick implements Listener{
	Coc plugin = Coc.getPlugin();
	public static void getInteractStick(Player player){
		ItemStack InteractStick = new ItemStack(Material.STICK);
		ItemMeta InteractStickMeta = InteractStick.getItemMeta();
		InteractStickMeta.setDisplayName("InteractStick");
		List<String> list = new ArrayList<String>();
		list.add("Click on a building with this stick");
		list.add(" to open the building's panel");
		InteractStickMeta.setLore(list);
		InteractStick.setItemMeta(InteractStickMeta);
		player.getInventory().setItem(0, InteractStick);
	}
	private void Interacted(Location targetblock,Player player){
		Base arena = Base.getBase(player);
		List<String> contains = arena.containsbuildings();
		FileConfiguration dataconf = plugin.getdataconffile(player);
		for(Buildingspecs building : Buildingspecs.values()){
			if(contains.contains(building.getName())){
				for(int counter=1;counter<=arena.getAmountofBuilding(building.getName());counter++){
				Location loc1 = new Location(plugin.getServer().getWorld("coc"), dataconf.getInt(building.getName()+"."+counter+".location.x"), dataconf.getInt(building.getName()+"."+counter+".location.y"), dataconf.getInt(building.getName()+"."+counter+".location.z"));
				Location loc2 = new Location(plugin.getServer().getWorld("coc"), loc1.getBlockX()+building.getWidth(), loc1.getBlockY(), loc1.getBlockZ()+building.getLength());
				if(checkIfInArea(loc1,loc2,targetblock) == true){
					BuildingInteractEvent event = new BuildingInteractEvent(arena.getBuilding(building.getName(), counter, player));
					Bukkit.getServer().getPluginManager().callEvent(event);
					return;
				}
				}
			}
		}
	}
	@EventHandler
	public void onInteract(PlayerInteractEvent event){
		Action action = event.getAction();
		ItemStack is= event.getItem();
		
		if(action== Action.PHYSICAL || is == null || is.getType().equals(Material.AIR)){
			return;
		}
		if(!(event.getPlayer().getWorld().equals(plugin.getServer().getWorld("coc")))){
			return;
		}
		if(!(is.hasItemMeta())){
			return;
		}
	if(is.getType() == Material.STICK && is.getItemMeta().getDisplayName().equals("InteractStick")){
		if(!(event.getAction() == Action.LEFT_CLICK_BLOCK) && (!(event.getAction() == Action.RIGHT_CLICK_BLOCK))){
			return;
		}
		InteractStick stick = new InteractStick();
		stick.Interacted(event.getClickedBlock().getLocation(), event.getPlayer());
		return;
		}
	}
	
	
	private boolean checkIfInArea(Location corner1, Location corner2, Location Pos){
	    if(Pos.getBlockX() >= corner1.getBlockX() && Pos.getBlockX() <= corner2.getBlockX()){
	        if(Pos.getBlockZ() >= corner1.getBlockZ() && Pos.getBlockZ() <= corner2.getBlockZ()){
	            return true;
	        }
	    }
	    return false;
	}

}
