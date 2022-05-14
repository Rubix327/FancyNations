package me.rubix327.fancynations.data.towns;

import me.rubix327.fancynations.data.AbstractProcess;
import me.rubix327.fancynations.data.DataManager;
import me.rubix327.fancynations.data.townworkers.TownWorker;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TownProcess extends AbstractProcess<Town> implements ITownManager {

    private static TownProcess instance = null;

    public TownProcess() {
        super(Town.class);
    }

    public static TownProcess getInstance(){
        if (instance == null){
            instance = new TownProcess();
        }
        return instance;
    }

    public List<String> getTownsFor(CommandSender sender) {
        int playerId = DataManager.getFNPlayerManager().get(sender.getName()).getId();
        if (!(sender instanceof Player) || sender.hasPermission("fancynations.admin")){
            List<String> names = new ArrayList<>();
            getAll().values().forEach(town -> names.add(town.getName()));
            return names;
        }
        else if ((DataManager.getTownWorkerManager()).isMayor(playerId)){
            TownWorker townWorker = DataManager.getTownWorkerManager().getByPlayer(sender.getName());
            String townName = DataManager.getTownManager().get(townWorker.getTownId()).getName();
            return Collections.singletonList(townName);
        }
        return new ArrayList<>();
    }

}
