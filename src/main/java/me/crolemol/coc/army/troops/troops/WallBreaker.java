package me.crolemol.coc.army.troops.troops;

import org.bukkit.entity.Player;

import me.crolemol.coc.arena.building.interfaces.Building;
import me.crolemol.coc.army.troops.Soldier;
import me.crolemol.coc.economy.Elixir;
import me.crolemol.coc.economy.Resource;
import me.crolemol.npc.NPCEntity;

public class WallBreaker extends Soldier{

	private WallBreaker(NPCEntity entity, Player owner, int Level) {
		super(entity, owner, Level);
	}

	@Override
	public double getMaxCocHealth() {
		return WallBreakerSpecs.values()[getLevel()-1].getHealth();
	}

	@Override
	public Resource getTrainingCost() {
		return WallBreakerSpecs.values()[getLevel()-1].getTrainingCost();
	}

	@Override
	public Building getFavouriteBuilding() {
		// TODO invent system
		return null;
	}

	@Override
	public int getMovementSpeed() {
		return 24;
	}

	@Override
	public int getHousingSpace() {
		return 2;
	}

	@Override
	public int getTrainingTime() {
		return 120;
	}

	@Override
	public double getDamagePerAttack() {
		return WallBreakerSpecs.values()[getLevel()-1].getDamage();
	}
	public int getDmageOnWallerPerAttack(){
		return WallBreakerSpecs.values()[getLevel()-1].getDamageOnWall();
	}

	@Override
	public double getAttackSpeed() {
		return 1;
	}

	@Override
	public double getRange() {
		return 1;
	}
	
	public enum WallBreakerSpecs {
		lv1(12,20, new Elixir(1000)),
		lv2(16,24, new Elixir(1500)),
		lv3(24,29,new Elixir(2000)),
		lv4(32,35, new Elixir(2500)),
		lv5(46,42,new Elixir(3000)),
		lv6(60,54, new Elixir(3500));
		int damage;
		int health;
		Elixir trainingcost;

		WallBreakerSpecs(int damage, int health, Elixir trainingcost) {
			this.damage = damage;
			this.health = health;
			this.trainingcost = trainingcost;
		}

		public int getDamage() {
			return damage;
		}
		public int getDamageOnWall(){
			return damage*40;
		}

		public int getHealth() {
			return health;
		}

		public Elixir getTrainingCost() {
			return trainingcost;
		}
	}

}
