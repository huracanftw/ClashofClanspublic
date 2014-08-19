package me.crolemol.coc.arena.panels;

public enum TownhallLimit {
	lv1(1),
	lv2(2),
	lv3(3),
	lv4(4),
	lv5(5),
	lv6(6),
	lv7(6),
	lv8(6),
	lv9(6),
	lv10(7);
	
	int maxgoldmine;
	TownhallLimit(int maxgoldmine2){
		maxgoldmine = maxgoldmine2;
	}
	
	public int getMaxGoldmines(){
		return maxgoldmine;
	}
}
