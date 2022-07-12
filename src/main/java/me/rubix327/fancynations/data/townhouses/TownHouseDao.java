package me.rubix327.fancynations.data.townhouses;

import me.rubix327.fancynations.data.AbstractDao;
import me.rubix327.fancynations.util.Utils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TownHouseDao extends AbstractDao<TownHouse> implements ITownHouseManager {

    private static TownHouseDao instance = null;

    private TownHouseDao(String table) {
        super(table);
    }

    public static TownHouseDao getInstance(String tableName){
        if (instance == null){
            instance = new TownHouseDao(tableName);
        }
        return instance;
    }

    @Override
    protected TownHouse loadObject(ResultSet resultSet) throws SQLException {

        int id = resultSet.getInt("Id");
        int townId = resultSet.getInt("TownId");
        int ownerId = resultSet.getInt("OwnerId");
        String location = resultSet.getString("Location");

        return new TownHouse(id, townId, ownerId, Utils.deserializeLocation(location));
    }

    @Override
    public void add(TownHouse house) {

        String query = getQuery("town_houses_add");

        query = query
                .replace("@Table", table)
                .replace("@TownId", String.valueOf(house.getTownId()))
                .replace("@OwnerId", String.valueOf(house.getOwnerId()))
                .replace("@Location", Utils.serializeLocation(house.getLocation()));

        super.executeVoid(query);
    }

}
