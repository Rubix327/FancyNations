package me.rubix327.fancynations.data.towns;

import me.rubix327.fancynations.data.AbstractDao;
import me.rubix327.fancynations.data.DataManager;
import me.rubix327.fancynations.data.professions.PredefinedProfession;
import me.rubix327.fancynations.data.professions.Profession;
import me.rubix327.fancynations.data.townworkers.TownWorker;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TownDao extends AbstractDao<Town> implements ITownManager {

    private static TownDao instance = null;

    private TownDao(String table) {
        super(table);
    }

    public static TownDao getInstance(String tableName){
        if (instance == null){
            instance = new TownDao(tableName);
        }
        return instance;
    }

    @Override
    protected Town loadObject(ResultSet resultSet) throws SQLException {

        int id = resultSet.getInt("Id");
        int nationId = resultSet.getInt("Nation");
        String name = resultSet.getString("Name");
        int balance = resultSet.getInt("Balance");
        int stationsTax = resultSet.getInt("StationsTax");
        int auctionTax = resultSet.getInt("AuctionTax");
        int tasksTax = resultSet.getInt("TasksTax");

        return new Town(id, nationId, name, balance, stationsTax, auctionTax, tasksTax);
    }

    @Override
    public void add(Town town) {
        String query = getQuery("towns_add");

        query = query
                .replace("@Table", table)
                .replace("@Nation", String.valueOf(town.getNationId()))
                .replace("@Name", String.valueOf(town.getName()))
                .replace("@Balance", String.valueOf(town.getBalance()))
                .replace("@StationsTax", String.valueOf(town.getStationsTax()))
                .replace("@AuctionTax", String.valueOf(town.getAuctionTax()))
                .replace("@TasksTax", String.valueOf(town.getTasksTax()));

        super.executeVoid(query);
    }

    /**
     * Shows list of towns that player (sender) can edit.
     * For admins and console the list consists of all created towns.
     * For mayor the list consists of only the town he controls.
     */
    public List<String> getTownsFor(CommandSender sender) {
        TownWorker worker = DataManager.getTownWorkerManager().getByPlayer(sender.getName());
        String profession = Profession.get(worker.getProfessionId()).getName();
        if (!(sender instanceof Player) || sender.hasPermission("fancynations.admin")){
            List<String> names = new ArrayList<>();
            DataManager.getTownManager().getAll().values().forEach(town -> names.add(town.getName()));
            return names;
        }
        else if (profession.equalsIgnoreCase(PredefinedProfession.Mayor.toString())){
            return Collections.singletonList(Town.get(worker.getTownId()).getName());
        }
        return new ArrayList<>();
    }

}
