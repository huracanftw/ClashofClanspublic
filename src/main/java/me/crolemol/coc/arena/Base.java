package me.crolemol.coc.arena;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import me.crolemol.coc.Coc;
import me.crolemol.coc.arena.building.ArcherQueenAltar;
import me.crolemol.coc.arena.building.ArmyCamp;
import me.crolemol.coc.arena.building.BarbarianKingAltar;
import me.crolemol.coc.arena.building.Barracks;
import me.crolemol.coc.arena.building.BuildersHut;
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

	@SuppressWarnings("deprecation")
	public Boolean isinownbase(Player player){
		CuboidClipboard cc = null;
		try {
			cc = CuboidClipboard.loadSchematic(new File(plugin.getDataFolder() + "/schematics/ground.schematic"));
		} catch (DataException | IOException e) {
			e.printStackTrace();
		}
		if (plugin.getServer().getPlayer(player.getName()) != null) {
			if (player.getLocation().getBlockX() < getArenaSpawn().getBlockX()
					+ cc.getLength() / 2
					&& player.getLocation().getBlockX() > getArenaSpawn().getBlockX() - cc.getLength() / 2) {
				if (player.getLocation().getBlockZ() < getArenaSpawn().getBlockZ() + cc.getLength() / 2
						&& player.getLocation().getBlockZ() > getArenaSpawn().getBlockZ() - cc.getLength() / 2) {
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

	public int getAmountofBuilding(String Buildingname) {
	try {
		ResultSet result = plugin.getDataBase().query("SELECT count(*) AS counter FROM Buildings WHERE owner='"+owner2.getUniqueId()+"' AND BuildingName='"+Buildingname+"'");
		
		 while (result.next()) {
			 return result.getInt("counter");
			 }
		 return 0;
	} catch (SQLException e) {
		e.printStackTrace();
		return 0;
	}
	}

	public Building getBuilding(String BuildingName, int BuildingID,
			OfflinePlayer owner) {
		switch (BuildingName) {
		case "goldmine":
			return Goldmine.getGoldmine(BuildingID, owner);
		case "townhall":
			return Townhall.getTownhall(owner);
		case "goldstorage":
			return GoldStorage.getGoldStorage(BuildingID, owner);
		case "elixircollector":
			return ElixirCollector.getElixirCollector(BuildingID, owner);
		case "elixirstorage":
			return ElixirStorage.getElixirStorage(BuildingID, owner);
		case "darkelixirstorage":
			return DarkElixirStorage.getDarkElixirStorage(BuildingID, owner);
		case "darkelixirdrill":
			return DarkElixirDrill.getDarkElixirDrill(BuildingID, owner);
		case "armycamp":
			return ArmyCamp.getArmyCamp(BuildingID, owner);
		case "buildershut":
			return BuildersHut.getBuildersHut(BuildingID, owner);
		case "barbariankingaltar":
			return BarbarianKingAltar.getBarbarianKingAltar(BuildingID, owner);
		case "barracks":
			return Barracks.getBarracks(BuildingID, owner);
		case "archerqueenaltar":
			return ArcherQueenAltar.getArcherQueenAltar(BuildingID, owner);
		}
		
		return null;
	}

	@SuppressWarnings("deprecation")
	public void Rebuild() {
		List<String> contains = containsbuildings();
		Coc.getPlugin();
		World world = Coc.getPlugin().getServer().getWorld("coc");
		File ground = new File(plugin.getDataFolder()
				+ "/schematics/ground.schematic");
		CuboidClipboard cc = null;
		try {
			cc = CuboidClipboard.loadSchematic(ground);
		} catch (DataException | IOException e) {
			e.printStackTrace();
		}
		pasteschematic(world,ground,
				new Vector(getArenaSpawn().getBlockX() - cc.getWidth() / 2,
						getArenaSpawn().getBlockY() - 1, getArenaSpawn().getBlockZ() - cc.getLength() / 2));
		int level;
		for (BuildingType building : BuildingType.values()) {
			if (contains.contains(building.getName())) {
				for (int counter = 1; counter <= getAmountofBuilding(building
						.getName()); counter++) {
					Building building2 = getBuilding(building.getName(),counter,owner2);
					if (building2.getLevel() == 0) {
						level = 1;
					} else {
						level = building2.getLevel();
					}
					File schematic = new File(Coc.getPlugin().getDataFolder()
							+ "/schematics/" + building.getName() + "/"
							+ building.getName() + "_lv" + level + ".schematic");
					Vector vec = new Vector(building2.getLocation().getBlockX(),
							building2.getLocation().getBlockY(), building2.getLocation().getBlockZ());
					Base.pasteschematic(world, schematic, vec);
				}
			}
		}
	}

	public List<String> containsbuildings() {
		ResultSet result = plugin.getDataBase().query("SELECT * FROM Buildings WHERE owner='"+owner2.getUniqueId()+"' AND BuildingID=1");
		List<String> list = new ArrayList<String>();
		boolean hasnext = true;
		for(@SuppressWarnings("unused")
		int counter = 1;hasnext ==true;counter++){
			try {
				String temp = result.getString("BuildingName");
				list.add(temp);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(result.next() == false){
					hasnext=false;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			}
		return list;
	}


	public Location getArenaSpawn() {
		return ArenaSpawn2;
	}

	public Building placeNonRealBuilding(Building building, Location loc,
			OfflinePlayer owner) {
		if (building.isRealBuilding() == true) {
			return null;
		}
		int counter = getAmountofBuilding(building.getBuildingName()) + 1;
		Long caltime = null;
		if (building instanceof ResourceBuilding) {
			Calendar cal = Calendar.getInstance();
			Long caltime2 = cal.getTimeInMillis() / 60 / 1000;
			caltime = caltime2;
		}
		plugin.getDataBase().query("INSERT INTO Buildings ('owner','BuildingName','Location_x',"
				+ "'Location_y','Location_z','Level','BuildingID','Upgrade','LastCollect') VALUES ('"+owner.getUniqueId()+"','"+building.getBuildingName()+"'"
						+ ","+loc.getBlockX()+","+loc.getBlockY()+","+loc.getBlockZ()+","+building.getLevel()+","+counter+",NULL,"+caltime+");");
		try {
			plugin.getDataBase().getConnection().commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return getBuilding(building.getBuildingName(), counter, owner);
	}

	public int getArenaUUID() {
		return ArenaUUID2;
	}

	public static Base getBase(int arenaUUID) {
		ResultSet result = plugin.getDataBase().query("SELECT * FROM Bases WHERE BaseID="+arenaUUID);
			OfflinePlayer owner = null;
			int x = 0;
			int y = 0;
			int z = 0;			
			try {
				owner = plugin.getServer().getOfflinePlayer(UUID.fromString(result.getString("owner")));
				x = result.getInt("spawnlocation_x");
				y = result.getInt("spawnlocation_y");
				z = result.getInt("spawnlocation_z");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if(y == 0){
				return null;
			}
			World world = plugin.getServer().getWorld("coc");
			Location loc = new Location(world, x, y, z);
			return new Base(owner, arenaUUID, loc);
	}

	public static Base getBase(OfflinePlayer owner) {
		ResultSet result = plugin.getDataBase().query("SELECT * FROM Bases WHERE owner='"+owner.getUniqueId()+"'");

		int x = 0;
		int y = 0;
		int z = 0;	
		int uuid = 0;
		try {
			uuid = result.getInt("BaseID");
			x = result.getInt("spawnlocation_x");
			y = result.getInt("spawnlocation_y");
			z = result.getInt("spawnlocation_z");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(y == 0){
			return null;
		}
		
		World world = plugin.getServer().getWorld("coc");
		Location loc = new Location(world, x, y, z);
		return new Base(owner, uuid, loc);
}

	public static Base getBase(Location loc) {
		int UUID = getArenaUUID(loc);
		return getBase(UUID);

	}

	@SuppressWarnings("deprecation")
	public static int getArenaUUID(Location loc) {
		Coc plugin = Coc.getPlugin();
		CuboidClipboard cc = null;
		try {
			cc = CuboidClipboard.loadSchematic(new File(plugin.getDataFolder()
					+ "/schematics/ground.schematic"));
		} catch (DataException | IOException e) {
			e.printStackTrace();
		}
		if (loc.getBlockX() < 0) {
			return 0;
		}
		if (loc.getBlockZ() < 0) {
			return 0;
		}

		int max_x = (int) Math.ceil(plugin.getgeneraldataconf().getInt("max x")
				/ cc.getLength());
		double x = Math.ceil(loc.getX() / cc.getWidth());
		double z = Math.ceil(loc.getY() / cc.getLength()) - 1;
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
		owner.sendMessage(ChatColor.RED+ "[ClashofClans] you don't have a base, creating one now!");
		File schema = new File(plugin.getDataFolder() + "/schematics","ground.schematic");
		pasteschematic(plugin.getServer().getWorld("coc"), schema, vec);
		CuboidClipboard cc = null;
		try {
			cc = CuboidClipboard.loadSchematic(new File(plugin.getDataFolder()
					+ "/schematics/ground.schematic"));
		} catch (DataException | IOException e3) {
			e3.printStackTrace();
		}

		plugin.getgeneraldataconf().set(
				"nextground.x",
				plugin.getgeneraldataconf().getInt("nextground.x")
						+ cc.getLength());
		if (plugin.getgeneraldataconf().getInt("nextground.x") > plugin
				.getgeneraldataconf().getInt("max x")) {
			plugin.getgeneraldataconf().set("nextground.x", 0);
			plugin.getgeneraldataconf().set(
					"nextground.z",
					plugin.getgeneraldataconf().getInt("nextground.z")
							+ cc.getLength());
			}
			try {
				Coc.getPlugin().getgeneraldataconf().save(plugin.getGeneralFile());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				Coc.getPlugin().getDataBase()
						.query("INSERT INTO 'Bases' ('BaseID','owner','spawnlocation_x','spawnlocation_y','spawnlocation_z') VALUES"
								+ "(NULL,'"
								+ owner.getUniqueId().toString()
								+ "',"
								+ (vec.getBlockX() + (cc.getWidth() / 2))
								+ ",65,"
								+ (vec.getBlockZ() + (cc.getLength() / 2))
								+ ")").close();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
			;

			try {
				Coc.getPlugin().getDataBase()
						.query("INSERT INTO 'Resources' ('owner','Gold','Elixir','DarkElixir','Gems') VALUES ('"
								+ owner.getUniqueId().toString() + "',1000,1000,0,500);").close();
						plugin.getDataBase().getConnection().commit();
			} catch (SQLException e2) {
				e2.printStackTrace();
			};
			ResultSet result;
			try {
				result = Coc.getPlugin().getDataBase()
						.query("SELECT * FROM 'Bases' WHERE owner = '"
								+ owner.getUniqueId().toString()+"'");
				Vector townhallvec = new Vector(
						result.getInt("spawnlocation_x") - 6,
						result.getInt("spawnlocation_y") - 1,
						result.getInt("spawnlocation_z") + 5);
				result.close();
				File townhallschema = new File(plugin.getDataFolder()
						+ "/schematics/townhall", "townhall_lv1.schematic");
				pasteschematic(plugin.getServer().getWorld("coc"),
						townhallschema, townhallvec);
				Coc.getPlugin()
						.getDataBase()
						.query("INSERT INTO 'Buildings' ('owner','BuildingName','Location_x',"
								+ "'Location_y','Location_z','Level','BuildingID') VALUES ('"
								+ owner.getUniqueId()
								+ "','townhall',"
								+ townhallvec.getBlockX()
								+ ","
								+ townhallvec.getBlockY()
								+ ","
								+ townhallvec.getBlockZ() + ",1,1);").close();
				plugin.getDataBase().getConnection().commit();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}

			try {
				generalconf.save(plugin.getGeneralFile());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	@SuppressWarnings("deprecation")
	private static void pasteschematic(World world, File file,
			com.sk89q.worldedit.Vector origin) {
		EditSession es = new EditSession(new BukkitWorld(world), 999999999);
		CuboidClipboard cc;
		try {
			cc = CuboidClipboard.loadSchematic(file);
			cc.paste(es, origin, false);
		} catch (DataException | IOException | MaxChangedBlocksException e) {
			e.printStackTrace();
		}
	}
}
