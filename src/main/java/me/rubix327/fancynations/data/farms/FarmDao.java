package me.rubix327.fancynations.data.farms;

import me.rubix327.fancynations.data.AbstractDao;
import me.rubix327.fancynations.data.DataManager;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FarmDao extends AbstractDao<Farm> implements IFarmManager {

    private final String tableName;

    public FarmDao(String tableName) {
        super(tableName);
        this.tableName = tableName;
    }

    @Override
    protected Farm loadObject(ResultSet resultSet) throws SQLException {

        int id = resultSet.getInt("Id");
        int townId = resultSet.getInt("Town");
        String name = resultSet.getString("Name");
        String location = resultSet.getString("Location");
        int level = resultSet.getInt("Level");
        String loadedResource = resultSet.getString("LoadedResource");
        int amount = resultSet.getInt("Amount");

        return new Farm(id, townId, name, DataManager.deserializeLocation(location), level, loadedResource, amount);
    }

    @Override
    public void add(Farm farm) {
        String query = "INSERT INTO @Table (Town, Name, Location, Level, LoadedResource, Amount)" +
                "VALUES(@TownId, '@Name', '@Location', @Level, '@LoadedResource', @Amount)";

        query = query
                .replace("@Table", tableName)
                .replace("@TownId", String.valueOf(farm.getTownId()))
                .replace("@Name", String.valueOf(farm.getName()))
                .replace("@Location", DataManager.serializeLocation(farm.getLocation()))
                .replace("@Level", String.valueOf(farm.getLevel()))
                .replace("@LoadedResource", String.valueOf(farm.getLoadedResource()))
                .replace("@Amount", String.valueOf(farm.getAmount()));

        super.executeVoid(query);
    }

}
