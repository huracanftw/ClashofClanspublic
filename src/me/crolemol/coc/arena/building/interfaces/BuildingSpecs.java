package me.crolemol.coc.arena.building.interfaces;

import me.crolemol.coc.economy.Resource;

public interface BuildingSpecs {
	
	public Resource getUpgradePrice();
	public int getGainExpOnUpgrade();
	public int getUpgradeTime();
	public int getHealth();
	public int getMinTownhallLevel();

}