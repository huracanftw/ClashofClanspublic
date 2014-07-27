package me.crolemol.coc;

import java.io.IOException;

import me.crolemol.coc.arena.ArenaApi;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import com.sk89q.worldedit.data.DataException;

public class Eventlistener implements Listener {
	Coc plugin = Coc.plugin;
	ArenaApi arenaApi = new ArenaApi();
	@EventHandler
	public void onplayermove(PlayerMoveEvent event) throws DataException, IOException {
		if (event.getPlayer().getWorld().equals(plugin.getServer().getWorld("coc"))) {
			Location loc = event.getPlayer().getLocation();
			int ArenaUUID = arenaApi.getArenaUUID(loc); // throws DataException and IOException
			Location spawn = arenaApi.getArenaSpawn(ArenaUUID);
			if (event.getTo().getBlockX() > spawn.getBlockX() + 19){
				Vector vec = new Vector(event.getPlayer().getLocation().getX()-3,event.getPlayer().getLocation().getY()+1.5,event.getPlayer().getLocation().getZ());
				event.getPlayer().setVelocity(vec);
			}
			if(event.getTo().getBlockX() < spawn.getBlockX() - 19){
				Vector vec = new Vector(event.getPlayer().getLocation().getX()+3,event.getPlayer().getLocation().getY()+1.5,event.getPlayer().getLocation().getZ());
				event.getPlayer().setVelocity(vec);
			}
			if (event.getTo().getBlockZ() > spawn.getBlockZ() + 19){
				Vector vec = new Vector(event.getPlayer().getLocation().getX(),event.getPlayer().getLocation().getY()+1.5,event.getPlayer().getLocation().getZ()-3);
				event.getPlayer().setVelocity(vec);
			}
			if(event.getTo().getBlockZ() < spawn.getBlockZ() - 19){
				Vector vec = new Vector(event.getPlayer().getLocation().getX()+3,event.getPlayer().getLocation().getY()+1.5,event.getPlayer().getLocation().getZ()+3);
				event.getPlayer().setVelocity(vec);
			}
	
	}
	}
}
