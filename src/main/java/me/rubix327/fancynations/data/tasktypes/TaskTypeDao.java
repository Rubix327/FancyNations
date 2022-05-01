package me.rubix327.fancynations.data.tasktypes;

import me.rubix327.fancynations.data.AbstractDao;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskTypeDao extends AbstractDao<TaskType> implements ITaskTypeManager {

    private static TaskTypeDao instance = null;

    private TaskTypeDao(String tableName) {
        super(tableName);
    }

    public static TaskTypeDao getInstance(String tableName){
        if (instance == null){
            instance = new TaskTypeDao(tableName);
        }
        return instance;
    }

    @Override
    protected TaskType loadObject(ResultSet resultSet) throws SQLException {

        int id = resultSet.getInt("Id");
        String group = resultSet.getString("TypeGroup");
        String name = resultSet.getString("Name");

        return new TaskType(id, group, name);
    }

    @Override
    public void add(TaskType dto) {
        String query = "INSERT INTO @Table (TypeGroup, Name) VALUES ('@TypeGroup', '@Name')";

        query = query
                .replace("@Table", table)
                .replace("@TypeGroup", String.valueOf(dto.getGroup()))
                .replace("@Name", String.valueOf(dto.getName()));

        super.executeVoid(query);
    }
}
