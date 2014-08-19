package me.crolemol.coc.arena.building;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import me.crolemol.coc.Coc;
import me.crolemol.coc.arena.Base;
import me.crolemol.coc.arena.building.interfaces.Building;
import me.crolemol.coc.arena.building.interfaces.ResourceBuilding;
import me.crolemol.coc.arena.panels.TownhallLimit;

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

public class RelativeBuilding implements Listener{
	static Coc plugin = Coc.getPlugin();
	private Building building2;
	private static Map<String,Location> target= new HashMap<>();
	private static Map<String,Building> staticbuilding = new HashMap<>();
	public RelativeBuilding(Building building){
		building2 = building;
	}
	
	@SuppressWarnings("deprecation")
	public void placeRelativeBuilding(Player player){
		FileConfiguration dataconf = Coc.getdataconffile(player);
		removeRelativeBuilding(player);
		int counter=1;
		for(Boolean test=false;test==false;){
		if(!dataconf.contains(building2.getBuildingName()+"."+counter)){
			test = true;
			break;
		}else{
			counter++;
		}
		}
		TownhallLimit[] thl = TownhallLimit.values();
		if(counter>thl[dataconf.getInt("townhall.1.level")].getMaxGoldmines()){
			player.sendMessage(ChatColor.RED+"[ClashofClans] you have reached the max number of goldmine for this townhall level!");
			return;
		}
		dataconf.set(building2.getBuildingName()+"."+counter+".location.x", player.getTargetBlock(null, 10).getLocation().getBlockX());
		dataconf.set(building2.getBuildingName()+"."+counter+".location.y", player.getTargetBlock(null, 10).getLocation().getBlockY()+1);
		dataconf.set(building2.getBuildingName()+"."+counter+".location.z", player.getTargetBlock(null, 10).getLocation().getBlockZ());
		dataconf.set(building2.getBuildingName()+"."+counter+".level", building2.getLevel());
		if(building2 instanceof ResourceBuilding){
			Calendar cal = Calendar.getInstance();
			Long caltime = cal.getTimeInMillis()/60/1000;
			dataconf.set(building2.getBuildingName()+"."+counter+".lastcollect", caltime);
		}
		try {
			dataconf.save(Coc.getdatafile(player));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Base base = Base.getBase(player);
		base.Rebuild();

	}
	

	@SuppressWarnings("deprecation")
	public void putRelativeBuilding(Player player){
		
		CuboidClipboard cc=null;
		try {
			cc = CuboidClipboard.loadSchematic(new File(plugin.getDataFolder()+"/schematics/"+building2.getBuildingName()+"/"+building2.getBuildingName()+"_lv"+building2.getLevel()+".schematic"));
		} catch (DataException | IOException e) {
			e.printStackTrace();
		}
		for(int xcounter=0;xcounter<cc.getWidth();xcounter++){
			for(int zcounter=0;zcounter<cc.getLength();zcounter++){
				for(int ycounter=0;ycounter<cc.getHeight();ycounter++){
					Location loc = new Location(plugin.getServer().getWorld("coc"),player.getTargetBlock(null, 10).getLocation().getBlockX()+xcounter,player.getTargetBlock(null, 10).getLocation().getBlockY()+ycounter+cc.getOffset().getBlockY()+1,player.getTargetBlock(null, 10).getLocation().getBlockZ()+zcounter);
					Vector vec = new Vector(xcounter, ycounter, zcounter);
					BaseBlock block = cc.getBlock(vec);
					int materialid = block.getId();
					Material material = Material.getMaterial(materialid);
					player.sendBlockChange(loc, material, (byte) block.getData());
				}
			}
		}

		target.put(player.getName(), player.getTargetBlock(null, 10).getLocation());
		staticbuilding.put(player.getName(), building2);
	}
	

	@SuppressWarnings("deprecation")
	public void removeRelativeBuilding(Player player){
		if(!target.containsKey(player.getPlayer().getName())){return;}
		if(!staticbuilding.containsKey(player.getPlayer().getName())){return;}
		CuboidClipboard cc=null;
		try {
			cc = CuboidClipboard.loadSchematic(new File(plugin.getDataFolder()+"/schematics/"+building2.getBuildingName()+"/"+building2.getBuildingName()+"_lv"+building2.getLevel()+".schematic"));
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
		target.remove(player.getName());
		staticbuilding.put(player.getName(), null);
	}


	@SuppressWarnings("deprecation")
	private void moveRelativeBuilding(Player player){
		if(!target.containsKey(player.getPlayer().getName())){return;}
		if(!target.get(player.getName()).equals(player.getTargetBlock(null, 10))){
		CuboidClipboard cc=null;
		try {
			cc = CuboidClipboard.loadSchematic(new File(plugin.getDataFolder()+"/schematics/"+building2.getBuildingName()+"/"+building2.getBuildingName()+"_lv"+building2.getLevel()+".schematic"));
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

	public int getLevel(){
		return building2.getLevel();
	}
	public String getBuildingName(){
		return building2.getBuildingName();
	}
	
	
	@EventHandler
	private void onplayermove(PlayerMoveEvent event){
		if(!event.getPlayer().getWorld().equals(plugin.getServer().getWorld("coc"))){return;}
		if(!target.containsKey(event.getPlayer().getName())){return;}
		if(!staticbuilding.containsKey(event.getPlayer().getName())){return;}
		RelativeBuilding rb = new RelativeBuilding(getNewBuilding(event.getPlayer()));
		rb.moveRelativeBuilding(event.getPlayer());
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	private void onPlayerInteract(PlayerInteractEvent event){
		Base base = Base.getBase(event.getPlayer());
		if(!event.getPlayer().getWorld().equals(plugin.getServer().getWorld("coc"))){return;}
		if(!target.containsKey(event.getPlayer().getName())){return;}
		if(!staticbuilding.containsKey(event.getPlayer().getName())){return;}
		RelativeBuilding rb = new RelativeBuilding(getNewBuilding(event.getPlayer()));
		Coc.getPlugin().getServer().getPlayer("sander").sendMessage(rb.getLevel()+"");
		CuboidClipboard cc = null;
		try {
			cc = CuboidClipboard.loadSchematic(new File(plugin.getDataFolder()+"/schematics/ground.schematic"));
		} catch (DataException | IOException e) {
			e.printStackTrace();
		}
		CuboidClipboard cc2 = null;
		try {
			cc2 = CuboidClipboard.loadSchematic(new File(plugin.getDataFolder()+"/schematics/"+rb.building2.getBuildingName()+"/"+rb.building2.getBuildingName()+"_lv"+rb.building2.getLevel()+".schematic"));
		} catch (DataException | IOException e) {
			e.printStackTrace();
		}
		if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR)){
			if(event.getPlayer().getTargetBlock(null, 10).getLocation().getBlockY() == Coc.getdataconffile(event.getPlayer()).getInt("spawn.y")-1){
				Location loc1 = new Location(plugin.getServer().getWorld("coc"), base.getArenaSpawn().getBlockX()+cc.getWidth()/2-10, base.getArenaSpawn().getBlockY(), base.getArenaSpawn().getBlockZ()+cc.getLength()/2-10);
				Location loc2 = new Location(plugin.getServer().getWorld("coc"), base.getArenaSpawn().getBlockX()-cc.getWidth()/2+10, base.getArenaSpawn().getBlockY(), base.getArenaSpawn().getBlockZ()-cc.getLength()/2+10);
				if(checkIfInArea(loc1, loc2, event.getPlayer().getTargetBlock(null, 10).getLocation()) == true){
					if(checkIfInArea(loc1, loc2, event.getPlayer().getTargetBlock(null, 10).getLocation().add(cc2.getWidth()-1, 0, 0))){
						if(checkIfInArea(loc1, loc2, event.getPlayer().getTargetBlock(null, 10).getLocation().add(0, 0, cc2.getLength()-1))){
							if(checkIfInArea(loc1, loc2, event.getPlayer().getTargetBlock(null, 10).getLocation().add(cc2.getWidth()-1, 0, cc2.getLength()-1))){
								rb.placeRelativeBuilding(event.getPlayer());
								base.Rebuild();
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
			rb.removeRelativeBuilding(event.getPlayer());
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
	private Building getNewBuilding(Player player){
		Building building = staticbuilding.get(player.getName());
		if(building instanceof Goldmine){
			return new Goldmine(building.getOwner(),building.getLocation(),building.getLevel(),building.getBuildingID());
		}else if(building instanceof Townhall){
			return new Townhall(building.getOwner(),building.getLocation(),building.getLevel());
		}
		return null;
	}
}
