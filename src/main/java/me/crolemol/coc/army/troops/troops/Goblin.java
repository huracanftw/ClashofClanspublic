package me.crolemol.coc.army.troops.troops;

import org.bukkit.entity.Player;

import me.crolemol.coc.arena.building.interfaces.Building;
import me.crolemol.coc.army.troops.Soldier;
import me.crolemol.coc.economy.Elixir;
import me.crolemol.npc.NPCEntity;

public class Goblin extends Soldier{

	public Goblin(NPCEntity entity, Player owner, int Level) {
		super(entity, owner, Level);
	}

	@Override
	public double getMaxCocHealth() {
		return GoblinSpecs.values()[getLevel()-1].getHealth();
	}

	@Override
	public Elixir getTrainingCost() {
		return GoblinSpecs.values()[getLevel()-1].getTrainingCost();
	}

	@Override
	public Building getFavouriteBuilding() {
		// TODO Make System to define favorite buildings
		return null;
	}

	@Override
	public int getMovementSpeed() {
		return 32;
	}

	@Override
	public int getHousingSpace() {
		return 1;
	}

	@Override
	public int getTrainingTime() {
		return 30;
	}

	@Override
	public double getDamagePerAttack() {
		return GoblinSpecs.values()[getLevel()-1].getDamage();
	}
	public int getDamagePerSecondonResources(){
		return GoblinSpecs.values()[getLevel()-1].getDamage()*2;
	}

	@Override
	public double getAttackSpeed() {
		return 1;
	}

	@Override
	public double getRange() {
		return 0.4;
	}
	
	public enum GoblinSpecs {
		lv1(11, 25,new Elixir(25)),
		lv2(14, 30,new Elixir(40)),
		lv3(19, 36,new Elixir(60)),
		lv4(24, 43, new Elixir(80)),
		lv5(32, 52,new Elixir(100)),
		lv6(42, 68,new Elixir(150));
		int damage;
		int health;
		Elixir trainingcost;

		GoblinSpecs(int damage, int health, Elixir trainingcost) {
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
