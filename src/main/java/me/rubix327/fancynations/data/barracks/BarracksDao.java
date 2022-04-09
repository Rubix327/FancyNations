package me.rubix327.fancynations.data.barracks;

import me.rubix327.fancynations.data.AbstractDao;
import me.rubix327.fancynations.data.DataManager;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BarracksDao extends AbstractDao<Barracks> implements IBarracksManager{

    private static BarracksDao instance = null;

    private BarracksDao(String table) {
        super(table);
    }

    public static BarracksDao getInstance(String tableName){
        if (instance == null){
            instance = new BarracksDao(tableName);
        }
        return instance;
    }

    @Override
    protected Barracks loadObject(ResultSet resultSet) throws SQLException {

        int id = resultSet.getInt("Id");
        int townId = resultSet.getInt("Town");
        String name = resultSet.getString("Name");
        String location = resultSet.getString("Location");
        int level = resultSet.getInt("Level");

        return new Barracks(id, townId, name, DataManager.deserializeLocation(location), level);
    }

    @Override
    public void add(Barracks barracks) {
        String query = "INSERT INTO @Table (Town, Name, Location, Level)" +
                "VALUES(@TownId, '@Name', '@Location', @Level)";

        query = query
                .replace("@Table", table)
                .replace("@TownId", String.valueOf(barracks.getTownId()))
                .replace("@Name", String.valueOf(barracks.getName()))
                .replace("@Location", DataManager.serializeLocation(barracks.getLocation()))
                .replace("@Level", String.valueOf(barracks.getLevel()));

        super.executeVoid(query);
    }
}
