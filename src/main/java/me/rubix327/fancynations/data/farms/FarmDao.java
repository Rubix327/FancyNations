package me.rubix327.fancynations.data.farms;

import me.rubix327.fancynations.data.AbstractDao;
import me.rubix327.fancynations.data.DataManager;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FarmDao extends AbstractDao<Farm> implements IFarmManager {

    private static FarmDao instance = null;

    private FarmDao(String table) {
        super(table);
    }

    public static FarmDao getInstance(String tableName){
        if (instance == null){
            instance = new FarmDao(tableName);
        }
        return instance;
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
        String query = getQuery("farms_add");

        query = query
                .replace("@Table", table)
                .replace("@TownId", String.valueOf(farm.getTownId()))
                .replace("@Name", String.valueOf(farm.getName()))
                .replace("@Location", DataManager.serializeLocation(farm.getLocation()))
                .replace("@Level", String.valueOf(farm.getLevel()))
                .replace("@LoadedResource", String.valueOf(farm.getLoadedResource()))
                .replace("@Amount", String.valueOf(farm.getAmount()));

        super.executeVoid(query);
    }

}
