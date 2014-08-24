package me.crolemol.coc;

import java.io.File;
import java.io.IOException;

import me.crolemol.coc.arena.Base;
import me.crolemol.coc.arena.panels.BuildingShop;
import me.crolemol.coc.scoreboard.ScoreboardApi;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EmptyClipboardException;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.LocalWorld;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.data.DataException;

@SuppressWarnings("deprecation")
public class Eventlistener implements Listener {
	private Coc plugin = Coc.plugin;
	
	@EventHandler
	public void onplayermove(PlayerMoveEvent event) throws DataException, IOException {
		if (event.getPlayer().getWorld().equals(plugin.getServer().getWorld("coc"))) {
			if(!(event.getPlayer().hasPermission("coc.edge.bypass"))){
			Base base = Base.getBase(event.getPlayer().getLocation());	
			Location loc = event.getPlayer().getLocation();
			CuboidClipboard cc = CuboidClipboard.loadSchematic(new File(plugin.getDataFolder()+"/schematics/ground.schematic"));
			Location spawn = base.getArenaSpawn();
			Location corner1 = new Location(plugin.getServer().getWorld("coc"),spawn.getX()+cc.getLength()/2-10, spawn.getY(), spawn.getZ()+cc.getLength()/2-10);
			Location corner2 = new Location(plugin.getServer().getWorld("coc"),spawn.getX()-cc.getLength()/2+10, spawn.getY(), spawn.getZ()-cc.getLength()/2+10);
			if(checkIfInArea(corner1,corner2,loc)==false && (!(event.getPlayer().getVelocity().equals(getEdgeVector(event.getPlayer()))))){
				event.getPlayer().setVelocity(getEdgeVector(event.getPlayer()));
				event.getPlayer().sendMessage(ChatColor.RED+"[ClashofClans] you cannot leave your base this way!");
			}
	}
		}
	}
	@EventHandler
	public void onentityspawn(CreatureSpawnEvent event){
		if(event.getLocation().getWorld().equals(plugin.getServer().getWorld("coc"))){
			if(event.getEntity().getType() == null){
				return;
			}
			if(!(event.getEntity().getType().equals(EntityType.VILLAGER))){
				event.setCancelled(true);
			}
		}
	}
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event){
		Action action = event.getAction();
		ItemStack is= event.getItem();
		
		if(action== Action.PHYSICAL || is == null || is.getType().equals(Material.AIR)){
			return;
		}
		if(is.getType() == Material.STICK && is.getItemMeta().getDisplayName().equals("SurfaceStick")){
			WorldEditPlugin we = (WorldEditPlugin) plugin.getServer().getPluginManager().getPlugin("WorldEdit");
			 
			int loc = event.getClickedBlock().getLocation().getBlockY();
	        LocalSession localSession = we.getSession(event.getPlayer());
	        try {
				CuboidClipboard cc = localSession.getClipboard();
				int counter = cc.getOrigin().getBlockY() - loc;
				cc.setOffset(new com.sk89q.worldedit.Vector(0,counter,0));
				event.getPlayer().sendMessage(cc.getOffset()+"");

				event.getPlayer().sendMessage(ChatColor.GOLD+"[ClashofClans] new surface is now set!");
			} catch (EmptyClipboardException e) {
				e.printStackTrace();
			}
	        
	 
		}
		if(!(event.getPlayer().getWorld().equals(plugin.getServer().getWorld("coc")))){
			return;
		}
		if(!(is.hasItemMeta())){
			return;
		}
		if(is.getType() == Material.BOOK && is.getItemMeta().getDisplayName().equals("Shop")){
			BuildingShop shop = new BuildingShop();
			shop.OpenMainShopGUI(event.getPlayer());
			return;
		}
	}
	@EventHandler 
	public void onInventoryClick(InventoryClickEvent event){
		if(ChatColor.stripColor(event.getInventory().getName()).equalsIgnoreCase("BuildingShop")){
			Player player = (Player) event.getWhoClicked();
			event.setCancelled(true);
			if(event.getCurrentItem()==null || !event.getCurrentItem().hasItemMeta()){
				return;
			}
			switch(event.getCurrentItem().getType()){
			case DIAMOND_SWORD:
				break;
			case IRON_CHESTPLATE:
				break;
			case FLOWER_POT_ITEM:
				break;
			case IRON_PICKAXE:
				break;
			case DIAMOND_CHESTPLATE:
				break;
			case DIAMOND:
				break;
			default: player.closeInventory();
				
			}
			
		}
		
	}
	@EventHandler
	public void onWorldChange(PlayerChangedWorldEvent event){
		if(!event.getPlayer().getWorld().equals(plugin.getServer().getWorld("coc"))){
			ScoreboardApi.removeCurrencyBoardt(event.getPlayer());
			return;
		}
		if(event.getPlayer().getWorld().equals((plugin.getServer().getWorld("coc")))){
			event.getPlayer().setHealth(20);
			event.getPlayer().setFoodLevel(20);
		}
	}
	@EventHandler
	public void onFoodlevelChange(FoodLevelChangeEvent event){
		if(event.getEntity() instanceof Player){
			Player player = (Player) event.getEntity();
			if(player.getWorld().equals(plugin.getServer().getWorld("coc"))){
				event.setCancelled(true);
			}
		}
	}
	@EventHandler
	public void onitemSpawn(ItemSpawnEvent event){
		if(event.getEntity().getWorld().equals(plugin.getServer().getWorld("coc"))){
			event.setCancelled(true);
		}
	}
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event){
		if(event.getPlayer().getWorld().equals(plugin.getServer().getWorld("coc"))){
			if(!event.getPlayer().hasPermission("coc.block.break")){
				event.setCancelled(true);
			}
		}
	}

	private Vector getEdgeVector(Player player){
		Base base = Base.getBase(player.getLocation());
		Location spawn = base.getArenaSpawn();
		Vector start = new Vector(player.getLocation().getBlockX(),player.getLocation().getBlockY(),player.getLocation().getBlockZ());
		Vector direction = spawn.toVector().subtract(player.getLocation().toVector());
		BlockIterator  bi = new BlockIterator(player.getWorld(), start, direction, 0, (int)player.getLocation().distance(spawn));
		bi.next();
		bi.next();
		bi.next();
		bi.next();
		Location loc = bi.next().getLocation();
		loc.setY(loc.getBlockY()+2);
		double dX = player.getLocation().getX() - loc.getX();
		double dY = player.getLocation().getY() - loc.getY();
		double dZ = player.getLocation().getZ() - loc.getZ();
		double yaw = Math.atan2(dZ, dX);
		double pitch = Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + Math.PI;
		double X = Math.sin(pitch) * Math.cos(yaw);
		double Y = Math.sin(pitch) * Math.sin(yaw);
		double Z = Math.cos(pitch);
		Vector finalvector = new Vector(X, Z, Y);
		return finalvector;
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
