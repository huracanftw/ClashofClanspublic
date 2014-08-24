package me.crolemol.coc.arena.building.interfaces;


public interface Storage extends Building{
	public int getCapacity();
	@Override
	public StorageBuildingSpecs[] getBuildingSpecs();
}
