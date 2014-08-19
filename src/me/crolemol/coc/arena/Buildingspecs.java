package me.crolemol.coc.arena;

public enum Buildingspecs {
	// length is z axis
	// width is x axis
	townhall("townhall", 12, 10,6),
	goldmine("goldmine", 8, 8,11);
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
