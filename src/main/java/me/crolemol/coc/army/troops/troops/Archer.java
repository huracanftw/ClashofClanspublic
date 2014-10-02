package me.crolemol.coc.army.troops.troops;

import org.bukkit.entity.Player;

import me.crolemol.coc.arena.building.interfaces.Building;
import me.crolemol.coc.army.troops.Soldier;
import me.crolemol.coc.economy.Elixir;
import me.crolemol.npc.NPCEntity;

public class Archer extends Soldier{

	public Archer(NPCEntity entity, Player owner, int Level) {
		super(entity, owner, Level);
	}

	@Override
	public double getMaxCocHealth() {
		return ArcherSpecs.values()[getLevel()-1].getHealth();
	}

	@Override
	public Elixir getTrainingCost() {
		return ArcherSpecs.values()[getLevel()-1].getTrainingCost();
	}

	@Override
	public Building getFavouriteBuilding() {
		return null;
	}

	@Override
	public int getMovementSpeed() {
		return 24;
	}

	@Override
	public int getHousingSpace() {
		return 1;
	}

	@Override
	public int getTrainingTime() {
		return 25;
	}

	@Override
	public double getDamagePerAttack() {
		return ArcherSpecs.values()[getLevel()-1].getDamage();
	}

	@Override
	public double getAttackSpeed() {
		return 1;
	}

	@Override
	public double getRange() {
		return 3.5;
	}
	
	public enum ArcherSpecs {
		lv1(7, 20, new Elixir(50)),
		lv2(9, 23, new Elixir(80)),
		lv3(12, 28,new Elixir(120)),
		lv4(16, 33, new Elixir(160)),
		lv5(20, 40,new Elixir(200)),
		lv6(22, 44, new Elixir(300)),
		lv7(25, 48, new Elixir(400));
		int damage;
		int health;
		Elixir trainingcost;

		ArcherSpecs(int damage, int health, Elixir trainingcost) {
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
