package me.crolemol.coc;

import java.io.File;
import java.io.IOException;

import me.crolemol.coc.arena.ArenaApi;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.data.DataException;

public class CocCommandExecutor implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (cmd.getName().equals("coc")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				FileConfiguration dataconf = Coc.plugin.getdataconffile(player);
				FileConfiguration generalconf = Coc.plugin.getgeneraldataconf();
				if (Coc.plugin.getdatafile(player).exists() == true) {
					Location tploc = new Location(Coc.plugin.getServer()
							.getWorld("coc"), dataconf.getInt("spawn.x"),
							dataconf.getInt("spawn.y"),
							dataconf.getInt("spawn.z"));
					player.teleport(tploc);
					return true;
				} else {
					Vector vec = new Vector(generalconf.getInt("nextground.x"),
							generalconf.getInt("nextground.y"),
							generalconf.getInt("nextground.z"));
					player.sendMessage(ChatColor.RED + "you don't have a base, creating one now!");
					File schema = new File(Coc.plugin.getDataFolder()
							+ "/schematics", "ground.schematic");
					try {
						pasteschematic(Coc.plugin.getServer().getWorld("coc"),
								schema, vec);
					} catch (MaxChangedBlocksException | DataException
							| IOException e) {
						e.printStackTrace();
					}
					CuboidClipboard cc;
					try {
						cc = CuboidClipboard.loadSchematic(new File(Coc.plugin.getDataFolder()+"/schematics/ground.schematic"));
						Coc.plugin.generalConf.set("nextground.x", Coc.plugin.getgeneraldataconf().getInt("nextground.x")+cc.getLength());
					if(Coc.plugin.getgeneraldataconf().getInt("nextground.x") > Coc.plugin.getgeneraldataconf().getInt("max_x")){
						Coc.plugin.getgeneraldataconf().set("nextground.x", 0);
						Coc.plugin.getgeneraldataconf().set("nextground.z", Coc.plugin.getgeneraldataconf().getInt("nextground.z")+cc.getWidth());
					}
					dataconf.set("spawn.x", vec.getBlockX()+cc.getLength()/2);
					dataconf.set("spawn.y", 64);
					dataconf.set("spawn.z", vec.getBlockZ()+cc.getLength()/2);
					try {
						dataconf.save(Coc.plugin.getdatafile(player));
					} catch (IOException e) {
						e.printStackTrace();
					}
					} catch (DataException | IOException e) {
						e.printStackTrace();
					}
					try {
						generalconf.save(Coc.plugin.generalFile);
					} catch (IOException e) {
						e.printStackTrace();
					}

					ArenaApi arenaApi = new ArenaApi();
					arenaApi.giveplayerArenaUUID(player);
					return true;
				}
			}

		}
		return false;
	}

	protected void pasteschematic(World world, File file,
			com.sk89q.worldedit.Vector origin) throws DataException,
			IOException, MaxChangedBlocksException {
		EditSession es = new EditSession(new BukkitWorld(world), 999999999);
		@SuppressWarnings("deprecation")
		CuboidClipboard cc = CuboidClipboard.loadSchematic(file);
		cc.paste(es, origin, false);
	}

}