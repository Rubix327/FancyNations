package me.rubix327.fancynations.data.defendtowers;

import me.rubix327.fancynations.data.AbstractDao;
import me.rubix327.fancynations.data.DataManager;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DefendTowerDao extends AbstractDao<DefendTower> implements IDefendTowerManager {

    private final String tableName;

    public DefendTowerDao(String tableName) {
        super(tableName);
        this.tableName = tableName;
    }

    @Override
    protected DefendTower loadObject(ResultSet resultSet) throws SQLException {

        int id = resultSet.getInt("Id");
        int townId = resultSet.getInt("Town");
        String name = resultSet.getString("Name");
        String location = resultSet.getString("Location");
        int level = resultSet.getInt("Level");
        String loadedResource = resultSet.getString("LoadedResource");
        int amount = resultSet.getInt("Amount");

        return new DefendTower(
                id, townId, name, DataManager.deserializeLocation(location), level, loadedResource, amount);
    }

    @Override
    public void add(DefendTower tower) {
        String query = "INSERT INTO @Table (Town, Name, Location, Level, LoadedResource, Amount)" +
                "VALUES(@TownId, '@Name', '@Location', @Level, '@LoadedResource', @Amount)";

        query = query
                .replace("@Table", tableName)
                .replace("@TownId", String.valueOf(tower.getTownId()))
                .replace("@Name", String.valueOf(tower.getName()))
                .replace("@Location", DataManager.serializeLocation(tower.getLocation()))
                .replace("@Level", String.valueOf(tower.getLevel()))
                .replace("@LoadedResource", String.valueOf(tower.getLoadedResource()))
                .replace("@Amount", String.valueOf(tower.getAmount()));

        super.executeVoid(query);
    }

}
