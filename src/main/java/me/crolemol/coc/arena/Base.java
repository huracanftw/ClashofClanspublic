package me.crolemol.coc.arena;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;

import me.crolemol.coc.Coc;
import me.crolemol.coc.arena.building.ArmyCamp;
import me.crolemol.coc.arena.building.DarkElixirDrill;
import me.crolemol.coc.arena.building.DarkElixirStorage;
import me.crolemol.coc.arena.building.ElixirCollector;
import me.crolemol.coc.arena.building.ElixirStorage;
import me.crolemol.coc.arena.building.GoldStorage;
import me.crolemol.coc.arena.building.Goldmine;
import me.crolemol.coc.arena.building.Townhall;
import me.crolemol.coc.arena.building.interfaces.Building;
import me.crolemol.coc.arena.building.interfaces.ResourceBuilding;

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

	public Base(OfflinePlayer owner, int ArenaUUID, Location Arenaspawn) {
		owner2 = owner;
		ArenaUUID2 = ArenaUUID;
		ArenaSpawn2 = Arenaspawn;
	}

	private Base(Player owner, int ArenaUUID, Location Arenaspawn) {
		owner2 = owner;

		ArenaUUID2 = ArenaUUID;
		ArenaSpawn2 = Arenaspawn;
	}

	public void BuildnewBuilding(Building building, Location loc) {

	}

	@SuppressWarnings("deprecation")
	public Boolean isinownbase(Player player) throws DataException, IOException {
		FileConfiguration dataconf = plugin.getdataconffile(player);
		CuboidClipboard cc = CuboidClipboard.loadSchematic(new File(plugin
				.getDataFolder() + "/schematics/ground.schematic"));
		if (plugin.getServer().getPlayer(player.getName()) != null) {
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
		FileConfiguration dataconf = plugin.getdataconffile(owner2);
		for (Buildingspecs specs : Buildingspecs.values()) {
			if (dataconf.contains(specs.getName())) {
				contains.add(specs.getName());
			}
		}
		return contains;
	}

	public int getAmountofBuilding(String Buildingname) {
		FileConfiguration dataconf = plugin.getdataconffile(owner2);
		if (!dataconf.contains(Buildingname)) {
			return 0;
		}
		int numberofbuilding = 0;
		for (int counter = 1; dataconf.contains(Buildingname + "." + counter); counter++) {
			numberofbuilding = counter;
		}
		return numberofbuilding;
	}

	public Building getBuilding(String BuildingName, int BuildingID,
			OfflinePlayer owner) {
		FileConfiguration dataconf = plugin.getdataconffile(owner);
		if (!dataconf.contains(BuildingName + "." + BuildingID)) {
			return null;
		}
		switch (BuildingName) {
		case "goldmine":
			return Goldmine.getGoldmine(BuildingID,owner);
		case "townhall":
			return Townhall.getTownhall(owner);
		case "goldstorage":
			return GoldStorage.getGoldStorage(BuildingID, owner);
		case "elixircollector":
			return ElixirCollector.getElixirCollector(BuildingID, owner);
		case "elixirstorage":
			return ElixirStorage.getElixirStorage(BuildingID, owner);
		case "darkelixirstorage":
			return DarkElixirStorage.getElixirStorage(BuildingID, owner);
		case "darkelixirdrill":
			return DarkElixirDrill.getDarkElixirDrill(BuildingID, owner);
		case "armycamp":
			return ArmyCamp.getArmyCamp(BuildingID, owner);
		}
		return null;
	}

	public void Rebuild() {
		List<String> contains = containsbuildings();
		Coc.getPlugin();
		FileConfiguration dataconf = plugin.getdataconffile(owner2);
		World world = Coc.getPlugin().getServer().getWorld("coc");
		File ground = new File(plugin.getDataFolder()
				+ "/schematics/ground.schematic");
		try {
			@SuppressWarnings("deprecation")
			CuboidClipboard cc = CuboidClipboard.loadSchematic(ground);
			pasteschematic(
					world,
					ground,
					new Vector(dataconf.getInt("spawn.x") - cc.getWidth() / 2,
							dataconf.getInt("spawn.y") - 1, dataconf.getInt("spawn.z") - cc.getLength() / 2));
		} catch (MaxChangedBlocksException | DataException | IOException e) {
			e.printStackTrace();
		}
		int level;
		for (Buildingspecs building : Buildingspecs.values()) {
			if (contains.contains(building.getName())) {
				for (int counter = 1; counter <= getAmountofBuilding(building
						.getName()); counter++) {
					if (dataconf.getInt(building.getName() + "." + counter
							+ ".level") == 0) {
						level = 1;
					} else {
						level = dataconf.getInt(building.getName() + "."+ counter + ".level");
					}
					File schematic = new File(Coc.getPlugin().getDataFolder()
							+ "/schematics/" + building.getName() + "/"
							+ building.getName() + "_lv" + level + ".schematic");
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
	
	public Building placeNonRealBuilding(Building building,Location loc,OfflinePlayer owner){
		if(building.isRealBuilding()==true){return null;}
		int counter = getAmountofBuilding(building.getBuildingName())+1;
		Coc.getPlugin().getdataconffile(owner).set(building.getBuildingName()+"."+counter+".location.x", loc.getBlockX());
		Coc.getPlugin().getdataconffile(owner).set(building.getBuildingName()+"."+counter+".location.y", loc.getBlockY());
		Coc.getPlugin().getdataconffile(owner).set(building.getBuildingName()+"."+counter+".location.z", loc.getBlockZ());
		Coc.getPlugin().getdataconffile(owner).set(building.getBuildingName()+"."+counter+".level", building.getLevel());
		if(building instanceof ResourceBuilding){
			Calendar cal = Calendar.getInstance();
			Long caltime = cal.getTimeInMillis()/60/1000;
			Coc.getPlugin().getdataconffile(owner).set(building.getBuildingName()+"."+counter+".lastcollect", caltime);
		}
		return getBuilding(building.getBuildingName(), counter, owner);
	}
	public int getArenaUUID() {
		return ArenaUUID2;
	}

	@SuppressWarnings("deprecation")
	public static Base getBase(int arenaUUID) {
		if (plugin.getUUIDConf().contains(arenaUUID + "")) {
			Player owner = plugin.getServer().getPlayer(
					plugin.getUUIDConf().getString(arenaUUID + ""));
			FileConfiguration dataconf = plugin.getdataconffile(owner);
			World world = plugin.getServer().getWorld("coc");
			int x = dataconf.getInt("spawn.x");
			int y = dataconf.getInt("spawn.y");
			int z = dataconf.getInt("spawn.z");
			Location loc = new Location(world, x, y, z);
			return new Base(owner, arenaUUID, loc);
		}
		return null;
	}

	public static Base getBase(OfflinePlayer owner) {
		if (plugin.getdatafile(owner).exists()) {
			FileConfiguration dataconf = plugin.getdataconffile(owner);
			World world = plugin.getServer().getWorld("coc");
			int x = dataconf.getInt("spawn.x");
			int y = dataconf.getInt("spawn.y");
			int z = dataconf.getInt("spawn.z");
			Location loc = new Location(world, x, y, z);
			return new Base(owner, getBase(loc).getArenaUUID(), loc);
		}
		return null;
	}

	public static Base getBase(Location loc) {
		int UUID = getArenaUUID(loc);
		return getBase(UUID);

	}

	private static void giveplayerArenaUUID(Player player) {
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
	private static int getNextArenaUUID(){
		try {
			ResultSet result = Coc.getPlugin().getDataBase().query("SELECT 'UUID' FROM 'Bases'");
			Coc.getPlugin().getLogger().log(Level.INFO, result+"");
			return 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@SuppressWarnings("deprecation")
	public static int getArenaUUID(Location loc) {
		Coc plugin = Coc.getPlugin();
		CuboidClipboard cc = null;
		try {
			cc = CuboidClipboard.loadSchematic(new File(plugin.getDataFolder()	+ "/schematics/ground.schematic"));
		} catch (DataException | IOException e) {
			e.printStackTrace();
		}
		if(loc.getBlockX() < 0){return 0;}
		if(loc.getBlockZ() < 0){return 0;}

		int max_x = (int) Math.ceil(plugin.getgeneraldataconf().getInt("max x")
				/ cc.getLength());
		double x = Math.ceil(loc.getX() / cc.getWidth());
		double z = Math.ceil(loc.getY() / cc.getLength()) -1;
		int uuid = (int) (x + z * max_x);
		return uuid;
	}

	public OfflinePlayer getOwner() {
		return owner2;
	}

	@SuppressWarnings("deprecation")
	public static void newBase(Player owner) {
		FileConfiguration generalconf = plugin.getgeneraldataconf();
		Vector vec = new Vector(generalconf.getInt("nextground.x"),
				generalconf.getInt("nextground.y"),
				generalconf.getInt("nextground.z"));
		owner.sendMessage(ChatColor.RED
				+ "[ClashofClans] you don't have a base, creating one now!");
		File schema = new File(plugin.getDataFolder() + "/schematics",
				"ground.schematic");
		try {
			pasteschematic(plugin.getServer().getWorld("coc"), schema, vec);
		} catch (MaxChangedBlocksException | DataException | IOException e) {
			e.printStackTrace();
		}
		CuboidClipboard cc;
		FileConfiguration dataconf = plugin.getdataconffile(owner);
		try {
			cc = CuboidClipboard.loadSchematic(new File(plugin.getDataFolder()+ "/schematics/ground.schematic"));
			plugin.getgeneraldataconf().set("nextground.x",plugin.getgeneraldataconf().getInt("nextground.x")+ cc.getLength());
			if (plugin.getgeneraldataconf().getInt("nextground.x") > plugin.getgeneraldataconf().getInt("max x")) {
				plugin.getgeneraldataconf().set("nextground.x", 0);
				plugin.getgeneraldataconf().set(
						"nextground.z",
						plugin.getgeneraldataconf().getInt("nextground.z")
								+ cc.getLength());
			}
			Coc.getPlugin().getDataBase().query("INSERT INTO 'Bases' ('UUID','owner','spawnlocation_x','spawnlocation_y','spawnlocation_z') VALUES"
					+ "(1,'sander',"+ (vec.getBlockX() + (cc.getWidth() / 2))+",65,"+ (vec.getBlockZ() + (cc.getLength() / 2))+")");
			dataconf.set("spawn.x", vec.getBlockX() + cc.getLength() / 2);
			getNextArenaUUID();
			dataconf.set("spawn.y", 65);
			dataconf.set("spawn.z", vec.getBlockZ() + cc.getLength() / 2);
			dataconf.set("Gold", 1000);
			dataconf.set("Elixir", 1000);
			dataconf.set("DarkElixir", 0);
			dataconf.set("Gems", 50);
			Vector townhallvec = new Vector(dataconf.getInt("spawn.x") - 6,
					dataconf.getInt("spawn.y") - 1, dataconf.getInt("spawn.z") + 5);
			File townhallschema = new File(plugin.getDataFolder()
					+ "/schematics/townhall", "townhall_lv1.schematic");
			try {
				pasteschematic(plugin.getServer().getWorld("coc"),
						townhallschema, townhallvec);
			} catch (MaxChangedBlocksException | DataException | IOException e) {
				e.printStackTrace();
			}
			dataconf.set("townhall.1.location.x", townhallvec.getBlockX());
			dataconf.set("townhall.1.location.y", townhallvec.getBlockY());
			dataconf.set("townhall.1.location.z", townhallvec.getBlockZ());
			dataconf.set("townhall.1.level", 1);
		} catch (DataException | IOException | SQLException e) {
			e.printStackTrace();
		}
		try {
			generalconf.save(plugin.getGeneralFile());
		} catch (IOException e) {
			e.printStackTrace();
		}

		giveplayerArenaUUID(owner);
		plugin.saveDataconf(owner);
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
