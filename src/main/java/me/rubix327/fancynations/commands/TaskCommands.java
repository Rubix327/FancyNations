package me.rubix327.fancynations.commands;

import me.rubix327.fancynations.data.DataManager;
import me.rubix327.fancynations.data.Settings;
import me.rubix327.fancynations.data.task.GatheringTask;
import me.rubix327.fancynations.data.task.Task;
import me.rubix327.fancynations.data.task.TaskType;
import me.rubix327.fancynations.data.town.TownManager;
import net.Indyuce.mmocore.api.player.PlayerData;
import org.apache.commons.lang.StringUtils;
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

    /*
       label  a0       a1          a2         a3
    fn task create <town_name> <taskType> <taskName>
    fn task set    <task_id>   <variable> <value>
    fn task remove <task_id>
    fn task info <task_id>
    fn task start <task_id>
    fn task finish <task_id>
    fn task cancel <task_id>
    fn tasks
     */
    @Override
    protected void onCommand() {

        setPermission("fancynations.task");
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
            String taskCreatorName = (sender instanceof Player ? getPlayer().getName() : Settings.General.SERVER_VAR);

            // townName definition;
            if (!TownManager.exists(townName)){
                tell("&cThis town does not exist.");
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

            // Create new Task instance
            if (taskType == TaskType.Food || taskType == TaskType.Resource || taskType == TaskType.Crafting){
                GatheringTask task = new GatheringTask(townName, taskType, taskCreatorName, taskName);
                DataManager.getTaskManager().add(task);
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

            DataManager.getTaskManager().remove(taskId);

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
            final List<String> shouldBeIntegers = DataManager.getClassFieldsByType(Task.class, int.class);
            final List<String> shouldBeDoubles = DataManager.getClassFieldsByType(Task.class, double.class);

            if (sender instanceof Player){
                if (!getPlayer().hasPermission("fancynations.task.setvalue." + variable)){
                    tell("&cYou don't have enough permissions.");
                    return;
                }
            }

            if (!DataManager.getTaskManager().exists(taskId)){
                tell("&cTask with this ID does not exist.\n&cType /fn tasks to see all tasks.");
                return;
            }

            // Validations
            if (shouldBeDoubles.contains(variable.toLowerCase())){
                findNumber(Double.class, 3, "&cValue must be a floating point number (double).");
            }
            else if (shouldBeIntegers.contains(variable.toLowerCase())){
                findNumber(3, "&cValue must be a whole number (integer).");
            }

            DataManager.getTaskManager().update(taskId, variable, value);
        }

        // fn task info <task_id>
        else if (args[0].equalsIgnoreCase("info")){

            if (args.length < 2) {
                tell("&cSyntax: /fn task info <task_id>");
                return;
            }
            int taskId = findNumber(1, "&cTask ID must be a number");

            if (!DataManager.getTaskManager().exists(taskId)){
                tell("&cTask with this ID does not exist.\n&cType /fn tasks to see all tasks.");
                return;
            }
            Task task = DataManager.getTaskManager().get(taskId);

            List<String> info = Arrays.asList(
                    "&7Info about task #" + taskId,
                    "",
                    "&7Name: " + task.getTaskName(),
                    "&7Type: " + task.getTaskType().toString(),
                    "&7Town: " + task.getTownName(),
                    "&7Created by: " + (task.getCreatorName().equals("%server%") ? "Nation" : task.getCreatorName()),
                    "&7Max completions: " + task.getTakeAmount(),
                    "&7Money reward: " + task.getMoneyReward(),
                    "&7Experience reward: " + task.getExpReward(),
                    "&7Reputation reward: " + task.getRepReward(),
                    "&7Priority: " + task.getPriority(),
                    "&7Levels: " + task.getMinLevel() + " - " + task.getMaxLevel(),
                    "&7Description: " + task.getDescription()
                    );

            List<SimpleComponent> infoMessage = Common.convert(info, SimpleComponent::of);
            tell(infoMessage);
        }

        // fn task start <task_id>
        else if (args[0].equalsIgnoreCase("start")){

            if (args.length < 2) {
                tell("&cSyntax: /fn task info <task_id>");
                return;
            }
            int taskId = findNumber(2, "&cTask ID must be a number");

            if (!DataManager.getTaskManager().exists(taskId)){
                tell("&cTask with this ID does not exist.\n&cType /fn tasks to see all tasks.");
                return;
            }
            Task task = DataManager.getTaskManager().get(taskId);

            PlayerData playerData = PlayerData.get(getPlayer().getUniqueId());
            if (task.getTakeAmount() <= 0){
                tell("&cThis task is not available anymore.");
            }

            if (playerData.getLevel() < task.getMinLevel() || playerData.getLevel() > task.getMaxLevel()){
                tell("&cYour level is not suitable for this task.\n" +
                        "Your level: " + playerData.getLevel() + ". Required: " +
                        task.getMinLevel() + " - " + task.getMaxLevel());
                return;
            }

        }

        else{
            tell("&cSyntax: /fn task <create/remove/set/info>");
            return;
        }
    }

    @Override
    protected List<String> tabComplete() {
        if (args.length == 1){
            return Arrays.asList("create", "remove", "set", "info");
        }
        else if (args.length == 2 && args[0].equalsIgnoreCase("create")){
            return TownManager.getAvailableTownsFor(sender);
        }
        else if (args.length == 3 && args[0].equalsIgnoreCase("create")){
            return Arrays.stream(TaskType.values()).map(Enum::toString).collect(Collectors.toList());
        }
        else if (args.length == 2 && args[0].equalsIgnoreCase("remove")){
            return Collections.singletonList("<id>");
        }
        else if (args.length == 3 && args[0].equalsIgnoreCase("set")){
            return DataManager.getClassFields(Task.class);
        }
        else if (args.length == 4 && args[0].equalsIgnoreCase("set")){
            return Collections.singletonList("<value>");
        }
        return new ArrayList<>();
    }
}
