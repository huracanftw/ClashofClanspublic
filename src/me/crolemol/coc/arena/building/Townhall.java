package me.crolemol.coc.arena.building;

import me.crolemol.coc.Coc;
import me.crolemol.coc.arena.building.interfaces.Building;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Townhall implements Building{
	static Coc plugin = Coc.getPlugin();
	private int level2;
	private Location loc2;
	private Player owner2;
	
	public Townhall(Player owner,Location loc,int level){
		level2 = level;
		loc2 = loc;
		owner2 = owner;
	}
	
	public static Townhall getGoldmine(
			Player owner) {
		FileConfiguration dataconf = Coc.getdataconffile(owner);
		World world = plugin.getServer().getWorld("coc");
		int x = dataconf.getInt("townhall.1.location.x");
		int y = dataconf.getInt("townhall.1.location.x");
		int z = dataconf.getInt("townhall.1.location.x");
		
		return new Townhall(owner,new Location(world,x,y,z),dataconf.getInt("townhall.1.level"));
	}
	

	@Override
	public int getBuildingID() {
		return 1;
	}

	@Override
	public int getLevel() {
		return level2;
	}

	@Override
	public Location getLocation() {
		return loc2;
	}

	@Override
	public Player getOwner() {
		return owner2;
	}

	@Override
	public String getBuildingName() {
		return "townhall";
	}

}
