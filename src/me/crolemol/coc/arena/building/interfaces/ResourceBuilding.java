package me.crolemol.coc.arena.building.interfaces;



public interface ResourceBuilding extends Building {

	public int getProduction();
	public int getCapacity();
	public int getCollectable();
	public void Collect();
}
