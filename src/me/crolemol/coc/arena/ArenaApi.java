package me.crolemol.coc.arena;

import java.io.File;
import java.io.IOException;

import me.crolemol.coc.Coc;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.data.DataException;

public class ArenaApi {
	private static Coc plugin = Coc.getPlugin();
	public static File UUIDFile;
	public static FileConfiguration UUIDConf;

	
	public Boolean isinownbase(Player player) throws DataException, IOException{
		FileConfiguration dataconf = plugin.getdataconffile(player);
		@SuppressWarnings("deprecation")
		CuboidClipboard cc = CuboidClipboard.loadSchematic(new File(plugin.getDataFolder()+"/schematics/ground.schematic"));
		if(plugin.getServer().getOnlinePlayers().contains(player)){
		if(player.getLocation().getBlockX() < dataconf.getInt("spawn.x")+cc.getLength()/2 
				&& player.getLocation().getBlockX() > dataconf.getInt("spawn.x")-cc.getLength()/2){
			if(player.getLocation().getBlockZ() < dataconf.getInt("spawn.z")+cc.getLength()/2 
					&& player.getLocation().getBlockZ() > dataconf.getInt("spawn.z")-cc.getLength()/2){
				return true;
			}else {
				return false;
				}
		}else{
			return false;
		}
		}else{
			return false;
		}
}
	
	public Player playerisInWhoseBase(Player player) throws DataException, IOException{
		if (plugin.getServer().getOnlinePlayers().contains(player)){
			 int UUID = getArenaUUID(player.getLocation());
			String name= getUUIDConf().getString(""+UUID);
			@SuppressWarnings("deprecation")
			Player returnplayer = plugin.getServer().getPlayer(name);
			return returnplayer;
		}else{
			return null;
		}
	}
	public Integer getArenaUUID(Location loc) throws DataException, IOException{
			@SuppressWarnings("deprecation")
			CuboidClipboard cc = CuboidClipboard.loadSchematic(new File(plugin.getDataFolder()+"/schematics/ground.schematic"));

		int max_x  = plugin.getgeneraldataconf().getInt("max x")/cc.getLength();
		int x_edge = cc.getLength();
		int z_edge = cc.getLength();
		int UUID= 1;
		double max_columns = Math.ceil(UUIDConf.getInt("UUIDCounter")/max_x);
		if(loc.getX() >= 0 || loc.getZ() >= 0){		for(int column=0;column<=max_columns;column++){
			for(int row=1;row<= max_x;row++){
				x_edge = row*cc.getLength();
				if(loc.getBlockX()<x_edge && loc.getBlockZ()<z_edge){
					return UUID;
				}else{
				UUID++;
				}
			}
			}
		}
		return null;
		
		
	}

	public Location getArenaSpawn(Player player){
		FileConfiguration dataconf = plugin.getdataconffile(player);
		Location loc = new Location(plugin.getServer().getWorld("coc"), dataconf.getInt("spawn.x"), dataconf.getInt("spawn.y") , dataconf.getInt("spawn.z"));
		return loc;
	} 
	@SuppressWarnings("deprecation")
	public Location getArenaSpawn(int ArenaUUID){
		OfflinePlayer player;
		if(plugin.getServer().getOnlinePlayers().contains(plugin.getServer().getPlayer(getUUIDConf().getString(""+ArenaUUID)))){
			Player player2 = plugin.getServer().getPlayer(getUUIDConf().getString(""+ArenaUUID));
			player = player2;
		}else{
			OfflinePlayer player2 = plugin.getServer().getOfflinePlayer(getUUIDConf().getString(""+ArenaUUID));
			player = player2;
		}
		FileConfiguration dataconf = plugin.getdataconffile(player);
		Location loc = new Location(plugin.getServer().getWorld("coc"), dataconf.getInt("spawn.x"), dataconf.getInt("spawn.y") , dataconf.getInt("spawn.z"));
		return loc;
	} 
	public static File getUUIDFile(){
		return UUIDFile;
	}
	public static FileConfiguration getUUIDConf(){
		return UUIDConf;
	}
	public void giveplayerArenaUUID(Player player){
		int UUID = UUIDConf.getInt("UUIDCounter");
		UUID++;
		UUIDConf.set(""+UUID, player.getName());
		UUIDConf.set("UUIDCounter", UUIDConf.getInt("UUIDCounter")+1);
		try {
			getUUIDConf().save(getUUIDFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
