package me.crolemol.coc.arena.building.interfaces;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

import me.crolemol.coc.Coc;
import me.crolemol.coc.economy.PlayerData;
import me.crolemol.coc.economy.Resource;



public abstract class ResourceBuilding extends Building {

	protected ResourceBuilding(int level) {
		super(level);
	}

	protected ResourceBuilding(OfflinePlayer owner, Location loc, int level,
			int BuildingID, boolean isreal) {
		super(owner, loc, level, BuildingID, isreal);
	}

	public abstract int getProduction();
	public abstract Resource getProductionType();

	public abstract int getCapacity();

	public Resource getCollectable() {
		Resource resource = getProductionType();
		resource.setAmount(0);
		if (getLevel() == 0) {
			return resource;
		}
		if (isRealBuilding() == false) {
			return resource;
		}
		ResourceBuildingSpecs[] spec =  getBuildingSpecs();
		Calendar cal = Calendar.getInstance();
		Long caltime = cal.getTimeInMillis() / 60 / 1000;
		ResultSet result = Coc.getPlugin().getDataBase().query(
				"SELECT LastCollect FROM Buildings WHERE owner = '"
						+ getOwner().getUniqueId() + "' AND BuildingID = "
						+ getBuildingID() + " AND BuildingName = '"
						+ getBuildingName()+"'");
		Long time1 = (long) 0;
		try {
			time1 = result.getLong("LastCollect");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		double time2 = caltime - time1;
		int elixir = (int) (time2 * Math.floor(spec[getLevel() - 1]
				.getProduction() / 60));
		resource.setAmount(elixir);
		return resource;
	}

	public void Collect() {
		if (super.getLevel() == 0) {
			return;
		}
		if (super.isRealBuilding() == false) {
			return;
		}
		PlayerData.Give(getCollectable(), super.getOwner());
		setCollectable(0);
	}

	public void setCollectable(int collectable) {
		Calendar cal = Calendar.getInstance();
		Long caltime = cal.getTimeInMillis() / 60 / 1000;
		if (isRealBuilding() == true) {
			Long lastcollect;
			double time = collectable / (getProduction() / 60);
			lastcollect = (long) (caltime - time);
			Coc.getPlugin()
					.getDataBase()
					.query("UPDATE Buildings SET LastCollect="
							+ lastcollect + " WHERE owner = '"
							+ getOwner().getUniqueId() + "' AND BuildingID = "
							+ getBuildingID() + " AND BuildingName = '"
							+ getBuildingName()+"'");
		}
	}
	@Override
	public abstract ResourceBuildingSpecs[] getBuildingSpecs();
}
