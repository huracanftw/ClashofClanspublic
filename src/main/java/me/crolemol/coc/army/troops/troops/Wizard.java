package me.crolemol.coc.army.troops.troops;

import org.bukkit.entity.Player;

import me.crolemol.coc.arena.building.interfaces.Building;
import me.crolemol.coc.army.troops.Soldier;
import me.crolemol.coc.economy.Elixir;
import me.crolemol.coc.economy.Resource;
import me.crolemol.npc.NPCEntity;

public class Wizard extends Soldier{

	private Wizard(NPCEntity entity, Player owner, int Level) {
		super(entity, owner, Level);
	}

	@Override
	public double getMaxCocHealth() {
		return WizardSpecs.values()[getLevel()-1].getHealth();
	}

	@Override
	public Resource getTrainingCost() {
		return WizardSpecs.values()[getLevel()-1].getTrainingCost();
	}

	@Override
	public Building getFavouriteBuilding() {
		// TODO invent system
		return null;
	}

	@Override
	public int getMovementSpeed() {
		return 16;
	}

	@Override
	public int getHousingSpace() {
		return 4;
	}

	@Override
	public int getTrainingTime() {
		return 8*60;
	}

	@Override
	public double getDamagePerAttack() {
		return WizardSpecs.values()[getLevel()-1].getDamage();
	}

	@Override
	public double getAttackSpeed() {
		return 1.5;
	}

	@Override
	public double getRange() {
		return 3;
	}
	
	public enum WizardSpecs {
		lv1(75,75, new Elixir(1500)),
		lv2(105,90, new Elixir(2000)),
		lv3(135,108,new Elixir(2500)),
		lv4(187.5,130, new Elixir(3000)),
		lv5(255,156,new Elixir(3500)),
		lv6(270,164, new Elixir(4000));
		double damage;
		int health;
		Elixir trainingcost;

		WizardSpecs(double damage, int health, Elixir trainingcost) {
			this.damage = damage;
			this.health = health;
			this.trainingcost = trainingcost;
		}

		public double getDamage() {
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
