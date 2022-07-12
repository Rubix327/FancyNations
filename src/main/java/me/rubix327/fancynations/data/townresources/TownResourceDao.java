package me.rubix327.fancynations.data.townresources;

import me.rubix327.fancynations.data.AbstractDao;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TownResourceDao extends AbstractDao<TownResource> implements ITownResourceManager {

    private static TownResourceDao instance = null;

    private TownResourceDao(String table) {
        super(table);
    }

    public static TownResourceDao getInstance(String tableName){
        if (instance == null){
            instance = new TownResourceDao(tableName);
        }
        return instance;
    }

    @Override
    protected TownResource loadObject(ResultSet resultSet) throws SQLException {

        int id = resultSet.getInt("Id");
        int townId = resultSet.getInt("Town");
        String name = resultSet.getString("Name");
        int amount = resultSet.getInt("Amount");

        return new TownResource(id, townId, name, amount);
    }

    @Override
    public void add(TownResource resource) {
        String query = getQuery("town_resources_add");

        query = query
                .replace("@Table", table)
                .replace("@TownId", String.valueOf(resource.getTownId()))
                .replace("@ResourceName", String.valueOf(resource.getName()))
                .replace("@Amount", String.valueOf(resource.getAmount()));

        super.executeVoid(query);
    }

    public boolean exists(int townId, String resourceName) {
        String query = getQuery("town_resources_exists");

        query = query
                .replace("@Table", this.table)
                .replace("@TownId", String.valueOf(townId))
                .replace("@ResourceName", resourceName);

        return executeBool(query);
    }

    public TownResource get(int townId, String resourceName) {
        String query = getQuery("town_resources_get");

        query = query
                .replace("@Table", this.table)
                .replace("@TownId", String.valueOf(townId))
                .replace("@ResourceName", resourceName);

        return executeObject(query);
    }

}
