package me.crolemol.coc;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

public class Nullchunkgenerator extends ChunkGenerator{
	public byte[] generate(World world, Random random, int cx, int cz)
	{
		return new byte[65536];
	}

	@Override
	public Location getFixedSpawnLocation(World world, Random random)
	{
		return new Location(world, 0, 64, 0);
	}
}
