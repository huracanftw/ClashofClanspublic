package me.crolemol.coc.utils;

import org.bukkit.Material;

import de.ase34.flyingblocksapi.FlyingBlock;


public class FlyingBalloonBlock extends FlyingBlock{

	public FlyingBalloonBlock(Material material, byte materialData) {
		super(material, materialData);
	}

	@Override
	public void onTick() {
		// setting it to don't fall
		
	}

}
