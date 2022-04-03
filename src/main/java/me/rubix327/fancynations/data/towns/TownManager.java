package me.rubix327.fancynations.data.towns;

import lombok.Getter;
import me.rubix327.fancynations.data.DataManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class TownManager {

    @Getter
    private static final HashMap<String, Town> towns = new HashMap<>();

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean exists(String townName){
        return towns.containsKey(townName);
    }

    public static void add(String townName, Town town){
        towns.put(townName, town);
    }

    public static void remove(String townName) throws NullPointerException {
        if (!exists(townName)){
            throw new NullPointerException("This town does not exist in towns hashmap.");
        }
        towns.remove(townName);
    }

    public static Town getByName(String townName) throws NullPointerException {
        if (!exists(townName)) {
            throw new NullPointerException("This town does not exist in towns hashmap.");
        }
        return towns.get(townName);
    }

    public static List<String> getAvailableTownsFor(CommandSender sender) {
        if (!(sender instanceof Player) || sender.hasPermission("fancynations.admin")){
            List<String> names = new ArrayList<>();
            towns.values().forEach(town -> names.add(town.getName()));
            return names;
        }
        else if (DataManager.getTownWorkerManager().isMayor(sender.getName())){
            int townId = DataManager.getTownWorkerManager().get(sender.getName()).getTownId();
            String townName = DataManager.getTownManager().get(townId).getName();
            return Collections.singletonList(townName);
        }
        return new ArrayList<>();
    }

}
