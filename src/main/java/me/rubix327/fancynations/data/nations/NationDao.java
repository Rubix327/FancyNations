package me.rubix327.fancynations.data.nations;

import me.rubix327.fancynations.data.AbstractDao;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NationDao extends AbstractDao<Nation> implements INationManager {

    private final String tableName;

    public NationDao(String tableName) {
        super(tableName);
        this.tableName = tableName;
    }

    @Override
    protected Nation loadObject(ResultSet resultSet) throws SQLException {

        int id = resultSet.getInt("Id");
        String name = resultSet.getString("Name");

        return new Nation(id, name);
    }

    @Override
    public void add(Nation nation) {
        String query = "INSERT INTO @Table (Name) VALUES ('@Name')";

        query = query
                .replace("@Table", tableName)
                .replace("@Name", nation.getName());

        super.executeVoid(query);
    }

}
