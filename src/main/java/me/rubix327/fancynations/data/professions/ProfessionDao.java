package me.rubix327.fancynations.data.professions;

import me.rubix327.fancynations.data.AbstractDao;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfessionDao extends AbstractDao<Profession> implements IProfessionManager {

    private static ProfessionDao instance = null;

    private ProfessionDao(String table) {
        super(table);
    }

    public static ProfessionDao getInstance(String tableName){
        if (instance == null){
            instance = new ProfessionDao(tableName);
        }
        return instance;
    }

    @Override
    protected Profession loadObject(ResultSet resultSet) throws SQLException {

        int id = resultSet.getInt("Id");
        String name = resultSet.getString("Name");
        double salary = resultSet.getDouble("Salary");
        double stationsTaxBonus = resultSet.getDouble("StationsTaxBonus");
        double auctionTaxBonus = resultSet.getDouble("AuctionTaxBonus");
        double tasksTaxBonus = resultSet.getDouble("TasksTaxBonus");
        boolean permissionOpenBank = resultSet.getBoolean("PermissionOpenBank");
        boolean permissionDeposit = resultSet.getBoolean("PermissionDeposit");
        boolean permissionWithdraw = resultSet.getBoolean("PermissionWithdraw");

        return new Profession(id, name, salary, stationsTaxBonus, auctionTaxBonus, tasksTaxBonus,
                permissionOpenBank, permissionDeposit, permissionWithdraw);
    }

    @Override
    public void add(Profession dto) {
        String query = getQuery("professions_add");

        query = query
                .replace("@Table", table)
                .replace("@Name", String.valueOf(dto.getName()))
                .replace("@Salary", String.valueOf(dto.getSalary()))
                .replace("@StationsTaxBonus", String.valueOf(dto.getStationsTaxBonus()))
                .replace("@AuctionTaxBonus", String.valueOf(dto.getAuctionTaxBonus()))
                .replace("@TasksTaxBonus", String.valueOf(dto.getTasksTaxBonus()))
                .replace("@PermissionOpenBank", String.valueOf(dto.getPermissionOpenBank()))
                .replace("@PermissionDeposit", String.valueOf(dto.getPermissionDeposit()))
                .replace("@PermissionWithdraw", String.valueOf(dto.getPermissionWithdraw()));

        super.executeVoid(query);
    }
}
