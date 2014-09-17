package me.crolemol.coc.arena.building.interfaces;

import me.crolemol.coc.economy.Resource;

public interface Sellable {
	
	public Resource getSellPrice();
	public void setSellPrice();
}
