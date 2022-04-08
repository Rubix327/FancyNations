package me.rubix327.fancynations.data.workshops;

import me.rubix327.fancynations.data.AbstractDao;
import me.rubix327.fancynations.data.DataManager;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WorkshopDao extends AbstractDao<Workshop> implements IWorkshopManager {

    private final String tableName;

    public WorkshopDao(String tableName) {
        super(tableName);
        this.tableName = tableName;
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
        String query = "INSERT INTO @Table (Town, Name, Location, Level, LoadedResource, Amount)" +
                "VALUES(@TownId, '@Name', '@Location', @Level, '@LoadedResource', @Amount)";

        query = query
                .replace("@Table", tableName)
                .replace("@TownId", String.valueOf(workshop.getTownId()))
                .replace("@Name", String.valueOf(workshop.getName()))
                .replace("@Location", DataManager.serializeLocation(workshop.getLocation()))
                .replace("@Level", String.valueOf(workshop.getLevel()));

        super.executeVoid(query);
    }

}
