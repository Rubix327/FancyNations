package me.rubix327.fancynations.data.towns;

import lombok.Getter;
import me.rubix327.fancynations.data.DataManager;
import org.apache.commons.lang.NotImplementedException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class TownProcess implements ITownManager{

    @Getter
    private static final HashMap<Integer, Town> towns = new HashMap<>();

    @Override
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean exists(int townId){
        return towns.containsKey(townId);
    }

    @Override
    public boolean exists(String name) {
        return false;
    }

    @Override
    public void add(Town town){
        towns.put(town.getId(), town);
    }

    @Override
    public Town get(int townId) throws NullPointerException {
        if (!exists(townId)) {
            throw new NullPointerException("This town does not exist in towns hashmap.");
        }
        return towns.get(townId);
    }

    @Override
    public Town get(String name) {
        return null;
    }

    @Override
    public void update(int townId, String variable, Object newValue) {
        throw new NotImplementedException();
    }

    @Override
    public void remove(int townId) throws NullPointerException {
        if (!exists(townId)){
            throw new NullPointerException("This town does not exist in towns hashmap.");
        }
        towns.remove(townId);
    }

    @Override
    public HashMap<Integer, Town> getAll() {
        throw new NotImplementedException();
    }

    @Override
    public List<String> getTownsFor(CommandSender sender) {
        if (!(sender instanceof Player) || sender.hasPermission("fancynations.admin")){
            List<String> names = new ArrayList<>();
            towns.values().forEach(town -> names.add(town.getName()));
            return names;
        }
        else if (DataManager.getTownWorkerManager().isMayor(sender.getName())){
            int playerId = DataManager.getFNPlayerManager().get(sender.getName()).getId();
            int townId = DataManager.getTownWorkerManager().get(playerId).getTownId();
            Town town = DataManager.getTownManager().get(townId);
            return Collections.singletonList(town.getName());
        }
        return new ArrayList<>();
    }

    @Override
    public int getMaxId() {
        return 0;
    }

}
