package me.rubix327.fancynations.data.town;

import lombok.Getter;
import me.rubix327.fancynations.data.worker.TownWorkerManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class TownManager {

    @Getter
    private static final HashMap<String, Town> towns = new HashMap<>();

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
        else if (TownWorkerManager.isMayor(((Player)sender).getName())){

        }
        return Arrays.asList("SunRise", "Moscow", "Capatov");
    }

}
