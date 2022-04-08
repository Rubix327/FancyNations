package me.rubix327.fancynations.data;

import me.rubix327.fancynations.FancyNations;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public abstract class AbstractDao<T> extends AbstractDataHandler<T> {

    protected String table;

    protected AbstractDao(String table){
        this.table = table;
    }

    protected abstract T loadObject(ResultSet resultSet) throws SQLException;

    /**
     Check if the record with specified id exists.
     Works only with columns called 'ID' (not case sensitive).
     @param id Record id
     */
    public boolean exists(int id) throws NullPointerException{
        String query = "SELECT Id FROM @Table WHERE Id = @ID";

        query = query
                .replace("@Table", this.table)
                .replace("@ID", String.valueOf(id));

        return this.executeBool(query);
    }

    /**
     Check if the record with specified name exists.
     Works only with columns called 'Name' (not case sensitive).
     @param name Record name
     */
    public boolean exists(String name) throws NullPointerException{
        String query = "SELECT Id FROM @Table WHERE Name = @Name";

        query = query
                .replace("@Table", this.table)
                .replace("@Name", name);

        return this.executeBool(query);
    }

    /**
     Get a record with the specified id.
     Works only with columns called 'ID' (not case sensitive).
     @param id Record id
     @exception NullPointerException if object does not exist in the table
     */
    public T get(int id) throws NullPointerException{
        try{
            String query = "SELECT * FROM @Table WHERE @ID = @ID";
            query = query
                    .replace("@Table", this.table)
                    .replace("@ID", String.valueOf(id));
            PreparedStatement ps = FancyNations.getInstance().database.getConnection().
                    prepareStatement(query);
            DatabaseManager.logSqlQuery(query);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()){
                return loadObject(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NullPointerException("Object with this id does not exist. Use Dao.exists() before this method.");
    }

    /**
     Get all fields of a record with the specified name.
     Works only with columns called 'Name' (not case sensitive).
     @param name Record name
     @exception NullPointerException if object does not exist in the table
     */
    public T get(String name) throws NullPointerException{
        try{
            String query = "SELECT * FROM @Table WHERE Name = '@Name'";
            query = query
                    .replace("@Table", this.table)
                    .replace("@Name", name);
            PreparedStatement ps = FancyNations.getInstance().database.getConnection().
                    prepareStatement(query);
            DatabaseManager.logSqlQuery(query);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()){
                return loadObject(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NullPointerException("Object with this id does not exist. Use Dao.exists() before this method.");
    }

    /**
     Sets the new value for the specified variable.
     @param id Record id
     @param variable Field to change
     @param newValue New value
     */
    public void update(int id, String variable, Object newValue) throws NullPointerException{
        String query = "UPDATE @Table SET @Var = @Value WHERE ID = @ID";

        query = query.replace("@Table", this.table)
                .replace("@Var", variable)
                .replace("@Value", String.valueOf(newValue))
                .replace("@ID", String.valueOf(id));

        this.executeVoid(query);
    }

    /**
     Remove a record with the specified id
     @param id Record id
     */
    public void remove(int id) throws NullPointerException{
        String query = "DELETE FROM @Table WHERE ID = @ID";

        query = query
                .replace("@Table", this.table)
                .replace("@ID", String.valueOf(id));

        this.executeVoid(query);
    }

    /**
     Get all the objects from a table.
     If no objects exist then it will return an empty HashMap<>.
     @return hashmap with objects casted to dto class specified in sub-dao constructor.
     */
    public HashMap<Integer, T> getAll() throws NullPointerException{
        try{
            String query = "SELECT * FROM @Table";
            query = query.replace("@Table", this.table);

            PreparedStatement ps = FancyNations.getInstance().database.getConnection().
                    prepareStatement(query);
            DatabaseManager.logSqlQuery(query);
            ResultSet resultSet = ps.executeQuery();

            HashMap<Integer, T> dtos = new HashMap<>();
            while (resultSet.next()){
                dtos.put(resultSet.getInt("ID"), loadObject(resultSet));
            }
            return dtos;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NullPointerException("Something went wrong.");
    }

    /**
     Get the maximum id of all records.
     @return maxId
     */
    public int getMaxId() throws NullPointerException{
        try{
            String query = "SELECT Id FROM @Table ORDER BY Id DESC LIMIT 1";
            query = query.replace("@Table", this.table);
            PreparedStatement ps = FancyNations.getInstance().database.getConnection().
                    prepareStatement(query);
            DatabaseManager.logSqlQuery(query);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()){
                return resultSet.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NullPointerException("Something went wrong.");
    }

    /**
     * Execute the specified query (request) on the database.
     * Returns nothing. Suitable for 'add', 'update' or 'remove' methods
     * @param query - Request to the database
     */
    public void executeVoid(String query) throws NullPointerException {
        try{
            DatabaseManager.logSqlQuery(query);
            PreparedStatement ps = FancyNations.getInstance().database.getConnection().prepareStatement(query);
            ps.executeUpdate();
            return;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NullPointerException("Object with this id does not exist or something went wrong.");
    }

    /**
     * Execute the specified query (request) on the database.
     * Returns true/false value. Suitable for 'exists' methods.
     * @param query - Request to the database
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean executeBool(String query) throws NullPointerException {
        try{
            DatabaseManager.logSqlQuery(query);
            PreparedStatement ps = FancyNations.getInstance().database.getConnection().
                    prepareStatement(query);
            ResultSet resultSet = ps.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NullPointerException("Something went wrong.");
    }

}
