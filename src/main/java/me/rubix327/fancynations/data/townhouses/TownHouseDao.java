package me.rubix327.fancynations.data.townhouses;

import me.rubix327.fancynations.data.AbstractDao;
import me.rubix327.fancynations.data.DataManager;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TownHouseDao extends AbstractDao<TownHouse> implements ITownHouseManager {

    private final String tableName;

    public TownHouseDao(String tableName) {
        super(tableName);
        this.tableName = tableName;
    }

    @Override
    protected TownHouse loadObject(ResultSet resultSet) throws SQLException {

        int id = resultSet.getInt("Id");
        int townId = resultSet.getInt("Town");
        int ownerId = resultSet.getInt("Owner");
        String location = resultSet.getString("Location");

        return new TownHouse(id, townId, ownerId, DataManager.deserializeLocation(location));
    }

    @Override
    public void add(TownHouse house) {

        String query = "INSERT INTO @Table (Town, Owner, Location)" +
                "VALUES(@Town, @Owner, '@Location')";

        query = query
                .replace("@Table", tableName)
                .replace("@Town", String.valueOf(house.getTownId()))
                .replace("@Owner", String.valueOf(house.getOwnerId()))
                .replace("@Location", DataManager.serializeLocation(house.getLocation()));

        super.executeVoid(query);
    }

}
