package me.crolemol.coc.arena;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.crolemol.coc.Coc;
import me.crolemol.coc.arena.building.Goldmine;
import me.crolemol.coc.arena.building.Townhall;
import me.crolemol.coc.arena.building.interfaces.Building;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.data.DataException;

public class Base {
	private static Coc plugin = Coc.getPlugin();
	private OfflinePlayer owner2;
	private int ArenaUUID2;
	private Location ArenaSpawn2;

	public Base(OfflinePlayer owner,int ArenaUUID,Location Arenaspawn){
		owner2 = owner;
		ArenaUUID2 = ArenaUUID;
		ArenaSpawn2 = Arenaspawn;
		}
	public Base(Player owner,int ArenaUUID,Location Arenaspawn){
			owner2 = owner;
		
		ArenaUUID2 = ArenaUUID;
		ArenaSpawn2 = Arenaspawn;
	}
	public Boolean isinownbase(Player player) throws DataException, IOException {
		FileConfiguration dataconf = Coc.getdataconffile(player);
		@SuppressWarnings("deprecation")
		CuboidClipboard cc = CuboidClipboard.loadSchematic(new File(plugin
				.getDataFolder() + "/schematics/ground.schematic"));
		if (plugin.getServer().getOnlinePlayers().contains(player)) {
			if (player.getLocation().getBlockX() < dataconf.getInt("spawn.x")
					+ cc.getLength() / 2
					&& player.getLocation().getBlockX() > dataconf
							.getInt("spawn.x") - cc.getLength() / 2) {
				if (player.getLocation().getBlockZ() < dataconf
						.getInt("spawn.z") + cc.getLength() / 2
						&& player.getLocation().getBlockZ() > dataconf
								.getInt("spawn.z") - cc.getLength() / 2) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	public List<String> containsbuildings() {
		List<String> contains = new ArrayList<String>();
		Coc.getPlugin();
		FileConfiguration dataconf = Coc.getdataconffile(owner2);
		for (Buildingspecs specs : Buildingspecs.values()) {
			if (dataconf.contains(specs.getName())) {
				contains.add(specs.getName());
			}
		}
		return contains;
	}
	public int getAmountofBuilding(String Buildingname, OfflinePlayer owner22) {
		FileConfiguration dataconf = Coc.getdataconffile(owner22);
		if (!dataconf.contains(Buildingname)) {
			return 0;
		}
		int numberofbuilding = 0;
		for (int counter = 1; dataconf.contains(Buildingname + "." + counter); counter++) {
			numberofbuilding = counter;
		}
		return numberofbuilding;
	}
	public Building getBuilding(String BuildingName,int BuildingID, Player owner){
		FileConfiguration dataconf = Coc.getdataconffile(owner);
		if(!dataconf.contains(BuildingName+"."+BuildingID)){return null;}
		World world = plugin.getServer().getWorld("coc");
		int x = dataconf.getInt(BuildingName+"."+BuildingID+".location.x");
		int y = dataconf.getInt(BuildingName+"."+BuildingID+".location.y");
		int z = dataconf.getInt(BuildingName+"."+BuildingID+".location.z");
		Location loc = new Location(world,x,y,z);
		int level = dataconf.getInt(BuildingName+"."+BuildingID+".level");
		if(BuildingName.equals("goldmine")){
			return new Goldmine(owner,loc,level,BuildingID);
		}else if(BuildingName.equals("townhall")){
			return new Townhall(owner,loc,level);
		}
		return null;
	}
	


	public void Rebuild() {
		List<String> contains = containsbuildings();
		Coc.getPlugin();
		FileConfiguration dataconf = Coc.getdataconffile(owner2);
		World world = Coc.getPlugin().getServer().getWorld("coc");
		File ground = new File(plugin.getDataFolder()
				+ "/schematics/ground.schematic");
		try {
			@SuppressWarnings("deprecation")
			CuboidClipboard cc = CuboidClipboard.loadSchematic(ground);
			pasteschematic(world,ground,new Vector(dataconf.getInt("spawn.x") - cc.getWidth() / 2,
							dataconf.getInt("spawn.y"), dataconf
									.getInt("spawn.z") - cc.getLength() / 2));
		} catch (MaxChangedBlocksException | DataException | IOException e) {
			e.printStackTrace();
		}
		for (Buildingspecs building : Buildingspecs.values()) {
			if (contains.contains(building.getName())) {
				for (int counter = 1; counter <= getAmountofBuilding(
						building.getName(), owner2); counter++) {
					File schematic = new File(Coc.getPlugin().getDataFolder()
							+ "/schematics/"+ building.getName()+ "/"+ building.getName()+ "_lv"
							+ dataconf.getInt(building.getName() + "."
									+ counter + ".level") + ".schematic");
					Vector vec = new Vector(dataconf.getInt(building.getName()
							+ "." + counter + ".location.x"),
							dataconf.getInt(building.getName() + "." + counter
									+ ".location.y"), dataconf.getInt(building
									.getName() + "." + counter + ".location.z"));
					try {
						pasteschematic(world, schematic, vec);
					} catch (MaxChangedBlocksException | DataException
							| IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}


	public Location getArenaSpawn() {
		return ArenaSpawn2;
	}
	public int getArenaUUID(){
		return ArenaUUID2;
	}

	@SuppressWarnings("deprecation")
	public static Base getBase(int arenaUUID){
		if(plugin.getUUIDConf().contains(arenaUUID+"")){
		Player owner = plugin.getServer().getPlayer(plugin.getUUIDConf().getString(arenaUUID+""));
		FileConfiguration dataconf = Coc.getdataconffile(owner);
		World world = plugin.getServer().getWorld("coc");
		int x = dataconf.getInt("spawn.x");
		int y = dataconf.getInt("spawn.y");
		int z = dataconf.getInt("spawn.z");
		Location loc = new Location(world,x,y,z);
		return new Base(owner, arenaUUID, loc);
		}
		return null;
	}
	public static Base getBase(OfflinePlayer owner){
		if(Coc.getdatafile(owner).exists()){
		FileConfiguration dataconf = Coc.getdataconffile(owner);
		World world = plugin.getServer().getWorld("coc");
		int x = dataconf.getInt("spawn.x");
		int y = dataconf.getInt("spawn.y");
		int z = dataconf.getInt("spawn.z");
		Location loc = new Location(world,x,y,z);
		return new Base(owner, getBase(loc).getArenaUUID(), loc);
		}
		return null;
	}
	public static Base getBase(Location loc){
		int UUID = getArenaUUID(loc);
		return getBase(UUID);
		
	}

	public static void giveplayerArenaUUID(Player player) {
		int uuid = plugin.getUUIDConf().getInt("UUIDCounter");
		uuid++;
		plugin.getUUIDConf().set(uuid + "", player.getName());
		plugin.getUUIDConf().set("UUIDCounter", uuid);
		try {
			plugin.getUUIDConf().save(plugin.getUUIDFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@SuppressWarnings("deprecation")
	public static Integer getArenaUUID(Location loc) {
		Coc plugin  = Coc.getPlugin();
		CuboidClipboard cc = null;
		try {
			cc = CuboidClipboard.loadSchematic(new File(plugin.getDataFolder() + "/schematics/ground.schematic"));
		} catch (DataException | IOException e) {
			e.printStackTrace();
		}

		int max_x = plugin.getgeneraldataconf().getInt("max x")
				/ cc.getLength();
		int x_edge = cc.getLength();
		int z_edge = cc.getLength();
		int UUID = 1;
		double max_columns = Math.ceil(plugin.getUUIDConf().getInt("UUIDCounter") / max_x);
		if (loc.getX() >= 0 || loc.getZ() >= 0) {
			for (int column = 0; column <= max_columns; column++) {
				for (int row = 1; row <= max_x; row++) {
					x_edge = row * cc.getLength();
					if (loc.getBlockX() < x_edge && loc.getBlockZ() < z_edge) {
						return UUID;
					} else {
						UUID++;
					}
				}
			}
		}
		return null;

	}
	public OfflinePlayer getOwner(){
		return owner2;
	}
	@SuppressWarnings("deprecation")
	public static void newBase(Player owner){
		FileConfiguration dataconf = Coc.getdataconffile(owner);
		FileConfiguration generalconf = plugin.getgeneraldataconf();
		Vector vec = new Vector(
				generalconf.getInt("nextground.x"),
				generalconf.getInt("nextground.y"),
				generalconf.getInt("nextground.z"));
		owner.sendMessage(ChatColor.RED
				+ "you don't have a base, creating one now!");
		File schema = new File(plugin.getDataFolder()
				+ "/schematics", "ground.schematic");
		try {
			pasteschematic(
					plugin.getServer().getWorld("coc"),
					schema, vec);
		} catch (MaxChangedBlocksException | DataException
				| IOException e) {
			e.printStackTrace();
		}
		CuboidClipboard cc;
		try {
			cc = CuboidClipboard.loadSchematic(new File(plugin.getDataFolder()
							+ "/schematics/ground.schematic"));
			plugin.getgeneraldataconf().set(
					"nextground.x",
					plugin.getgeneraldataconf().getInt(
							"nextground.x")
							+ cc.getLength());
			if (plugin.getgeneraldataconf().getInt(
					"nextground.x") > plugin
					.getgeneraldataconf().getInt("max_x")) {
				plugin.getgeneraldataconf().set(
						"nextground.x", 0);
				plugin.getgeneraldataconf().set(
						"nextground.z",
						plugin.getgeneraldataconf().getInt(
								"nextground.z")
								+ cc.getWidth());
			}
			dataconf.set("spawn.x",
					vec.getBlockX() + cc.getLength() / 2);
			dataconf.set("spawn.y", 64);
			dataconf.set("spawn.z",
					vec.getBlockZ() + cc.getLength() / 2);
			dataconf.set("Gold", 1000);
			dataconf.set("Elixir", 1000);
			dataconf.set("Gems", 50);
			Vector townhallvec = new Vector(
					dataconf.getInt("spawn.x") - 6,
					dataconf.getInt("spawn.y"),
					dataconf.getInt("spawn.z") + 5);
			File townhallschema = new File(
					plugin.getDataFolder()+ "/schematics/townhall",
					"townhall_lv1.schematic");
			try {
				pasteschematic(
						plugin.getServer().getWorld("coc"),
						townhallschema, townhallvec);
			} catch (MaxChangedBlocksException | DataException
					| IOException e) {
				e.printStackTrace();
			}
			dataconf.set("townhall.1.location.x",townhallvec.getBlockX());
			dataconf.set("townhall.1.location.y",townhallvec.getBlockY());
			dataconf.set("townhall.1.location.z",townhallvec.getBlockZ());
			dataconf.set("townhall.1.level", 1);

			try {
				Coc.getPlugin();
				dataconf.save(Coc.getdatafile(owner));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (DataException | IOException e) {
			e.printStackTrace();
		}
		try {
			generalconf.save(plugin.getGeneralFile());
		} catch (IOException e) {
			e.printStackTrace();
		}

		giveplayerArenaUUID(owner);
	}
	

	private static void pasteschematic(World world, File file,
			com.sk89q.worldedit.Vector origin) throws DataException,
			IOException, MaxChangedBlocksException {
		EditSession es = new EditSession(new BukkitWorld(world), 999999999);
		@SuppressWarnings("deprecation")
		CuboidClipboard cc = CuboidClipboard.loadSchematic(file);
		cc.paste(es, origin, false);
	}
}