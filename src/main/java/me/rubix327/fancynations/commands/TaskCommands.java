package me.rubix327.fancynations.commands;

import me.rubix327.fancynations.data.DataManager;
import me.rubix327.fancynations.data.GatheringTask;
import me.rubix327.fancynations.data.Task;
import me.rubix327.fancynations.data.TaskType;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.command.SimpleCommandGroup;
import org.mineacademy.fo.command.SimpleSubCommand;
import org.mineacademy.fo.model.SimpleComponent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TaskCommands extends SimpleSubCommand {
    protected TaskCommands(SimpleCommandGroup parent, String sublabel) {
        super(parent, sublabel);
    }

    //     label   a0       a1       a2        a3
    // /fn task create <town_name> <taskType> <taskName>
    // /fn task remove <task_id>
    // /fn tasks
    @Override
    protected void onCommand() {

        addTellPrefix(false);

        if (args.length == 0){
            tell("Help_page");
            return;
        }

        // /fn task create <town_name> <taskType> <taskName>
        if (args[0].equalsIgnoreCase("create")){
            if (args.length < 4) {
                tell("&cSyntax: /fn task create <town_name> <type> <name>");
                return;
            }

            String townName = args[1];
            TaskType taskType;
            String taskName = String.join(" ", Arrays.asList(args).subList(3, args.length));
            String taskCreatorName;

            // townName definition;
            if (!DataManager.isTownExist(townName)){
                tell("&cThis town already exists.");
                return;
            }

            // taskType definition
            try{
                taskType = TaskType.valueOf(StringUtils.capitalize(args[2]));
            } catch (IllegalArgumentException e){
                tell("&cТакого типа задания не существует.\n&cДоступные типы: " +
                        Arrays.stream(TaskType.values()).map(Object::toString).collect(Collectors.joining(", ")));
                return;
            }

            // taskCreatorName definition
            if (sender instanceof Player){
                taskCreatorName = getPlayer().getName();
            }
            else{
                taskCreatorName = "Server";
            }

            // Create new Task instance
            if (taskType == TaskType.Food || taskType == TaskType.Resource || taskType == TaskType.Crafting){
                GatheringTask task = new GatheringTask(townName, taskType, taskCreatorName, taskName);
                DataManager.addTask(task.getId(), task);
            }
//            else if (taskType == TaskType.Mobkill){
//
//            }

        }
        // fn task remove <task_id>
        else if (args[0].equalsIgnoreCase("remove")){

            if (args.length < 2) {
                tell("&cSyntax: /fn task remove <task_id>");
                return;
            }
            int taskId = findNumber(1, "&cTask ID must be a number");
            DataManager.removeTask(taskId);

        }

        // fn task set <task_id> <variable> <value>
        else if (args[0].equalsIgnoreCase("set")){

            if (args.length < 4) {
                tell("&cSyntax: /fn task set <task_id> <variable> <value>");
                return;
            }
            int taskId = findNumber(1, "&cTask ID must be a number");
            String variable = args[2];
            String value = args[3];

            if (!DataManager.isTaskExist(taskId)){
                tell("&cTask with this ID does not exist.\n&cType /fn tasks to see all tasks.");
                return;
            }
            Task task = DataManager.getTaskById(taskId);

            final List<String> shouldBeIntegers =
                    Arrays.asList("take_amount", "min_level", "max_level", "reputation_reward", "priority");
            final List<String> shouldBeDoubles = Arrays.asList("money_reward", "exp_reward");

            // Validations
            if (shouldBeDoubles.contains(variable.toLowerCase())){
                findNumber(Double.class, 3, "&cValue must be a floating point number (double).");
            }
            else if (shouldBeIntegers.contains(variable.toLowerCase())){
                findNumber(3, "&cValue must be a whole number (integer).");
            }

            DataManager.setValue(task, variable, value);
        }

        else if (args[0].equalsIgnoreCase("info")){

            if (args.length < 2) {
                tell("&cSyntax: /fn task info <task_id>");
                return;
            }
            int taskId = findNumber(1, "&cTask ID must be a number");

            if (!DataManager.isTaskExist(taskId)){
                tell("&cTask with this ID does not exist.\n&cType /fn tasks to see all tasks.");
                return;
            }
            Task task = DataManager.getTaskById(taskId);

            List<String> info = Arrays.asList(
                    "&7Info for task #" + taskId,
                    "",
                    "&7Name: " + task.getTaskName(),
                    "&7Type: " + task.getTaskType().toString(),
                    "&7Town: " + task.getTownName(),
                    "&7Created by: " + task.getCreatorName(),
                    "&7Max completions: " + task.getTakeAmount(),
                    "&7Money reward: " + task.getMoneyReward(),
                    "&7Experience reward: " + task.getExpReward(),
                    "&7Reputation reward: " + task.getReputationReward(),
                    "&7Priority: " + task.getPriority(),
                    "&7Levels: " + task.getMinLevel() + " - " + task.getMaxLevel(),
                    "&7Description: " + task.getDescription()
                    );

            List<SimpleComponent> firstMessage = Common.convert(info, SimpleComponent::of);
            tell(firstMessage);

        }

    }

    @Override
    protected List<String> tabComplete() {
        if (args.length == 1){
            return Arrays.asList("create", "remove", "set", "info");
        }
        else if (args.length == 2 && args[0].equalsIgnoreCase("create")){
            return DataManager.getAvailableTownsFor(sender);
        }
        else if (args.length == 3 && args[0].equalsIgnoreCase("create")){
            // TODO: Check
            List<String> types = new ArrayList<>();
            Arrays.stream(TaskType.values()).forEach(type -> types.add(type.toString()));
            return types;
        }
        else if (args.length == 2 && args[0].equalsIgnoreCase("remove")){
            return Collections.singletonList("<id>");
        }
        else if (args.length == 2 && args[0].equalsIgnoreCase("set")){
            return DataManager.taskVariablesList;
        }
        else if (args.length == 3 && args[0].equalsIgnoreCase("set")){
            return Collections.singletonList("<value>");
        }
        return new ArrayList<>();
    }
}
