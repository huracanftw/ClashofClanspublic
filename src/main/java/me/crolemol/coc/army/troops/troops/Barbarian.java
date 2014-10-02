package me.crolemol.coc.army.troops.troops;

import java.sql.SQLException;

import me.crolemol.coc.Coc;
import me.crolemol.coc.arena.building.interfaces.Building;
import me.crolemol.coc.army.troops.Soldier;
import me.crolemol.coc.army.troops.SoldierType;
import me.crolemol.coc.economy.Elixir;
import me.crolemol.npc.NPC;
import me.crolemol.npc.NPCEntity;
import me.crolemol.npc.NPCFactory;
import me.crolemol.npc.NPCProfile;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Barbarian extends Soldier{
	private Barbarian(NPCEntity npc,Player owner,int Level){
		super(npc,owner,Level);
	}
	public static Soldier spawnBarbarian(Location location,Player owner,int Level){
		NPCFactory factory = new NPCFactory(Coc.getPlugin());
		NPC npc = factory.spawnHumanNPC(location, new NPCProfile("Barbarian"));
		try {
			Coc.getPlugin().getDataBase().query("INSERT INTO Troops(owner,entityid,Level,armycampID,SoldierType) VALUES ('"+owner.getUniqueId()+"','"+npc.getBukkitEntity().getUniqueId()+"',"+Level+",1,'"+SoldierType.Barbarian.name()+"')").close();
			Coc.getPlugin().getDataBase().getConnection().commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new Barbarian((NPCEntity) npc,owner,Level);
	}
	@Override
	public double getMaxCocHealth() {
		return BarbarianSpecs.values()[getLevel()-1].getHealth();
	}

	@Override
	public Elixir getTrainingCost() {
		return BarbarianSpecs.values()[getLevel()-1].getTrainingCost();
	}

	@Override
	public Building getFavouriteBuilding() {
		return null;
	}

	@Override
	public int getMovementSpeed() {
		return 16;
	}

	@Override
	public int getHousingSpace() {
		return 1;
	}

	@Override
	public int getTrainingTime() {
		return 20;
	}
	@Override
	public double getDamagePerAttack() {
	return BarbarianSpecs.values()[getLevel()-1].getDamage();
	}

	public enum BarbarianSpecs {
		lv1(8, 45, new Elixir(25)),
		lv2(11, 54, new Elixir(40)),
		lv3(14, 65,new Elixir(60)),
		lv4(18, 78, new Elixir(80)),
		lv5(23, 95,new Elixir(100)),
		lv6(26, 110, new Elixir(150));
		int damage;
		int health;
		Elixir trainingcost;

		BarbarianSpecs(int damage, int health, Elixir trainingcost) {
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
	@Override
	public double getAttackSpeed() {
		return 1;
	}
	@Override
	public double getRange() {
		return 0.4;
	}
	
}
