package me.crolemol.coc.arena;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import me.crolemol.coc.Coc;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.data.DataException;

public class RelativeBuilding implements Listener{
	Coc plugin = Coc.getPlugin();
	static Map<String,String> BuildingName2 = new HashMap<>();
	static Map<String,Integer> level2 = new HashMap<>();
	static Map<String,Location> target= new HashMap<>();
	@SuppressWarnings("deprecation")
	public void pasteRelativeBuilding(String BuildingName,int level,Player paster){
		
		CuboidClipboard cc=null;
		try {
			cc = CuboidClipboard.loadSchematic(new File(plugin.getDataFolder()+"/schematics/"+BuildingName+"/"+BuildingName+"_lv"+level+".schematic"));
		} catch (DataException | IOException e) {
			e.printStackTrace();
		}
		for(int xcounter=0;xcounter<cc.getWidth();xcounter++){
			for(int zcounter=0;zcounter<cc.getLength();zcounter++){
				for(int ycounter=0;ycounter<cc.getHeight();ycounter++){
					Location loc = new Location(plugin.getServer().getWorld("coc"),paster.getTargetBlock(null, 10).getLocation().getBlockX()+xcounter,paster.getTargetBlock(null, 10).getLocation().getBlockY()+ycounter,paster.getTargetBlock(null, 10).getLocation().getBlockZ()+zcounter);
					Vector vec = new Vector(xcounter, ycounter, zcounter);
					int materialid = cc.getBlock(vec).getId();
					Material material = Material.getMaterial(materialid);
					paster.sendBlockChange(loc, material, (byte) 0);
				}
			}
		}
		BuildingName2.put(paster.getName(), BuildingName);
		level2.put(paster.getName(), level);
		target.put(paster.getName(), paster.getTargetBlock(null, 10).getLocation());
		
	}
	@SuppressWarnings("deprecation")
	public void removeRelativeBuilding(Player player){
		if(!BuildingName2.containsKey(player.getName())){return;}
		if(!level2.containsKey(player.getPlayer().getName())){return;}
		if(!target.containsKey(player.getPlayer().getName())){return;}
		CuboidClipboard cc=null;
		try {
			cc = CuboidClipboard.loadSchematic(new File(plugin.getDataFolder()+"/schematics/"+BuildingName2.get(player.getName())+"/"+BuildingName2.get(player.getName())+"_lv"+level2.get(player.getName())+".schematic"));
		} catch (DataException | IOException e) {
			e.printStackTrace();
		}
		for(int xcounter=0;xcounter<cc.getWidth();xcounter++){
			for(int zcounter=0;zcounter<cc.getLength();zcounter++){
				for(int ycounter=0;ycounter<cc.getHeight();ycounter++){
					Location loc = new Location(plugin.getServer().getWorld("coc"),target.get(player.getName()).getBlockX()+xcounter,target.get(player.getName()).getBlockY()+ycounter,target.get(player.getName()).getBlockZ()+zcounter);
					Block block = loc.getBlock();
					Material material = block.getType();
					player.sendBlockChange(loc, material, (byte) 0);
				}
			}
		}
	}
	@EventHandler
	private void onplayermove(PlayerMoveEvent event){
		if(!event.getPlayer().getWorld().equals(plugin.getServer().getWorld("coc"))){return;}
		if(!BuildingName2.containsKey(event.getPlayer().getName())){return;}
		if(!level2.containsKey(event.getPlayer().getName())){return;}
		removeRelativeBuilding(event.getPlayer());
		pasteRelativeBuilding(BuildingName2.get(event.getPlayer().getName()),level2.get(event.getPlayer().getName()),event.getPlayer());
		}
}
