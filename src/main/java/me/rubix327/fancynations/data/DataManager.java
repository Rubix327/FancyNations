package me.rubix327.fancynations.data;

import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class DataManager {

    @Getter
    private static HashMap<String, Town> towns = new HashMap<>();

    @Getter
    private static HashMap<Integer, Task> tasks = new HashMap<>();

    // TODO: To DB
    public static void addTask(int id, Task task){
        tasks.put(id, task);
    }

    // TODO: To DB
    public static void removeTask(int id){
        tasks.remove(id);
    }

    // TODO: To DB
    public static Task getTaskById(int id) throws NullPointerException{
        if (tasks.get(id) == null){
            throw new NullPointerException("This task does not exist in tasks hashmap.");
        }
        return tasks.get(id);
    }

    public final static List<String> taskVariablesList = Arrays.asList("task_name", "description", "take_amount",
            "min_level", "max_level", "reputation_reward", "priority", "money_reward", "exp_reward");

    // TODO: To DB
    public static void setValue(Task task, String variable, Object value){
        switch (variable){
            case ("task_name"): task.setTaskName(String.valueOf(value));
            case ("description"): task.setDescription(String.valueOf(value));
            case ("take_amount"): task.setTakeAmount((int) value);
            case ("min_level"): task.setMinLevel((int) value);
            case ("max_level"): task.setMaxLevel((int) value);
            case ("reputation_reward"): task.setReputationReward((int) value);
            case ("priority"): task.setReputationReward((int) value);
            case ("money_reward"): task.setMoneyReward((double) value);
            case ("exp_reward"): task.setExpReward((double) value);
        }
    }

    public static List<String> getAvailableTownsFor(CommandSender sender) {
        // TODO: make individual for every player
        if (!(sender instanceof Player) || sender.hasPermission("fancynations.admin")){
            List<String> names = new ArrayList<>();
            DataManager.getTowns().values().forEach(town -> names.add(town.getName()));
            return names;
        }
//        else if (Mayor.is((Player)sender)){
//
//        }
        return Arrays.asList("SunRise", "Moscow", "Capatov");
    }
}
