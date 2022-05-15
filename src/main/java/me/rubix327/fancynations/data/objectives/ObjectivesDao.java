package me.rubix327.fancynations.data.objectives;

import me.rubix327.fancynations.data.AbstractDao;
import me.rubix327.fancynations.data.tasks.TaskGroup;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class ObjectivesDao extends AbstractDao<Objective> implements IObjectivesManager {

    private static ObjectivesDao instance;

    private ObjectivesDao(String table) {
        super(table);
    }

    public static ObjectivesDao getInstance(String tableName){
        if (instance == null){
            instance = new ObjectivesDao(tableName);
        }
        return instance;
    }

    @Override
    protected Objective loadObject(ResultSet resultSet) throws SQLException{
        int id = resultSet.getInt("Id");
        String type = resultSet.getString("Type");
        String target = resultSet.getString("Target");
        int amount = resultSet.getInt("Amount");
        int taskId = resultSet.getInt("Task");

        if (ObjectiveInfo.get(type).getGroup() == TaskGroup.Gathering){
            return new GatheringObjective(id, type, target, amount, taskId);
        }
        else if (ObjectiveInfo.get(type).getGroup() == TaskGroup.Mobs) {
            return new MobKillObjective(id, type, target, amount, taskId);
        }
        throw new NullPointerException("This objective type group does not exist.");
    }

    public void add(Objective objective) {
        String query = getQuery("objectives_add");

        query = query
                .replace("@Table", table)
                .replace("@Type", String.valueOf(objective.getType()))
                .replace("@Target", objective.getTarget())
                .replace("@Amount", String.valueOf(objective.getAmount()))
                .replace("@Task", String.valueOf(objective.getTaskId()));

        super.executeVoid(query);
    }

    public HashMap<Integer, Objective> getAllFor(int taskId){
        String query = getQuery("objectives_get_all_for");
        query = query
                .replace("@Table", table)
                .replace("@TaskID", String.valueOf(taskId));

        return executeAll(query);
    }

}
