package me.rubix327.fancynations.data.taskprogresses;

import me.rubix327.fancynations.data.AbstractDao;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskProgressDao extends AbstractDao<TaskProgress> implements ITaskProgressManager {

    private static TaskProgressDao instance = null;

    private TaskProgressDao(String table) {
        super(table);
    }

    public static TaskProgressDao getInstance(String tableName){
        if (instance == null){
            instance = new TaskProgressDao(tableName);
        }
        return instance;
    }

    @Override
    protected TaskProgress loadObject(ResultSet resultSet) throws SQLException {

        int id = resultSet.getInt("Id");
        int objectiveId = resultSet.getInt("Objective");
        int progress = resultSet.getInt("Progress");

        return new TaskProgress(id, objectiveId, progress);
    }

    @Override
    public void add(TaskProgress progress) {
        String query = "INSERT INTO @Table (Objective, Progress) VALUES (@Objective, @Progress)";

        query = query
                .replace("@Table", table)
                .replace("@Objective", String.valueOf(progress.getObjectiveId()))
                .replace("@Progress", String.valueOf(progress.getProgress()));

        super.executeVoid(query);
    }

}
