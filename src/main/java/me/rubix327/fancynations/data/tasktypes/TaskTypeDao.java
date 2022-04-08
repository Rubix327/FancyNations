package me.rubix327.fancynations.data.tasktypes;

import me.rubix327.fancynations.data.AbstractDao;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskTypeDao extends AbstractDao<TaskType> implements ITaskTypeManager {

    public TaskTypeDao(String tableName) {
        super(tableName);
    }

    @Override
    protected TaskType loadObject(ResultSet resultSet) throws SQLException {

        int id = resultSet.getInt("Id");
        String group = resultSet.getString("Group");
        String name = resultSet.getString("Name");

        return new TaskType(id, group, name);
    }

    @Override
    public void add(TaskType dto) {
        // This class does not assume adding new instances,
        // but this method must be here to override the method from superclass.
    }
}
