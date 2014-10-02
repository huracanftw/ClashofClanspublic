package me.crolemol.coc.army.troops;
public enum SoldierType {
	Barbarian(1),
	Archer(1),
	Goblin(1);
	int housingspace;
	private SoldierType(int HousingSpace){
		this.housingspace = HousingSpace;
	}
	public int getHousingSpace(){
		return this.housingspace;
	}
	public static SoldierType getSoldierType(String name){
		for(SoldierType type : SoldierType.values()){
			if(type.name().equals(name)){
				return type;
			}
		}
		return null;
	}
}
