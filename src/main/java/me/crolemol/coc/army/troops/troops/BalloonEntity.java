package me.crolemol.coc.army.troops.troops;

import java.io.File;
import java.io.IOException;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.data.DataException;

import me.crolemol.coc.Coc;
import me.crolemol.coc.utils.NormalFlyingBlock;
import me.crolemol.npc.NPCEntity;
import me.crolemol.npc.NPCNetworkManager;
import me.crolemol.npc.NPCProfile;

public class BalloonEntity extends NPCEntity{

	private BalloonEntity(World world, NPCProfile profile,
			NPCNetworkManager networkManager) {
		super(world, profile, networkManager);
	}
	@SuppressWarnings("deprecation")
	public static void spawnBalloon(final Location loc){
		File balloonschem = new File(Coc.getPlugin().getDataFolder()+"/schematics/balloon/balloon.schematic");
		CuboidClipboard cc = null;
		try {
			cc = CuboidClipboard.loadSchematic(balloonschem);
		} catch (DataException | IOException e) {
			e.printStackTrace();
		}
		
		for (int xcounter = 0; xcounter < cc.getWidth(); xcounter++) {
			for (int zcounter = 0; zcounter < cc.getLength(); zcounter++) {
				for (int ycounter = 0; ycounter < cc.getHeight(); ycounter++) {
					Location spawnloc = new Location(Coc.getPlugin().getServer().getWorld(
							"coc"), loc.getX()
							+ xcounter, loc.getY()
							+ ycounter + cc.getOffset().getBlockY(), loc.getZ()
							+ zcounter);
					Vector vec = new Vector(xcounter, ycounter, zcounter);
					BaseBlock block = cc.getBlock(vec);
					int materialid = block.getId();
					Material material = Material.getMaterial(materialid);
					if(material.equals(Material.AIR) == false){
					NormalFlyingBlock flyingblock = new NormalFlyingBlock(material,(byte) block.getData());
					flyingblock.spawn(spawnloc);
					}
				}
			}
		}
		
	}

}
