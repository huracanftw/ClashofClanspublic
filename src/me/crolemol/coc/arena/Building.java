package me.crolemol.coc.arena;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import me.crolemol.coc.Coc;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.data.DataException;

public class Building implements Listener{
	Coc plugin = Coc.getPlugin();
	private static Map<String,String> BuildingName2 = new HashMap<>();
	private static Map<String,Integer> level2 = new HashMap<>();
	private static Map<String,Location> target= new HashMap<>();
	
	public void placeBuilding(Player player,Location targetlocation, String BuildingName,int level){
		FileConfiguration dataconf = Coc.getdataconffile(player);
		int counter=1;
		for(Boolean test=false;test==false;){
		if(!dataconf.contains(BuildingName+"."+counter)){
			test = true;
			break;
		}else{
			counter++;
		}
		}
		dataconf.set(BuildingName+"."+counter+".location.x", targetlocation.getBlockX());
		dataconf.set(BuildingName+"."+counter+".location.y", targetlocation.getBlockY()+1);
		dataconf.set(BuildingName+"."+counter+".location.z", targetlocation.getBlockZ());
		dataconf.set(BuildingName+"."+counter+".level", level);
		try {
			dataconf.save(Coc.getdatafile(player));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Base base = new Base();
		base.Rebuild(player);

	}

	@SuppressWarnings("deprecation")
	public void placeRelativeBuilding(String BuildingName,int level,Player paster){
		
		CuboidClipboard cc=null;
		try {
			cc = CuboidClipboard.loadSchematic(new File(plugin.getDataFolder()+"/schematics/"+BuildingName+"/"+BuildingName+"_lv"+level+".schematic"));
		} catch (DataException | IOException e) {
			e.printStackTrace();
		}
		for(int xcounter=0;xcounter<cc.getWidth();xcounter++){
			for(int zcounter=0;zcounter<cc.getLength();zcounter++){
				for(int ycounter=0;ycounter<cc.getHeight();ycounter++){
					Location loc = new Location(plugin.getServer().getWorld("coc"),paster.getTargetBlock(null, 10).getLocation().getBlockX()+xcounter,paster.getTargetBlock(null, 10).getLocation().getBlockY()+ycounter+cc.getOffset().getBlockY()+1,paster.getTargetBlock(null, 10).getLocation().getBlockZ()+zcounter);
					Vector vec = new Vector(xcounter, ycounter, zcounter);
					BaseBlock block = cc.getBlock(vec);
					int materialid = block.getId();
					Material material = Material.getMaterial(materialid);
					paster.sendBlockChange(loc, material, (byte) block.getData());
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
					Location loc = new Location(plugin.getServer().getWorld("coc"),target.get(player.getName()).getBlockX()+xcounter,target.get(player.getName()).getBlockY()+ycounter+cc.getOffset().getBlockY()+1,target.get(player.getName()).getBlockZ()+zcounter);
					Block block = loc.getBlock();
					Material material = block.getType();
					player.sendBlockChange(loc, material, loc.getBlock().getData());
				}
			}
		}
		BuildingName2.remove(player.getName());
		level2.remove(player.getName());
	}
	@SuppressWarnings("deprecation")
	public void moveRelativeBuilding(Player player){
		if(!BuildingName2.containsKey(player.getName())){return;}
		if(!level2.containsKey(player.getPlayer().getName())){return;}
		if(!target.containsKey(player.getPlayer().getName())){return;}
		if(!target.get(player.getName()).equals(player.getTargetBlock(null, 10))){
		CuboidClipboard cc=null;
		try {
			cc = CuboidClipboard.loadSchematic(new File(plugin.getDataFolder()+"/schematics/"+BuildingName2.get(player.getName())+"/"+BuildingName2.get(player.getName())+"_lv"+level2.get(player.getName())+".schematic"));
		} catch (DataException | IOException e) {
			e.printStackTrace();
		}
		for(int xcounter=0;xcounter<cc.getWidth();xcounter++){
			for(int zcounter=0;zcounter<cc.getLength();zcounter++){
				for(int ycounter=0;ycounter<cc.getHeight();ycounter++){
					Location loc = new Location(plugin.getServer().getWorld("coc"),target.get(player.getName()).getBlockX()+xcounter,target.get(player.getName()).getBlockY()+ycounter+cc.getOffset().getBlockY()+1,target.get(player.getName()).getBlockZ()+zcounter);
					Block block = loc.getBlock();
					block.setData(block.getData());
					Material material = block.getType();
					player.sendBlockChange(loc, material, loc.getBlock().getData());
				}
			}
			for(int xcounter2=0;xcounter2<cc.getWidth();xcounter2++){
				for(int zcounter2=0;zcounter2<cc.getLength();zcounter2++){
					for(int ycounter2=0;ycounter2<cc.getHeight();ycounter2++){
						Location loc2 = new Location(plugin.getServer().getWorld("coc"),player.getTargetBlock(null, 10).getLocation().getBlockX()+xcounter2,player.getTargetBlock(null, 10).getLocation().getBlockY()+ycounter2+cc.getOffset().getBlockY()+1,player.getTargetBlock(null, 10).getLocation().getBlockZ()+zcounter2);
						Vector vec = new Vector(xcounter2, ycounter2, zcounter2);
						BaseBlock block = cc.getBlock(vec);
						int materialid = block.getId();
						Material material = Material.getMaterial(materialid);
						player.sendBlockChange(loc2, material, (byte) block.getData());
					}
				}
			}
			}
		target.put(player.getName(), player.getTargetBlock(null, 10).getLocation());

		}
	}
	
	
	@EventHandler
	private void onplayermove(PlayerMoveEvent event){
		if(!event.getPlayer().getWorld().equals(plugin.getServer().getWorld("coc"))){return;}
		if(!BuildingName2.containsKey(event.getPlayer().getName())){return;}
		if(!level2.containsKey(event.getPlayer().getName())){return;}
		moveRelativeBuilding(event.getPlayer());
	}
	@SuppressWarnings("deprecation")
	@EventHandler
	private void onPlayerInteract(PlayerInteractEvent event){
		Base base = new Base();
		CuboidClipboard cc = null;
		try {
			cc = CuboidClipboard.loadSchematic(new File(plugin.getDataFolder()+"/schematics/ground.schematic"));
		} catch (DataException | IOException e) {
			e.printStackTrace();
		}
		if(!event.getPlayer().getWorld().equals(plugin.getServer().getWorld("coc"))){return;}
		if(!BuildingName2.containsKey(event.getPlayer().getName())){return;}
		if(!level2.containsKey(event.getPlayer().getName())){return;}
		CuboidClipboard cc2 = null;
		try {
			cc2 = CuboidClipboard.loadSchematic(new File(plugin.getDataFolder()+"/schematics/"+BuildingName2.get(event.getPlayer().getName())+"/"+BuildingName2.get(event.getPlayer().getName())+"_lv"+level2.get(event.getPlayer().getName())+".schematic"));
		} catch (DataException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR)){
			if(event.getPlayer().getTargetBlock(null, 10).getLocation().getBlockY() == Coc.getdataconffile(event.getPlayer()).getInt("spawn.y")-1){
				Location loc1 = new Location(plugin.getServer().getWorld("coc"), base.getArenaSpawn(event.getPlayer()).getBlockX()+cc.getWidth()/2-10, base.getArenaSpawn(event.getPlayer()).getBlockY(), base.getArenaSpawn(event.getPlayer()).getBlockZ()+cc.getLength()/2-10);
				Location loc2 = new Location(plugin.getServer().getWorld("coc"), base.getArenaSpawn(event.getPlayer()).getBlockX()-cc.getWidth()/2+10, base.getArenaSpawn(event.getPlayer()).getBlockY(), base.getArenaSpawn(event.getPlayer()).getBlockZ()-cc.getLength()/2+10);
				if(checkIfInArea(loc1, loc2, event.getPlayer().getTargetBlock(null, 10).getLocation()) == true){
					if(checkIfInArea(loc1, loc2, event.getPlayer().getTargetBlock(null, 10).getLocation().add(cc2.getWidth()-1, 0, 0))){
						if(checkIfInArea(loc1, loc2, event.getPlayer().getTargetBlock(null, 10).getLocation().add(0, 0, cc2.getLength()-1))){
							if(checkIfInArea(loc1, loc2, event.getPlayer().getTargetBlock(null, 10).getLocation().add(cc2.getWidth()-1, 0, cc2.getLength()-1))){
								placeBuilding(event.getPlayer(), event.getPlayer().getTargetBlock(null, 10).getLocation(), BuildingName2.get(event.getPlayer().getName()), level2.get(event.getPlayer().getName()));
								removeRelativeBuilding(event.getPlayer());
								base.Rebuild(event.getPlayer());
							}else{
								event.getPlayer().sendMessage(ChatColor.RED+"[ClashofClans] your building is not in your base!");
							}
	
						}else{
							event.getPlayer().sendMessage(ChatColor.RED+"[ClashofClans] your building is not in your base!");
						}
					}else{
						event.getPlayer().sendMessage(ChatColor.RED+"[ClashofClans] your building is not in your base!");
					}
				}else{
					event.getPlayer().sendMessage(ChatColor.RED+"[ClashofClans] your building is not in your base!");
				}
			}else{
				event.getPlayer().sendMessage(ChatColor.RED+"[ClashofClans] your building is not on the ground!");
			}
		}
		if(event.getAction().equals(Action.LEFT_CLICK_BLOCK) || event.getAction().equals(Action.LEFT_CLICK_AIR)){
			removeRelativeBuilding(event.getPlayer());
		}
	}
	private boolean checkIfInArea(Location corner1, Location corner2, Location PlayerPos){
	    if(PlayerPos.getBlockX() >= corner2.getBlockX() && PlayerPos.getBlockX() <= corner1.getBlockX()){
	        if(PlayerPos.getBlockZ() >= corner2.getBlockZ() && PlayerPos.getBlockZ() <= corner1.getBlockZ()){
	            return true;
	        }
	    }
	    return false;
	}
}
