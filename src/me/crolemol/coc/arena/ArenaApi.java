package me.crolemol.coc.arena;

import java.io.File;
import java.io.IOException;

import me.crolemol.coc.Coc;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.data.DataException;

public class ArenaApi {
	private static Coc plugin = Coc.getPlugin();
	protected static File UUIDFile;
	protected static FileConfiguration UUIDConf;
	
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
	
	public Player playerisinwhosebase(Player player) throws DataException, IOException{
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

		int max_x  = (plugin.getgeneraldataconf().getInt("max x") - plugin.getgeneraldataconf().getInt("min x"))/cc.getLength();
		int z_column = 0;
		int edge = 0;
		for (int column=0;loc.getBlockZ() > edge;column++){
			edge = column * cc.getLength();
			z_column = column;
		}
		int UUID=z_column*max_x;
		int min_x = plugin.getgeneraldataconf().getInt("min x");
		edge = 0;
		for(int row=0;edge < loc.getBlockX();row++){
			edge =min_x + row*cc.getLength();
			UUID++;
		}
		return UUID;
		
	}

	public Location getArenaSpawn(Player player){
		FileConfiguration dataconf = plugin.getdataconffile(player);
		Location loc = new Location(plugin.getServer().getWorld("coc"), dataconf.getInt("spawn.x"), dataconf.getInt("spawn.y") , dataconf.getInt("spawn.z"));
		return loc;
	} 
	public Location getArenaSpawn(int ArenaUUID){
		plugin.getServer().getPlayer("crolemol").sendMessage(getUUIDConf().getString(ArenaUUID+"")+"");
		@SuppressWarnings("deprecation")
		OfflinePlayer player = plugin.getServer().getOfflinePlayer(getUUIDConf().getString(""+ArenaUUID));
		FileConfiguration dataconf = plugin.getdataconffile(player);
		Location loc = new Location(plugin.getServer().getWorld("coc"), dataconf.getInt("spawn.x"), dataconf.getInt("spawn.y") , dataconf.getInt("spawn.z"));
		return loc;
	} 
	public static File getUUIDFile(){
		UUIDFile = new File(plugin.getDataFolder() + "/data/", 
				"ArenaUUIDS.yml");
		return UUIDFile;
	}
	public static FileConfiguration getUUIDConf(){
		UUIDConf = YamlConfiguration.loadConfiguration(getUUIDFile());
		return UUIDConf;
	}
	public void giveplayerArenaUUID(Player player){
		getUUIDConf().set(""+getUUIDConf().getInt("UUIDCounter")+1, player.getName());
		try {
			getUUIDConf().save(getUUIDFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
