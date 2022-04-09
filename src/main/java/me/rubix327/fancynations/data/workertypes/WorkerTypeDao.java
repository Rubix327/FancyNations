package me.rubix327.fancynations.data.workertypes;

import me.rubix327.fancynations.data.AbstractDao;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WorkerTypeDao extends AbstractDao<WorkerType> implements IWorkerTypeManager {

    private static WorkerTypeDao instance = null;

    private WorkerTypeDao(String table) {
        super(table);
    }

    public static WorkerTypeDao getInstance(String tableName){
        if (instance == null){
            instance = new WorkerTypeDao(tableName);
        }
        return instance;
    }

    @Override
    protected WorkerType loadObject(ResultSet resultSet) throws SQLException {

        int id = resultSet.getInt("Id");
        String name = resultSet.getString("Name");

        return new WorkerType(id, name);
    }

    @Override
    public void add(WorkerType dto) {
        // This class does not assume adding new instances,
        // but this method must be here to override the method from the interface.
    }
}
