package me.crolemol.coc.arena;

public enum BuildingType {
	// length is z axis
	// width is x axis
	Townhall("townhall", 14, 12,10),
	GoldMine("goldmine", 8, 8,11),
	GoldStorage("goldstorage",8,8,11),
	ElixirCollector("elixircollector",8,8,11),
	BuildersHut("buildershut",6,6,1),
	ElixirStorage("elixirstorage",8,8,11),
	DarkElixirStorge("darkelixirstorage",8,8,6),
	DarkelixirDrill("darkelixirdrill",8,8,6),
	ArmyCamp("armycamp",12,12,8),
	Barracks("barracks",8,8,10),
	BarbarianKingAltar("barbariankingaltar",8,8,1),
	ArcherQueenAltar("archerqueenaltar",8,8,1),
	SpellFactory("spellfactory",8,8,5),
	Cannon("cannon",8,8,12),
	ArcherTower("archertower",6,6,12),
	Mortar("mortar",8,8,8),
	WizardTower("wizardtower",8,8,8),
	AirDefense("airdefense",8,8,8),
	HiddenTesla("hiddentesla",7,7,8);
	private int returnlength;
	private int returnwidth;
	private String returnname;
	private int maxlevel;

	BuildingType(String name, int width, int length,int maxlevel) {
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
