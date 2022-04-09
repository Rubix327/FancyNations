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
        String query = "INSERT INTO @Table (Town, Name, Amount) VALUES (@Town, '@Name', @Amount)";

        query = query
                .replace("@Table", table)
                .replace("@Town", String.valueOf(resource.getTownId()))
                .replace("@Name", String.valueOf(resource.getName()))
                .replace("@Amount", String.valueOf(resource.getAmount()));

        super.executeVoid(query);
    }

}
