package me.crolemol.coc.arena.panels;

public enum TownhallLimit {
	lv1(1,1,5,1,1,0,0,0,1,1,0,0),
	lv2(2,1,5,2,1,0,0,0,1,2,0,0),
	lv3(3,2,5,3,2,0,0,0,2,2,0,0),
	lv4(4,2,5,4,2,0,0,0,2,3,0,0),
	lv5(5,2,5,5,2,0,0,0,3,3,0,1),
	lv6(6,2,5,6,2,0,0,0,3,3,0,1),
	lv7(6,2,5,6,2,1,0,1,4,4,0,1),
	lv8(6,3,5,6,3,1,1,1,4,4,0,1),
	lv9(6,4,5,6,4,1,2,1,4,4,1,1),
	lv10(7,4,5,6,4,1,3,1,4,4,1,1);
	
	int maxgoldmine;
	int maxgoldstorage;
	int maxbuildershut;
	int maxelixircollector;
	int maxelixirstorage;
	int maxdarkelixirstorage;
	int maxdarkelixirdrill;
	int maxbarbarianking;
	int maxarmycamp;
	int maxbarracks;
	int maxarcherqueen;
	int maxspellfactory;
	TownhallLimit(int maxgoldmine2,int maxgoldstorage,int maxbuildershut,int maxelixircollector,int maxelixirstorage,int maxdarkelixirstorage,int maxdarkelixirdrill,int maxbarbarianking,int maxarmycamp,int maxbarracks,int maxarcherqueen,int maxspellfactory){
		maxgoldmine = maxgoldmine2;
		this.maxgoldstorage =maxgoldstorage;
		this.maxbuildershut =maxbuildershut;
		this.maxelixircollector =maxelixircollector;
		this.maxelixirstorage= maxelixirstorage;
		this.maxdarkelixirstorage = maxdarkelixirstorage;
		this.maxdarkelixirdrill = maxdarkelixirdrill;
		this.maxbarbarianking = maxbarbarianking;
		this.maxarmycamp = maxarmycamp;
		this.maxbarracks = maxbarracks;
		this.maxspellfactory = maxspellfactory;
		}
	
	public int getMaxGoldmines(){
		return this.maxgoldmine;
	}
	public int getMaxGoldStrage(){
		return this.maxgoldstorage;
	}
	public int getMaxBuildersHut(){
		return this.maxbuildershut;
	}
	public int getMaxElixirCollector(){
		return this.maxelixircollector;
	}
	public int getMaxElixirStorage(){
		return this.maxelixirstorage;
	}
	public int getMaxDarkElixirStorage(){
		return this.maxdarkelixirstorage;
	}
	public int getMaxDarkElixirDrill(){
		return this.maxdarkelixirdrill;
	}
	public int getMaxArmyCamp(){
		return this.maxarmycamp;
	}
	public int getMaxBarracks(){
		return this.maxbarracks;
	}
	public int getMaxArcherQueenAltar(){
		return this.maxarcherqueen;
	}
	public int getMaxSpellFactory(){
		return this.maxspellfactory;
	}
	
}
