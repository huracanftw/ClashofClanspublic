package me.crolemol.coc.economy;

public class Elixir implements Resource{
	int Amount;
	public Elixir(int Amount){
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
