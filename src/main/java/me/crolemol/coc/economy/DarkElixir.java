package me.crolemol.coc.economy;

public class DarkElixir implements Resource{

	int Amount;
	public DarkElixir(int Amount){
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


}
