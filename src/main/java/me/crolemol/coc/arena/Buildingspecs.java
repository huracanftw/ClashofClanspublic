package me.crolemol.coc.arena;

public enum Buildingspecs {
	// length is z axis
	// width is x axis
	townhall("townhall", 14, 12,8),
	goldmine("goldmine", 8, 8,11),
	goldstorage("goldstorage",8,8,11),
	elixircollector("elixircollector",8,8,11),
	buildershut("buildershut",6,6,1),
	elixirstorage("elixirstorage",8,8,11),
	darkelixirstorage("darkelixirstorage",8,8,6),
	darkelixirdrill("darkelixirdrill",8,8,6),
	armycamp("armycamp",12,12,8),
	barracks("barracks",8,8,10);
	private int returnlength;
	private int returnwidth;
	private String returnname;
	private int maxlevel;

	Buildingspecs(String name, int width, int length,int maxlevel) {
		returnname = name;
		returnlength = length;
		returnwidth = width;
		this.maxlevel = maxlevel;
	}
	public int getMaxLevel(){
		return maxlevel;
	}

	public int getLength() {
		return returnlength;
	}

	public int getWidth() {
		return returnwidth;
	}

	public String getName() {
		return returnname;
	}
}
