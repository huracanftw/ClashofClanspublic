package me.crolemol.coc.economy;

public class Gem implements Resource{
	int amount;
	public Gem(int Amount){
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

}
