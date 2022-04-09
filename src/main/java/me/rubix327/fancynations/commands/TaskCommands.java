package me.rubix327.fancynations.commands;

import me.rubix327.fancynations.Localization;
import me.rubix327.fancynations.Settings;
import me.rubix327.fancynations.data.DataManager;
import me.rubix327.fancynations.data.fnplayers.FNPlayer;
import me.rubix327.fancynations.data.takentasks.TakenTask;
import me.rubix327.fancynations.data.tasks.GatheringTask;
import me.rubix327.fancynations.data.tasks.MobKillTask;
import me.rubix327.fancynations.data.tasks.Task;
import me.rubix327.fancynations.data.tasktypes.TaskType;
import me.rubix327.fancynations.data.tasktypes.TaskTypeDao;
import me.rubix327.fancynations.data.towns.TownDao;
import net.Indyuce.mmocore.api.player.PlayerData;
import net.Indyuce.mmocore.experience.EXPSource;
import org.bukkit.entity.Player;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.command.SimpleCommandGroup;
import org.mineacademy.fo.command.SimpleSubCommand;
import org.mineacademy.fo.model.SimpleComponent;

import java.time.LocalDateTime;
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

        Localization msgs = Localization.getInstance();
        setPermission("fancynations.task");
        addTellPrefix(false);

        if (args.length == 0){
            tell("Help_page");
            return;
        }

        // /fn task create <town_name> <taskType> <taskName>
        if (args[0].equalsIgnoreCase("create")){
            if (args.length < 4) {
                tell(msgs.get("syntax_task_create", sender));
                return;
            }
            int townId;
            TaskType taskType;
            if (!((TownDao)DataManager.getTownManager()).exists(args[1])){
                tell(msgs.get("error_town_not_exist", sender));
                return;
            }
            townId = (((TownDao)DataManager.getTownManager()).get(args[1])).getId();

            if (!((TaskTypeDao)DataManager.getTaskTypeManager()).exists(args[2])){
                tell(msgs.get("error_task_type_not_exist" +
                         DataManager.getTaskTypeManager().getAll().values()
                        .stream().map(Object::toString).collect(Collectors.joining(", ")), sender));
                return;
            }
            taskType = DataManager.getTaskTypeManager().get(args[2]);

            String taskName = String.join(" ", Arrays.asList(args).subList(3, args.length));
            int fnPlayerId = DataManager.getFNPlayerManager().get(getPlayer().getName()).getId();
            int serverId = DataManager.getFNPlayerManager().get(Settings.General.SERVER_VAR).getId();
            int taskCreatorId = (sender instanceof Player ? fnPlayerId : serverId);

            // Create new Task instance
            if (taskType.getGroup().equalsIgnoreCase("Gathering")){
                GatheringTask task = new GatheringTask(townId, taskType.getId(), taskCreatorId, taskName);
                DataManager.getTaskManager().add(task);
            }
            else if (taskType.getGroup().equalsIgnoreCase("Mobs")){
                MobKillTask task = new MobKillTask(townId, taskType.getId(), taskCreatorId, taskName);
                DataManager.getTaskManager().add(task);
            }

        }

        // fn task remove <task_id>
        else if (args[0].equalsIgnoreCase("remove")){

            if (args.length < 2) {
                tell(msgs.get("syntax_task_remove" , sender));
                return;
            }
            int taskId = findNumber(1, "&cTask ID must be a number");

            DataManager.getTaskManager().remove(taskId);

        }

        // fn task set <task_id> <variable> <value>
        else if (args[0].equalsIgnoreCase("set")){

            if (args.length < 4) {
                tell(msgs.get("syntax_task_set", sender));
                return;
            }
            int taskId = findNumber(1, "&cTask ID must be a number");
            String variable = args[2];
            String value = args[3];
            final List<String> shouldBeIntegers = DataManager.getClassFieldsByType(Task.class, int.class);
            final List<String> shouldBeDoubles = DataManager.getClassFieldsByType(Task.class, double.class);

            if (sender instanceof Player){
                if (!getPlayer().hasPermission("fancynations.task.setvalue." + variable)){
                    tell(msgs.get("error_not_enough_permissions", sender));
                    return;
                }
            }

            if (!DataManager.getTaskManager().exists(taskId)){
                tell(msgs.get("error_task_not_exist", sender));
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
                tell(msgs.get("syntax_task_info", sender));
                return;
            }
            int taskId = findNumber(1, "&cTask ID must be a number");

            if (!DataManager.getTaskManager().exists(taskId)){
                tell(msgs.get("error_task_not_exist", sender));
                return;
            }
            Task task = DataManager.getTaskManager().get(taskId);
            FNPlayer fnPlayer = DataManager.getFNPlayerManager().get(task.getCreatorId());

            List<String> info = Arrays.asList(
                    "&7Info about task #" + taskId,
                    "",
                    "&7Name: " + task.getTaskName(),
                    "&7Type: " + DataManager.getTaskTypeManager().get(task.getTaskTypeId()).getName(),
                    "&7Town: " + DataManager.getTownManager().get(task.getTownId()).getName(),
                    "&7Created by: " + (fnPlayer.getName().equals("%server%") ? "Nation" : fnPlayer.getName()),
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
            PlayerData playerData = PlayerData.get(getPlayer().getUniqueId());

            if (args.length < 2) {
                tell(msgs.get("syntax_task_start", sender));
                return;
            }
            int taskId = findNumber(2, "&cTask ID must be a number.");

            if (!DataManager.getTaskManager().exists(taskId)){
                tell(msgs.get("error_task_not_exist", sender));
                return;
            }
            Task task = DataManager.getTaskManager().get(taskId);

            if (task.getTakeAmount() <= 0){
                tell(msgs.get("error_task_not_available", sender));
            }

            if (playerData.getLevel() < task.getMinLevel() || playerData.getLevel() > task.getMaxLevel()){
                String msg1 = msgs.get("error_level_not_suitable", sender);
                msg1 = msg1.replace("@playerLevel", String.valueOf(playerData.getLevel()))
                                .replace("@playerMinLevel", String.valueOf(task.getMinLevel()))
                                .replace("@playerMaxLevel", String.valueOf(task.getMaxLevel()));
                tell(msg1);
                return;
            }

            // If player own this task already
            FNPlayer fnPlayer = DataManager.getFNPlayerManager().get(getPlayer().getName());
            if (DataManager.getTakenTaskManager().exists(fnPlayer.getId(), taskId)){
                tell(msgs.get("error_task_already_taken", sender));
                return;
            }

            DataManager.getTaskManager().update(taskId, "take_amount", task.getTakeAmount() - 1);
        }

        else if (args[0].equalsIgnoreCase("finish")){
            PlayerData playerData = PlayerData.get(getPlayer().getUniqueId());

            if (args.length < 2) {
                tell(msgs.get("syntax_task_finish", sender));
                return;
            }
            int taskId = findNumber(2, "&cTask ID must be a number.");

            if (!DataManager.getTaskManager().exists(taskId)){
                tell(msgs.get("error_task_not_exist", sender));
                return;
            }
            Task task = DataManager.getTaskManager().get(taskId);

            if (task.getPlacementDateTime().getNanos() + task.getTimeToComplete() < LocalDateTime.now().getNano()){
                tell(msgs.get("error_task_time_to_complete_expired", sender));
                // TODO automatically takeAmount += 1 when task expires
                // TODO automatically remove takentask from player when task expires
                return;
            }

            if (!task.isAllObjectivesCompleted(getPlayer(), taskId)){
                tell(msgs.get("error_task_objective_not_completed", sender));
                return;
            }

            // If player does not own this task
            FNPlayer fnPlayer = DataManager.getFNPlayerManager().get(getPlayer().getName());
            if (!DataManager.getTakenTaskManager().exists(fnPlayer.getId(), taskId)){
                tell(msgs.get("error_task_not_taken", sender));
                return;
            }

            // TODO give money
            // TODO give reputation

            playerData.giveExperience(task.getExpReward(), EXPSource.QUEST, getPlayer().getLocation(), false);

            // TODO if type==gathering then send 50% of resources to town
            // TODO if type==mobkill then ???

            TakenTask takenTask = DataManager.getTakenTaskManager().get(fnPlayer.getId(), taskId);
            DataManager.getTakenTaskManager().remove(takenTask.getId());
        }

        else{
            tell(msgs.get("syntax_task", sender));
            return;
        }
    }

    @Override
    protected List<String> tabComplete() {
        if (args.length == 1){
            return Arrays.asList("create", "remove", "set", "info");
        }
        else if (args.length == 2 && args[0].equalsIgnoreCase("create")){
            return DataManager.getTownManager().getTownsFor(sender);
        }
        else if (args.length == 3 && args[0].equalsIgnoreCase("create")){
            return DataManager.getTaskTypeManager().getAll().values().stream().map(Object::toString)
                    .collect(Collectors.toList());
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
