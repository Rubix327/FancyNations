package me.rubix327.fancynations.data.nations;

import me.rubix327.fancynations.data.AbstractDao;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NationDao extends AbstractDao<Nation> implements INationManager {

    private static NationDao instance = null;

    private NationDao(String table) {
        super(table);
    }

    public static NationDao getInstance(String tableName){
        if (instance == null){
            instance = new NationDao(tableName);
        }
        return instance;
    }

    @Override
    protected Nation loadObject(ResultSet resultSet) throws SQLException {

        int id = resultSet.getInt("Id");
        String name = resultSet.getString("Name");

        return new Nation(id, name);
    }

    @Override
    public void add(Nation nation) {
        String query = getQuery("nations_add");

        query = query
                .replace("@Table", table)
                .replace("@Name", nation.getName());

        super.executeVoid(query);
    }

}
