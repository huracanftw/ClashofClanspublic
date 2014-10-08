package me.crolemol.coc.economy;

public class Gold implements Resource{
	int Amount;
	public Gold(int Amount){
		this.Amount = Amount;
	}

	@Override
	public int getAmount() {
		return Amount;
	}

	@Override
	public void setAmount(int Amount) {
		this.Amount = Amount;
	}

	@Override
	public ResourceType getResourceType() {
		return ResourceType.Gold;
	}

}
