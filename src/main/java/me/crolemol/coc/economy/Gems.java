package me.crolemol.coc.economy;

public class Gems implements Resource{
	int amount;
	public Gems(int Amount){
		amount = Amount;
	}
	@Override
	public int getAmount() {
		return amount;
	}

	@Override
	public void setAmount(int Amount) {
		amount = Amount;
		
	}
	@Override
	public ResourceType getResourceType() {
		return ResourceType.Gems;
	}

}
