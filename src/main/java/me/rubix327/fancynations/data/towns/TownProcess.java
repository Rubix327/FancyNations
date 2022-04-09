package me.rubix327.fancynations.data.towns;

import lombok.Getter;
import me.rubix327.fancynations.data.AbstractProcess;
import me.rubix327.fancynations.data.DataManager;
import me.rubix327.fancynations.data.fnplayers.FNPlayerDao;
import me.rubix327.fancynations.data.townworkers.TownWorker;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class TownProcess extends AbstractProcess<Town> implements ITownManager {

    @Getter
    private static final HashMap<Integer, Town> dtos = new HashMap<>();

    public TownProcess() {
        super(dtos, Town.class);
    }

    public List<String> getTownsFor(CommandSender sender) {
        int playerId = ((FNPlayerDao)DataManager.getFNPlayerManager()).get(sender.getName()).getId();
        if (!(sender instanceof Player) || sender.hasPermission("fancynations.admin")){
            List<String> names = new ArrayList<>();
            dtos.values().forEach(town -> names.add(town.getName()));
            return names;
        }
        else if ((DataManager.getTownWorkerManager()).isMayor(playerId)){
            TownWorker townWorker = DataManager.getTownWorkerManager().get(sender.getName());
            String townName = DataManager.getTownManager().get(townWorker.getTownId()).getName();
            return Collections.singletonList(townName);
        }
        return new ArrayList<>();
    }

}
