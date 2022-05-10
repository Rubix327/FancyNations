package me.rubix327.fancynations.data.workshops;

import me.rubix327.fancynations.data.AbstractDao;
import me.rubix327.fancynations.data.DataManager;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WorkshopDao extends AbstractDao<Workshop> implements IWorkshopManager {

    private static WorkshopDao instance = null;

    private WorkshopDao(String table) {
        super(table);
    }

    public static WorkshopDao getInstance(String tableName){
        if (instance == null){
            instance = new WorkshopDao(tableName);
        }
        return instance;
    }

    @Override
    protected Workshop loadObject(ResultSet resultSet) throws SQLException {

        int id = resultSet.getInt("Id");
        int townId = resultSet.getInt("Town");
        String name = resultSet.getString("Name");
        String location = resultSet.getString("Location");
        int level = resultSet.getInt("Level");

        return new Workshop(id, townId, name, DataManager.deserializeLocation(location), level);
    }

    @Override
    public void add(Workshop workshop) {
        String query = getQuery("workshops_add");

        query = query
                .replace("@Table", table)
                .replace("@TownId", String.valueOf(workshop.getTownId()))
                .replace("@Name", String.valueOf(workshop.getName()))
                .replace("@Location", DataManager.serializeLocation(workshop.getLocation()))
                .replace("@Level", String.valueOf(workshop.getLevel()));

        super.executeVoid(query);
    }

}
