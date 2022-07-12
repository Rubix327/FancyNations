package me.rubix327.fancynations.data.taskprogresses;

import me.rubix327.fancynations.data.AbstractDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

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
        int takenTaskId = resultSet.getInt("TakenTask");
        int progress = resultSet.getInt("Progress");

        return new TaskProgress(id, objectiveId, takenTaskId, progress);
    }

    public boolean exists(int objectiveId, int takenTaskId){
        String query = getQuery("task_progress_exists");

        query = query
                .replace("@Table", this.table)
                .replace("@ObjectiveId", String.valueOf(objectiveId))
                .replace("@TakenTaskId", String.valueOf(takenTaskId));

        return executeBool(query);
    }

    @Override
    public void add(TaskProgress progress) {
        String query = getQuery("task_progresses_add");

        query = query
                .replace("@Table", table)
                .replace("@ObjectiveId", String.valueOf(progress.getObjectiveId()))
                .replace("@TakenTaskId", String.valueOf(progress.getTakenTaskId()))
                .replace("@Progress", String.valueOf(progress.getProgress()));

        executeVoid(query);
    }

    public TaskProgress get(int objectiveId, int takenTaskId){
        String query = getQuery("task_progresses_get");

        query = query
                .replace("@Table", this.table)
                .replace("@ObjectiveId", String.valueOf(objectiveId))
                .replace("@TakenTaskId", String.valueOf(takenTaskId));

        return executeObject(query);
    }

    public HashMap<Integer, TaskProgress> getAllByTakenTask(int takenTaskId){
        String query = getQuery("task_progresses_get_all_by_taken_task");

        query = query
                .replace("@Table", this.table)
                .replace("@TakenTaskId", String.valueOf(takenTaskId));

        return executeAll(query);
    }

}
