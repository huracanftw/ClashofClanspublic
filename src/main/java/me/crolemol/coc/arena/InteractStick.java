package me.crolemol.coc.arena;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import me.crolemol.coc.Coc;
import me.crolemol.coc.arena.events.BuildingInteractEvent;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
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
		if(arena.containsbuildings() == null){return;}
		List<String> contains = arena.containsbuildings();
		for(BuildingType building : BuildingType.values()){
			if(contains.contains(building.getName())){
				for(int counter=1;counter<=arena.getAmountofBuilding(building.getName());counter++){
					int x = 0;
					int y = 0;
					int z = 0;
					try {
						ResultSet result = plugin
								.getDataBase()
								.query("SELECT Location_x,Location_y,Location_z FROM Buildings WHERE owner = '"
										+ player.getUniqueId()
										+ "' AND BuildingID = "+ counter
										+ " AND BuildingName = '"+building.getName()+"'");
						x = result.getInt("Location_x");
						y = result.getInt("Location_y");
						z = result.getInt("Location_z");
					} catch (SQLException e) {
						e.printStackTrace();
					}
				Location loc1 = new Location(plugin.getServer().getWorld("coc"), x, y, z);
				Location loc2 = new Location(plugin.getServer().getWorld("coc"), loc1.getBlockX()+building.getWidth(), loc1.getBlockY(), loc1.getBlockZ()+building.getLength());
				if(checkIfInArea(loc1,loc2,targetblock) == true){
					BuildingInteractEvent event = new BuildingInteractEvent(arena.getBuilding(building.getName(), counter, player),player);
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
