package me.rubix327.fancynations.data.objectives;

import me.rubix327.fancynations.data.AbstractDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class ObjectivesDao extends AbstractDao<Objective> implements IObjectivesManager {

    private static ObjectivesDao instance = null;

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
        int task = resultSet.getInt("Task");
        String name = resultSet.getString("Name");
        int amount = resultSet.getInt("Amount");

        return new Objective(id, task, name, amount);
    }

    public void add(Objective objective) {
        String query = "INSERT INTO @Table (Task, Name, Amount) VALUES (@Task, '@Name', @Amount)";

        query = query
                .replace("@Table", table)
                .replace("@Task", String.valueOf(objective.getTask()))
                .replace("@Name", objective.getName())
                .replace("@Amount", String.valueOf(objective.getAmount()));

        super.executeVoid(query);
    }

    public HashMap<Integer, Objective> getAllFor(int taskId){
        String query = "SELECT * FROM @Table WHERE Task = @TaskID";
        query = query
                .replace("@Table", table)
                .replace("@TaskID", String.valueOf(taskId));

        return executeAll(query);
    }

}
