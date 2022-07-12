package me.rubix327.fancynations.data.churches;

import me.rubix327.fancynations.data.AbstractDao;
import me.rubix327.fancynations.util.Utils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ChurchDao extends AbstractDao<Church> implements IChurchManager {

    private static ChurchDao instance = null;

    private ChurchDao(String table) {
        super(table);
    }

    public static ChurchDao getInstance(String tableName){
        if (instance == null){
            instance = new ChurchDao(tableName);
        }
        return instance;
    }

    @Override
    protected Church loadObject(ResultSet resultSet) throws SQLException {

        int id = resultSet.getInt("Id");
        int townId = resultSet.getInt("Town");
        String name = resultSet.getString("Name");
        String location = resultSet.getString("Location");
        int level = resultSet.getInt("Level");

        return new Church(id, townId, name, Utils.deserializeLocation(location), level);
    }

    @Override
    public void add(Church church) {
        String query = getQuery("churches_add");

        query = query
                .replace("@Table", table)
                .replace("@TownId", String.valueOf(church.getTownId()))
                .replace("@Name", String.valueOf(church.getName()))
                .replace("@Location", Utils.serializeLocation(church.getLocation()))
                .replace("@Level", String.valueOf(church.getLevel()));

        super.executeVoid(query);
    }

}
