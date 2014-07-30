package me.crolemol.coc.arena;

import java.util.ArrayList;
import java.util.List;

import me.crolemol.coc.Coc;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InteractStick {
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
	public void Interacted(Location targetblock,Player player){
		String[] contains = containsbuildings(player);
		
	}
	
	private String[] containsbuildings(Player player){
		int counter = 1;
		String[] contains = new String[counter];
		FileConfiguration dataconf = Coc.getPlugin().getdataconffile(player);
		contains[counter -1] = "townhall";
		return contains;
	}
	
	
	private boolean checkIfInArea(Location corner1, Location corner2, Location PlayerPos){
	    if(PlayerPos.getBlockX() >= corner2.getBlockX() && PlayerPos.getBlockX() <= corner1.getBlockX()){
	        if(PlayerPos.getBlockZ() >= corner2.getBlockZ() && PlayerPos.getBlockZ() <= corner1.getBlockZ()){
	            return true;
	        }
	    }
	    return false;
	}
	private enum Buildinglengths{
		townhall(12,10);
		private int returnlength;
		private int returnwidth;
		Buildinglengths(int length,int width){
			returnlength = length;
			returnwidth = width;
		}
		private int getLength(){
			return returnlength;
		}
		private int getWidth(){
			return returnwidth;
		}
	}
}
