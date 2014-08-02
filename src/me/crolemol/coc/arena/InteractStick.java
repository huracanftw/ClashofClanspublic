package me.crolemol.coc.arena;

import java.util.ArrayList;
import java.util.List;

import me.crolemol.coc.Coc;
import me.crolemol.coc.arena.Base.Buildingspecs;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InteractStick {
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
	public String[] Interacted(Location targetblock,Player player){
		Base arena = new Base();
		List<String> contains = arena.containsbuildings(player);
		FileConfiguration dataconf = plugin.getdataconffile(player);
		for(Buildingspecs building : Buildingspecs.values()){
			if(contains.contains(building.getName())){
				for(int counter=1;counter<=arena.getAmountofBuilding(building.getName(),player);counter++){
				Location loc1 = new Location(plugin.getServer().getWorld("coc"), dataconf.getInt(building.getName()+"."+counter+".location.x"), dataconf.getInt(building.getName()+"."+counter+".location.y"), dataconf.getInt(building.getName()+"."+counter+".location.z"));
				Location loc2 = new Location(plugin.getServer().getWorld("coc"), loc1.getBlockX()+building.getLength(), loc1.getBlockY(), loc1.getBlockZ()+building.getWidth());
				if(checkIfInArea(loc1,loc2,targetblock) == true){
					String[] NameAndId = new String[2];
					NameAndId[0] = building.getName();
					NameAndId[1] = counter+"";
					return NameAndId;
					}
				}
			}
		}
		// returns none and 0 when the block is not an instance of a building
		String[] None = new String[2];
		None[0] = "none";
		None[1] = "0";
		return None;
		
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
