package me.crolemol.coc.army.troops.troops;

import org.bukkit.entity.Player;

import me.crolemol.coc.arena.building.interfaces.Building;
import me.crolemol.coc.army.troops.Soldier;
import me.crolemol.coc.economy.Elixir;
import me.crolemol.coc.economy.Resource;
import me.crolemol.npc.NPCEntity;

public class Giant extends Soldier{

	public Giant(NPCEntity entity, Player owner, int Level) {
		super(entity, owner, Level);
	}

	@Override
	public double getMaxCocHealth() {
		return GaintSpecs.values()[getLevel()-1].getHealth();
	}

	@Override
	public Resource getTrainingCost() {
		return GaintSpecs.values()[getLevel()-1].getTrainingCost();
	}

	@Override
	public Building getFavouriteBuilding() {
		// TODO invent method to return favourite building
		return null;
	}

	@Override
	public int getMovementSpeed() {
		return 12;
	}

	@Override
	public int getHousingSpace() {
		return 5;
	}

	@Override
	public int getTrainingTime() {
		return 120;
	}

	@Override
	public double getDamagePerAttack() {
		return GaintSpecs.values()[getLevel()-1].getDamage();
	}

	@Override
	public double getAttackSpeed() {
		return 2;
	}

	@Override
	public double getRange() {
		return 1;
	}
	
	public enum GaintSpecs {
		lv1(22,300, new Elixir(500)),
		lv2(28,360, new Elixir(1000)),
		lv3(38,430,new Elixir(1500)),
		lv4(48,520, new Elixir(2000)),
		lv5(62,670,new Elixir(2500)),
		lv6(86,940, new Elixir(3000));
		int damage;
		int health;
		Elixir trainingcost;

		GaintSpecs(int damage, int health, Elixir trainingcost) {
			this.damage = damage;
			this.health = health;
			this.trainingcost = trainingcost;
		}

		public int getDamage() {
			return damage;
		}

		public int getHealth() {
			return health;
		}

		public Elixir getTrainingCost() {
			return trainingcost;
		}
	}

}
