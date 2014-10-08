package me.crolemol.coc.army.troops.troops;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import me.crolemol.coc.arena.building.interfaces.Building;
import me.crolemol.coc.army.troops.Soldier;
import me.crolemol.coc.economy.Elixir;
import me.crolemol.coc.economy.Resource;
import me.crolemol.npc.NPCEntity;

public class Balloon extends Soldier {

	private Balloon(NPCEntity entity, Player owner, int Level) {
		super(entity, owner, Level);
	}
	public static Balloon spawnBalloon(Location loc,int level,OfflinePlayer owner){
		
	}

	@Override
	public double getMaxCocHealth() {
		return BalloonSpecs.values()[getLevel()-1].getHealth();
	}

	@Override
	public Resource getTrainingCost() {
		return BalloonSpecs.values()[getLevel()-1].getTrainingCost();
	}

	@Override
	public Building getFavouriteBuilding() {
		// TODO invent system
		return null;
	}

	@Override
	public int getMovementSpeed() {
		return 10;
	}

	@Override
	public int getHousingSpace() {
		return 5;
	}

	@Override
	public int getTrainingTime() {
		return 8 * 60;
	}

	@Override
	public double getDamagePerAttack() {
		return BalloonSpecs.values()[getLevel()-1].getDamage();
	}

	@Override
	public double getAttackSpeed() {
		return 4;
	}

	@Override
	public double getRange() {
		return 0.5;
	}

	public enum BalloonSpecs {
		lv1(100, 150, new Elixir(2000)), lv2(128, 180, new Elixir(2500)), lv3(
				192, 216, new Elixir(3000)), lv4(288, 280, new Elixir(3500)), lv5(
				432, 390, new Elixir(4000)), lv6(648, 545, new Elixir(4500));
		int damage;
		int health;
		Elixir trainingcost;

		BalloonSpecs(int damage, int health, Elixir trainingcost) {
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
