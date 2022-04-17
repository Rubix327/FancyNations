package me.rubix327.fancynations.commands;

import me.rubix327.fancynations.FancyNations;
import me.rubix327.fancynations.Settings;
import me.rubix327.fancynations.data.DataManager;
import me.rubix327.fancynations.data.fnplayers.FNPlayer;
import me.rubix327.fancynations.data.reputations.Reputation;
import me.rubix327.fancynations.data.takentasks.TakenTask;
import me.rubix327.fancynations.data.tasks.GatheringTask;
import me.rubix327.fancynations.data.tasks.MobKillTask;
import me.rubix327.fancynations.data.tasks.Task;
import me.rubix327.fancynations.data.tasktypes.TaskType;
import net.Indyuce.mmocore.api.player.PlayerData;
import net.Indyuce.mmocore.experience.EXPSource;
import org.bukkit.entity.Player;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.model.SimpleComponent;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class TaskCommands extends SubCommandInterlayer {
    protected TaskCommands(String sublabel, String permLabel) {
        super(sublabel, permLabel);
    }

    /*
       label  a0       a1          a2         a3
    fn task create <town_name> <taskType> <taskName>
    fn task remove <task_id>
    fn task set    <task_id>   <variable> <value>
    fn task info <task_id>
    fn task list
    fn task start <task_id>
    fn task finish <task_id>
    fn task cancel <task_id>
     */
    @Override
    protected void onCommand() {

        if (args.length == 0){
            checkPermission("help");
            tell(msgs.get("syntax_task", sender));
            return;
        }

        // Create new task - fn task create <town_name> <taskType> <taskName>
        if (args[0].equalsIgnoreCase("create")){
            checkPermission("create");

            int townId;
            TaskType taskType;

            // Wrong syntax
            if (args.length < 4) {
                tell(msgs.get("syntax_task_create", sender));
                return;
            }

            // Town does not exist
            if (!DataManager.getTownManager().exists(args[1])){
                tell(msgs.get("error_town_not_exist", sender));
                return;
            }
            townId = (DataManager.getTownManager().get(args[1])).getId();

            // Task type does not exist
            if (!DataManager.getTaskTypeManager().exists(args[2])){
                tell(msgs.get("error_task_type_not_exist" +
                         DataManager.getTaskTypeManager().getAll().values()
                        .stream().map(Object::toString).collect(Collectors.joining(", ")), sender));
                return;
            }
            taskType = DataManager.getTaskTypeManager().get(args[2]);

            final String taskName = String.join(" ", Arrays.asList(args).subList(3, args.length));
            final int fnPlayerId = DataManager.getFNPlayerManager().get(getPlayer().getName()).getId();
            final int serverId = DataManager.getFNPlayerManager().get(Settings.General.SERVER_VAR).getId();
            final int taskCreatorId = (sender instanceof Player ? fnPlayerId : serverId);

            // Name length is too long
            if (taskName.length() > Settings.Tasks.MAX_NAME_LENGTH){
                tell(msgs.get("error_task_name_too_long", sender).replace(
                        "@maxLength", String.valueOf(Settings.Tasks.MAX_NAME_LENGTH)));
                return;
            }

            // Create new Task instance
            if (taskType.getGroup().equalsIgnoreCase("Gathering")){
                GatheringTask task = new GatheringTask(townId, taskType.getId(), taskCreatorId, taskName);
                DataManager.getTaskManager().add(task);
            }
            else if (taskType.getGroup().equalsIgnoreCase("Mobs")){
                MobKillTask task = new MobKillTask(townId, taskType.getId(), taskCreatorId, taskName);
                DataManager.getTaskManager().add(task);
            }
            tell(msgs.get("success_task_created", sender)
                    .replace("@type", taskType.getName())
                    .replace("@name", taskName));
        }

        // Remove task - fn task remove <task_id>
        else if (args[0].equalsIgnoreCase("remove")){
            checkPermission("remove");

            // Wrong syntax
            if (args.length < 2) {
                tell(msgs.get("syntax_task_remove" , sender));
                return;
            }
            int taskId = findNumber(1, msgs.get("error_id_must_be_number", sender));

            DataManager.getTaskManager().remove(taskId);
            tell(msgs.get("success_task_removed", sender).replace("@id", String.valueOf(taskId)));
        }

        // Change any task value - fn task set <task_id> <variable> <value>
        else if (args[0].equalsIgnoreCase("set")){

            final List<String> taskFields = DataManager.getClassFields(Task.class);
            final List<String> shouldBeIntegers = DataManager.getClassFieldsByType(Task.class, int.class);
            final List<String> shouldBeDoubles = DataManager.getClassFieldsByType(Task.class, double.class);

            // Wrong syntax
            if (args.length < 4) {
                tell(msgs.get("syntax_task_set", sender));
                return;
            }

            final int taskId = findNumber(1, msgs.get("error_id_must_be_number", sender));
            final String variable = args[2];
            final String value = args[3];

            // Variable does not exist
            if (!taskFields.contains(args[2])){
                tell(msgs.get("error_variable_not_exist", sender));
                return;
            }

            checkPermission("setvalue." + variable);

            // Task does not exist
            if (!DataManager.getTaskManager().exists(taskId)){
                tell(msgs.get("error_task_not_exist", sender));
                return;
            }

            // Validations
            if (shouldBeDoubles.contains(variable.toLowerCase())){
                findNumber(Double.class, 3, msgs.get("error_value_must_be_double", sender));
            }
            else if (shouldBeIntegers.contains(variable.toLowerCase())){
                findNumber(3, msgs.get("error_value_must_be_integer", sender));
            }

            DataManager.getTaskManager().update(taskId, variable, value);
            tell(msgs.get("success_task_updated", sender));
        }

        // Get info about task - fn task info <task_id>
        else if (args[0].equalsIgnoreCase("info")){
            checkPermission("info");

            // Wrong syntax
            if (args.length < 2) {
                tell(msgs.get("syntax_task_info", sender));
                return;
            }
            int taskId = findNumber(1, msgs.get("error_id_must_be_number", sender));

            // Task does not exist
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

        // Get all existing tasks - fn task list
        else if (args[0].equalsIgnoreCase("list")){
            checkPermission("list");

            // No tasks are created yet
            if (DataManager.getTaskManager().getAll().isEmpty()){
                tell(msgs.get("error_task_no_tasks", sender));
                return;
            }

            Collection<Task> tasks = DataManager.getTaskManager().getAll().values();
            tell(msgs.get("info_task_info_label", sender));
            for (Task task : tasks){
                tell(task.getInfo());
            }

        }

        // Start task - fn task start <task_id>
        else if (args[0].equalsIgnoreCase("start")){
            checkPermission("start");

            PlayerData playerData = PlayerData.get(getPlayer().getUniqueId());

            // Wrong syntax
            if (args.length < 2) {
                tell(msgs.get("syntax_task_start", sender));
                return;
            }
            int taskId = findNumber(2, msgs.get("error_id_must_be_number", sender));

            // Task does not exist
            if (!DataManager.getTaskManager().exists(taskId)){
                tell(msgs.get("error_task_not_exist", sender));
                return;
            }
            Task task = DataManager.getTaskManager().get(taskId);

            // Task is not available anymore
            if (task.getTakeAmount() <= 0){
                tell(msgs.get("error_task_not_available", sender));
            }

            // Player's level is not suitable for this task
            if (playerData.getLevel() < task.getMinLevel() || playerData.getLevel() > task.getMaxLevel()){
                String msg = msgs.get("error_level_not_suitable", sender);
                msg = msg.replace("@playerLevel", String.valueOf(playerData.getLevel()))
                                .replace("@playerMinLevel", String.valueOf(task.getMinLevel()))
                                .replace("@playerMaxLevel", String.valueOf(task.getMaxLevel()));
                tell(msg);
                return;
            }

            // Player already own this task
            FNPlayer fnPlayer = DataManager.getFNPlayerManager().get(getPlayer().getName());
            if (DataManager.getTakenTaskManager().exists(fnPlayer.getId(), taskId)){
                tell(msgs.get("error_task_already_taken", sender));
                return;
            }

            DataManager.getTaskManager().update(taskId, "take_amount", task.getTakeAmount() - 1);
            tell(msgs.get("success_task_started", sender));
        }

        // Finish task - fn task finish <task_id>
        else if (args[0].equalsIgnoreCase("finish")){
            checkPermission("finish");

            PlayerData playerData = PlayerData.get(getPlayer().getUniqueId());

            // Wrong syntax
            if (args.length < 2) {
                tell(msgs.get("syntax_task_finish", sender));
                return;
            }
            int taskId = findNumber(2, "&cTask ID must be a number.");

            // Task does not exist
            if (!DataManager.getTaskManager().exists(taskId)){
                tell(msgs.get("error_task_not_exist", sender));
                return;
            }
            Task task = DataManager.getTaskManager().get(taskId);

            // Time for completion has expired
            if (task.getPlacementDateTime().getNanos() + task.getTimeToComplete() < LocalDateTime.now().getNano()){
                tell(msgs.get("error_task_time_to_complete_expired", sender));

                // TODO automatically takeAmount += 1 when task expires
//                DataManager.getTaskManager().update(taskId, "takeAmount", task.getTakeAmount() + 1);
                // TODO automatically remove takentask from player when task expires
                return;
            }

            // One or more objectives is not completed
            if (!task.isAllObjectivesCompleted(getPlayer(), taskId)){
                tell(msgs.get("error_task_objective_not_completed", sender));
                return;
            }

            // Player does not own this task
            FNPlayer fnPlayer = DataManager.getFNPlayerManager().get(getPlayer().getName());
            if (!DataManager.getTakenTaskManager().exists(fnPlayer.getId(), taskId)){
                tell(msgs.get("error_task_not_taken", sender));
                return;
            }

            // Give money reward
            FancyNations.getInstance().getEconomy().depositPlayer(getPlayer(), task.getMoneyReward());

            // Give reputation reward
            int repId = DataManager.getReputationsManager().get(fnPlayer.getId(), task.getTownId()).getId();
            Reputation reputation = DataManager.getReputationsManager().get(repId);
            DataManager.getReputationsManager().update(
                    repId, "amount", reputation.getAmount() + task.getRepReward());

            // Give experience reward
            playerData.giveExperience(task.getExpReward(), EXPSource.QUEST, getPlayer().getLocation(), false);

            // TODO if type==gathering then send 50% of resources to town
            // TODO if type==mobkill then ???

            // Remove TakenTask attached to this Task
            TakenTask takenTask = DataManager.getTakenTaskManager().get(fnPlayer.getId(), taskId);
            DataManager.getTakenTaskManager().remove(takenTask.getId());
            tell(msgs.get("success_task_finish", sender)
                    .replace("@money", String.valueOf(task.getMoneyReward()))
                    .replace("@exp", String.valueOf(task.getExpReward()))
                    .replace("@rep", String.valueOf(task.getRepReward()))
            );
        }

        // Cancel task - fn task cancel <task_id>
        else if (args[0].equalsIgnoreCase("cancel")){
            checkPermission("cancel");

            // Wrong syntax
            if (args.length < 2) {
                tell(msgs.get("syntax_task_cancel", sender));
                return;
            }
            int taskId = findNumber(2, msgs.get("error_id_must_be_number", sender));

            // Task does not exist
            if (!DataManager.getTaskManager().exists(taskId)){
                tell(msgs.get("error_task_not_exist", sender));
                return;
            }
            Task task = DataManager.getTaskManager().get(taskId);

            if (!DataManager.getTakenTaskManager().exists(task.getCreatorId(), task.getId())){
                int takenTaskId = DataManager.getTakenTaskManager().get(task.getCreatorId(), task.getId()).getId();
                DataManager.getTakenTaskManager().remove(takenTaskId);
                // TODO Remove TaskProgress - cascade on db but what about file system?
            }
            tell(msgs.get("success_task_cancelled", sender));
        }

        else{
            tell(msgs.get("syntax_task", sender));
        }
    }

    @Override
    protected List<String> tabComplete() {
        if (args.length == 1){
            return Arrays.asList("create", "remove", "set", "info");
        }
        // fn task create _<townId>_ <taskType>
        else if (args.length == 2 && args[0].equalsIgnoreCase("create")){
            List<String> towns = new ArrayList<>();
            DataManager.getTownManager().getAll().values().forEach(town -> towns.add(town.getName()));
            return towns;
        }
        // fn task create <townId> _<taskType>_
        else if (args.length == 3 && args[0].equalsIgnoreCase("create")){
            return DataManager.getTaskTypeManager().getNames();
        }
        // fn task remove _<townId>_
        else if (args.length == 2 && args[0].equalsIgnoreCase("remove")){
            return Collections.singletonList("<id>");
        }
        // fn task set <townId> _<var>_ <value>
        else if (args.length == 3 && args[0].equalsIgnoreCase("set")){
            return DataManager.getClassFields(Task.class);
        }
        // fn task set <townId> <var> _<value>_
        else if (args.length == 4 && args[0].equalsIgnoreCase("set")){
            return Collections.singletonList("<value>");
        }
        return new ArrayList<>();
    }
}
