package me.rubix327.fancynations.data.towns;

import me.rubix327.fancynations.data.AbstractDao;
import me.rubix327.fancynations.data.DataManager;
import me.rubix327.fancynations.data.fnplayers.FNPlayerDao;
import me.rubix327.fancynations.data.townworkers.TownWorker;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TownDao extends AbstractDao<Town> implements ITownManager {

    private final String tableName;

    public TownDao(String tableName) {
        super(tableName);
        this.tableName = tableName;
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
        String query = "INSERT INTO @Table (Nation, Name, Balance, StationsTax, AuctionTax, TasksTax)" +
                "VALUES(@Nation, '@Name', @Balance, @StationsTax, @AuctionTax, @TasksTax)";

        query = query
                .replace("@Table", tableName)
                .replace("@Nation", String.valueOf(town.getNationId()))
                .replace("@Name", String.valueOf(town.getName()))
                .replace("@Balance", String.valueOf(town.getBalance()))
                .replace("@StationsTax", String.valueOf(town.getStationsTax()))
                .replace("@AuctionTax", String.valueOf(town.getAuctionTax()))
                .replace("@TasksTax", String.valueOf(town.getTasksTax()));

        super.executeVoid(query);
    }

    public List<String> getTownsFor(CommandSender sender) {
        int playerId = ((FNPlayerDao) DataManager.getFNPlayerManager()).get(sender.getName()).getId();
        if (!(sender instanceof Player) || sender.hasPermission("fancynations.admin")){
            List<String> names = new ArrayList<>();
            DataManager.getTownManager().getAll().values().forEach(town -> names.add(town.getName()));
            return names;
        }
        else if (DataManager.getTownWorkerManager().isMayor(playerId)){
            TownWorker townWorker = DataManager.getTownWorkerManager().get(sender.getName());
            String townName = DataManager.getTownManager().get(townWorker.getTownId()).getName();
            return Collections.singletonList(townName);
        }
        return new ArrayList<>();
    }

}
