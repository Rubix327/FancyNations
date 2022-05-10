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

    @Override
    public void add(TaskProgress progress) {
        String query = getQuery("task_progresses_add");

        query = query
                .replace("@Table", table)
                .replace("@Objective", String.valueOf(progress.getObjectiveId()))
                .replace("@Progress", String.valueOf(progress.getProgress()));

        super.executeVoid(query);
    }

    public TaskProgress get(int objectiveId, int takenTaskId){
        String query = getQuery("task_progresses_get");

        query = query
                .replace("@Table", this.table)
                .replace("@Objective", String.valueOf(objectiveId))
                .replace("@TakenTask", String.valueOf(takenTaskId));

        return this.executeObject(query);
    }

    public HashMap<Integer, TaskProgress> getAllByTakenTask(int takenTaskId){
        String query = getQuery("task_progresses_get_all_by_taken_task");

        query = query
                .replace("@Table", this.table)
                .replace("@TakenTask", String.valueOf(takenTaskId));

        return this.executeAll(query);
    }

}
