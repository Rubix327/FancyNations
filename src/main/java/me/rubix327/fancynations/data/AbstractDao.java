package me.rubix327.fancynations.data;

import me.rubix327.fancynations.FancyNations;
import me.rubix327.fancynations.Settings;
import me.rubix327.fancynations.util.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class AbstractDao<T extends AbstractDto> extends AbstractDataHandler<T> {

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
        String query = getQuery("general_exists_id");

        query = query
                .replace("@Table", this.table)
                .replace("@Id", String.valueOf(id));

        return this.executeBool(query);
    }

    /**
     Check if the record with specified name exists.
     Works only with columns called 'Name' (not case sensitive).
     @param name Record name
     */
    public boolean exists(String name) throws NullPointerException{
        String query = getQuery("general_exists_name");

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
    public T get(int id) {
        String query = getQuery("general_get_id");
        query = query
                .replace("@Table", this.table)
                .replace("@Id", String.valueOf(id));

        return executeObject(query);
    }

    /**
     Get all fields of a record with the specified name.
     Works only with columns called 'Name' (not case sensitive).
     @param name Record name
     @exception NullPointerException if object does not exist in the table
     */
    public T get(String name) {
        String query = getQuery("general_get_name");
        query = query
                .replace("@Table", this.table)
                .replace("@Name", name);

        return executeObject(query);
    }

    /**
     Sets the new value for the specified variable.
     @param id Record id
     @param variable Field to change
     @param newValue New value
     */
    public void update(int id, String variable, Object newValue) throws NullPointerException{
        String query = getQuery("general_update");

        query = query.replace("@Table", this.table)
                .replace("@Var", variable)
                .replace("@Value", String.valueOf(newValue))
                .replace("@Id", String.valueOf(id));

        this.executeVoid(query);
    }

    /**
     Remove a record with the specified id
     @param id Record id
     */
    public void remove(int id) throws NullPointerException{
        String query = getQuery("general_remove");

        query = query
                .replace("@Table", this.table)
                .replace("@Id", String.valueOf(id));

        this.executeVoid(query);
    }

    /**
     Get all the objects from a table.
     If no objects exist then it will return an empty HashMap<>.
     @return hashmap with objects casted to dto class specified in sub-dao constructor.
     */
    public HashMap<Integer, T> getAll() throws NullPointerException{
        return getAll(Settings.General.SQL_DEBUG);
    }

    public HashMap<Integer, T> getAll(boolean log) throws NullPointerException{
        String query = getQuery("general_get_all");
        query = query.replace("@Table", this.table);

        return executeAll(query, log);
    }

    /**
     * Get id that the next entry in the table would have.
     *
     * @return the next id
     */

    public int getNextId() throws NullPointerException {
        try {
            String query = getQuery("general_get_next_id");
            query = query.replace("@Table", this.table);
            PreparedStatement ps = FancyNations.getInstance().getDatabase().getConnection().
                    prepareStatement(query);
            Logger.logSqlQuery(query);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NullPointerException("Something went wrong.");
    }

    /**
     * Get list of names in specified table.
     *
     * @return The list of names
     */
    public List<String> getNames() throws NullPointerException {
        try {
            String query = getQuery("general_get_names");
            query = query.replace("@Table", this.table);
            PreparedStatement ps = FancyNations.getInstance().getDatabase().getConnection().
                    prepareStatement(query);
            Logger.logSqlQuery(query);
            ResultSet resultSet = ps.executeQuery();
            List<String> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(resultSet.getString("Name"));
            }
            return list;
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
            Logger.logSqlQuery(query);
            PreparedStatement ps = FancyNations.getInstance().getDatabase().getConnection().prepareStatement(query);
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
     * @param query Request to the database
     * @return true if found one record, false otherwise
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean executeBool(String query) throws NullPointerException {
        try{
            Logger.logSqlQuery(query);
            PreparedStatement ps = FancyNations.getInstance().getDatabase().getConnection().
                    prepareStatement(query);
            ResultSet resultSet = ps.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NullPointerException("Something went wrong.");
    }

    /**
     * Execute the specified query (request) on the database.
     * Returns one object. Suitable for 'get' methods.
     * @param query - Request to the database
     */
    public T executeObject(String query) throws NullPointerException {
        try{
            PreparedStatement ps = FancyNations.getInstance().getDatabase().getConnection().
                    prepareStatement(query);
            Logger.logSqlQuery(query);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()){
                return loadObject(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NullPointerException("Object with this parameters does not exist. Use Dao.exists() before this method.");
    }

    /**
     * Execute the specified query (request) on the database.
     * Returns all conditions appropriate objects. Suitable for 'getAll' methods.
     * If table does not contain any of these objects then it will return an empty HashMap.
     * @param query - Request to the database
     * @return HashMap of objects or empty HashMap.
     */
    public HashMap<Integer, T> executeAll(String query) throws NullPointerException {
        return executeAll(query, Settings.General.SQL_DEBUG);
    }

    public HashMap<Integer, T> executeAll(String query, boolean log) throws NullPointerException {
        try{
            PreparedStatement ps = FancyNations.getInstance().getDatabase().getConnection().
                    prepareStatement(query);
            if (log) {
                Logger.logSqlQuery(query);
            }
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

    public int executeInteger(String query){
        try{
            PreparedStatement ps = FancyNations.getInstance().getDatabase().getConnection().
                    prepareStatement(query);
            Logger.logSqlQuery(query);
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()){
                return resultSet.getInt("COUNT(*)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NullPointerException("Something went wrong.");
    }

    protected String getQuery(String key){
        return DatabaseManager.getInstance().getQuery(key);
    }

}
