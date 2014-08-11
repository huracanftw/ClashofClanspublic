package me.crolemol.coc;

import java.io.File;
import java.io.IOException;

import me.crolemol.coc.arena.Base;
import me.crolemol.coc.arena.InteractStick;
import me.crolemol.coc.scoreboard.ScoreboardApi;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
				if (args.length == 1 && args[0].equals("home")) {
					Player player = (Player) sender;
					Coc.getPlugin();
					FileConfiguration dataconf = Coc.getdataconffile(player);
					FileConfiguration generalconf = Coc.plugin
							.getgeneraldataconf();
					Coc.getPlugin();
					if (Coc.getdatafile(player).exists() == true) {
						Location tploc = new Location(Coc.plugin.getServer()
								.getWorld("coc"), dataconf.getInt("spawn.x"),
								dataconf.getInt("spawn.y"),
								dataconf.getInt("spawn.z"));
						player.teleport(tploc);
						player.getInventory().clear();
						ItemStack book = new ItemStack(Material.BOOK);
						ItemMeta bookMeta = book.getItemMeta();
						bookMeta.setDisplayName("Shop");
						book.setItemMeta(bookMeta);
						player.getInventory().setItem(8, book);
						InteractStick.getInteractStick(player);
						ScoreboardApi sb = new ScoreboardApi();
						sb.setCurrencyBoard(player);
						return true;
					} else {
						Vector vec = new Vector(
								generalconf.getInt("nextground.x"),
								generalconf.getInt("nextground.y"),
								generalconf.getInt("nextground.z"));
						player.sendMessage(ChatColor.RED
								+ "you don't have a base, creating one now!");
						File schema = new File(Coc.plugin.getDataFolder()
								+ "/schematics", "ground.schematic");
						try {
							pasteschematic(
									Coc.plugin.getServer().getWorld("coc"),
									schema, vec);
						} catch (MaxChangedBlocksException | DataException
								| IOException e) {
							e.printStackTrace();
						}
						CuboidClipboard cc;
						try {
							cc = CuboidClipboard.loadSchematic(new File(
									Coc.plugin.getDataFolder()
											+ "/schematics/ground.schematic"));
							Coc.plugin.generalConf.set(
									"nextground.x",
									Coc.plugin.getgeneraldataconf().getInt(
											"nextground.x")
											+ cc.getLength());
							if (Coc.plugin.getgeneraldataconf().getInt(
									"nextground.x") > Coc.plugin
									.getgeneraldataconf().getInt("max_x")) {
								Coc.plugin.getgeneraldataconf().set(
										"nextground.x", 0);
								Coc.plugin.getgeneraldataconf().set(
										"nextground.z",
										Coc.plugin.getgeneraldataconf().getInt(
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
									Coc.plugin.getDataFolder()
											+ "/schematics/townhall",
									"townhall_lv1.schematic");
							try {
								pasteschematic(
										Coc.plugin.getServer().getWorld("coc"),
										townhallschema, townhallvec);
							} catch (MaxChangedBlocksException | DataException
									| IOException e) {
								e.printStackTrace();
							}
							dataconf.set("townhall.1.location.x",
									townhallvec.getBlockX());
							dataconf.set("townhall.1.location.y",
									townhallvec.getBlockY());
							dataconf.set("townhall.1.location.z",
									townhallvec.getBlockZ());
							dataconf.set("townhall.1.level", 1);

							try {
								Coc.getPlugin();
								dataconf.save(Coc.getdatafile(player));
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

						Base base = new Base();
						base.giveplayerArenaUUID(player);
						return true;
					}
				}

			}
		}
		return false;
	}

	private void pasteschematic(World world, File file,
			com.sk89q.worldedit.Vector origin) throws DataException,
			IOException, MaxChangedBlocksException {
		EditSession es = new EditSession(new BukkitWorld(world), 999999999);
		@SuppressWarnings("deprecation")
		CuboidClipboard cc = CuboidClipboard.loadSchematic(file);
		cc.paste(es, origin, false);
	}

}