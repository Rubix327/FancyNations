package me.rubix327.fancynations.commands;

import me.rubix327.fancynations.Localization;
import me.rubix327.fancynations.Settings;
import me.rubix327.fancynations.data.DataManager;
import me.rubix327.fancynations.data.fnplayers.FNPlayer;
import me.rubix327.fancynations.data.objectives.Objective;
import me.rubix327.fancynations.data.reputations.Reputation;
import me.rubix327.fancynations.data.takentasks.TakenTask;
import me.rubix327.fancynations.data.taskprogresses.TaskProgress;
import me.rubix327.fancynations.data.tasks.Task;
import me.rubix327.fancynations.data.townresources.TownResource;
import me.rubix327.fancynations.util.Utils;
import net.Indyuce.mmocore.api.player.PlayerData;
import net.Indyuce.mmocore.experience.EXPSource;
import org.bukkit.entity.Player;
import org.mineacademy.fo.command.SimpleCommandGroup;

import java.time.LocalDateTime;
import java.util.*;

public class TaskCommands extends SubCommandInterlayer {
    protected TaskCommands(SimpleCommandGroup group, String sublabel, String permLabel) {
        super(group, sublabel, permLabel);
    }

    /*
       label  a0       a1          a2         a3
    fn task create <town_name> <taskName>
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

        // Create new task - fn task create <town_name> <taskName>
        if (args[0].equalsIgnoreCase("create")){
            checkPermission("create");

            int townId;

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

            final String taskName = String.join(" ", Arrays.asList(args).subList(3, args.length));
            final int taskCreatorId =
                    (sender instanceof Player ?
                    DataManager.getFNPlayerManager().get(getPlayer().getName()).getId() :
                    DataManager.getFNPlayerManager().get(Settings.General.SERVER_VAR).getId());

            // Name length is too long
            if (taskName.length() > Settings.Tasks.MAX_NAME_LENGTH){
                tell(msgs.get("error_task_name_too_long", sender).replace(
                        "@maxLength", String.valueOf(Settings.Tasks.MAX_NAME_LENGTH)));
                return;
            }

            // Create new Task instance
            Task task = new Task(townId, taskCreatorId, taskName);
            DataManager.getTaskManager().add(task);
            tell(msgs.get("success_task_created", sender)
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
                if (!Utils.isStringDouble(args[3])) {
                    tell(msgs.get("error_value_must_be_double", sender));
                    return;
                }
            }
            else if (shouldBeIntegers.contains(variable.toLowerCase())){
                if (!Utils.isStringInt(args[3])) {
                    tell(msgs.get("error_value_must_be_integer", sender));
                    return;
                }
            }

            DataManager.getTaskManager().update(taskId, variable, value);
            tell(msgs.get("success_task_updated", sender)
                    .replace("@id", String.valueOf(taskId)));
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
            int timeDays = task.getTimeToComplete() / 86400;
            int timeHours = task.getTimeToComplete() % 86400 / 3600;
            int timeMinutes = task.getTimeToComplete() % 86400 % 3600 / 60;
            int timeSeconds = task.getTimeToComplete() % 60;

            List<String> info = Settings.Messages_Templates.TASK_INFO;
            final String finalInfo = Localization.getInstance().replacePlaceholders(info)
                    .replace("@template_task_info_label", msgs.get("template_task_info_label", sender))
                    .replace("@template_task_info_name", msgs.get("template_task_info_name", sender))
                    .replace("@template_task_info_type", msgs.get("template_task_info_type", sender))
                    .replace("@template_task_info_town", msgs.get("template_task_info_town", sender))
                    .replace("@template_task_info_created_by", msgs.get("template_task_info_created_by", sender))
                    .replace("@template_task_info_completions_left", msgs.get("template_task_info_completions_left", sender))
                    .replace("@template_task_info_money_reward", msgs.get("template_task_info_money_reward", sender))
                    .replace("@template_task_info_exp_reward", msgs.get("template_task_info_exp_reward", sender))
                    .replace("@template_task_info_rep_reward", msgs.get("template_task_info_rep_reward", sender))
                    .replace("@template_task_info_priority", msgs.get("template_task_info_priority", sender))
                    .replace("@template_task_info_levels", msgs.get("template_task_info_levels", sender))
                    .replace("@template_task_info_description", msgs.get("template_task_info_description", sender))
                    .replace("@template_task_info_time_to_complete", msgs.get("template_task_info_time_to_complete", sender))
                    .replace("@id", String.valueOf(taskId))
                    .replace("@name", String.valueOf(task.getTaskName()))
                    .replace("@type", task.getLocalizedTypeName(sender))
                    .replace("@town", task.getTownName())
                    .replace("@created_by", task.getLocalizedCreatorName(sender))
                    .replace("@comp_left", String.valueOf(task.getTakeAmount()))
                    .replace("@money_reward", String.valueOf(task.getMoneyReward()))
                    .replace("@exp_reward", String.valueOf(task.getExpReward()))
                    .replace("@rep_reward", String.valueOf(task.getRepReward()))
                    .replace("@priority", String.valueOf(task.getPriority()))
                    .replace("@min_level", String.valueOf(task.getMinLevel()))
                    .replace("@max_level", String.valueOf(task.getMaxLevel()))
                    .replace("@description", task.getLocalizedDescription(sender))
                    .replace("@days", String.valueOf(timeDays))
                    .replace("@hours", String.valueOf(timeHours))
                    .replace("@minutes", String.valueOf(timeMinutes))
                    .replace("@seconds", String.valueOf(timeSeconds));

            tell(finalInfo);
        }

        // Get all existing tasks - fn task list [page]
        else if (args[0].equalsIgnoreCase("list")){
            checkPermission("list");

            int currentPage = 1;
            int maxPage = 1;
            int elementsPerPage = 8;

            // No tasks are created yet
            if (DataManager.getTaskManager().getAll().isEmpty()){
                tell(msgs.get("error_task_no_tasks", sender));
                return;
            }

            if (args.length == 2){
                if (!Utils.isStringInt(args[1])){
                    tell(msgs.get("error_page_should_be_number", sender));
                    return;
                }
                maxPage = DataManager.getTaskManager().getAll().size() / elementsPerPage;
            }

            final Collection<Task> tasks = DataManager.getTaskManager().getAll().values();
            final List<String> listHeader = Settings.Messages_Templates.TASK_LIST_HEADER;
            final List<String> listFooter = Settings.Messages_Templates.TASK_LIST_FOOTER;

            // Adding header
            List<String> generalList = new ArrayList<>(listHeader);

            // Adding elements
            for (Task task: tasks){
                String elementTemplate = msgs.get("template_task_list_element", sender);

                final String type = task.getLocalizedTypeName(sender);
                final String town = task.getTownName();
                final String creator = task.getLocalizedCreatorName(sender);

                String element = elementTemplate
                        .replace("@id", String.valueOf(task.getId()))
                        .replace("@type", type)
                        .replace("@town", town)
                        .replace("@creator", creator)
                        .replace("@name", task.getTaskName());

                generalList.add(element);
            }

            // Adding footer
            generalList.addAll(listFooter);

            String tasksList = Localization.getInstance().replacePlaceholders(generalList)
                    .replace("@template_task_list_label", msgs.get("template_task_list_label", sender))
                    .replace("@template_task_list_example", msgs.get("template_task_list_example", sender))
                    .replace("@template_task_list_element", msgs.get("template_task_list_element", sender))
                    .replace("@current", String.valueOf(currentPage))
                    .replace("@max", String.valueOf(maxPage));

            tell(tasksList);

        }

        // Start task - fn task start <task_id>
        else if (args[0].equalsIgnoreCase("start")){
            checkPermission("start");

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

            // Player's MMO level is not suitable for this task
            if (dependencies.IS_MMOCORE_LOADED) {
                PlayerData playerData = PlayerData.get(getPlayer().getUniqueId());
                if (playerData.getLevel() < task.getMinLevel() || playerData.getLevel() > task.getMaxLevel()) {
                    String msg = msgs.get("error_level_not_suitable", sender);
                    msg = msg.replace("@player_level", String.valueOf(playerData.getLevel()))
                            .replace("@task_min_level", String.valueOf(task.getMinLevel()))
                            .replace("@task_max_level", String.valueOf(task.getMaxLevel()));
                    tell(msg);
                    return;
                }
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

        // Finish task - fn task finish <taken_task_id>
        else if (args[0].equalsIgnoreCase("finish")){
            checkPermission("finish");

            // Wrong syntax
            if (args.length < 2) {
                tell(msgs.get("syntax_task_finish", sender));
                return;
            }
            int takenTaskId = findNumber(2, msgs.get("error_id_must_be_number", sender));

            // Taken task does not exist
            if (!DataManager.getTakenTaskManager().exists(takenTaskId)){
                tell(msgs.get("error_taken_task_not_exist", sender));
                return;
            }
            TakenTask takenTask = DataManager.getTakenTaskManager().get(takenTaskId);

            // Time for completion has expired
            // Despite we have runTaskExpireListener in DataManager,
            // here we additionally check if the task is currently available just in case.
            Task task = DataManager.getTaskManager().get(takenTask.getTaskId());
            if (task.getPlacementDateTime().getNanos() + task.getTimeToComplete() < LocalDateTime.now().getNano()){
                DataManager.getInstance().resetTakenTask(takenTask);
                tell(msgs.get("error_task_time_to_complete_expired", sender));
                return;
            }

            // One or more objectives is not completed
            if (!task.isAllObjectivesCompleted(getPlayer())){
                tell(msgs.get("error_task_objective_not_completed", sender));
                return;
            }

            // Player does not own this task
            FNPlayer fnPlayer = FNPlayer.getFNPlayer(getPlayer().getName());
            if (!DataManager.getTakenTaskManager().exists(fnPlayer.getId(), task.getId())){
                tell(msgs.get("error_task_not_taken", sender));
                return;
            }

            // Give money reward
            economy.depositPlayer(getPlayer(), task.getMoneyReward());

            // Give reputation reward
            Reputation.increase(fnPlayer.getId(), task.getTownId(), task.getRepReward());

            // Give MMO experience reward
            if (dependencies.IS_MMOCORE_LOADED){
                PlayerData.get(getPlayer().getUniqueId())
                        .giveExperience(task.getExpReward(), EXPSource.QUEST, getPlayer().getLocation(), false);
            }

            // Send a certain share of resources or mobs to a town
            for (Objective objective : DataManager.getObjectivesManager().getAllFor(task.getId()).values()){
                Integer share = (objective.getGroup().equalsIgnoreCase("Gathering") ?
                        Settings.Rewards.TOWN_RESOURCE_SHARE : Settings.Rewards.TOWN_MOBS_SHARE);
                TownResource townResource = new TownResource(task.getTownId(), objective.getTarget(),
                        objective.getAmount() / 100 * share);
                DataManager.getTownResourceManager().add(townResource);
            }

            // Remove TakenTask attached to this Task
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
                for (TaskProgress progress : DataManager.getTaskProgressManager().getAllByTakenTask(takenTaskId).values()){
                    DataManager.getTaskProgressManager().remove(progress.getId());
                }
                DataManager.getTakenTaskManager().remove(takenTaskId);
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
        // fn task create _<townId>_ <name>
        else if (args.length == 2 && args[0].equalsIgnoreCase("create")){
            List<String> towns = new ArrayList<>();
            DataManager.getTownManager().getAll().values().forEach(town -> towns.add(town.getName()));
            return towns;
        }
        // fn task create <townId> _<name>_
        else if (args.length == 4 && args[0].equalsIgnoreCase("create")){
            return Collections.singletonList("<name>");
        }
        // fn task remove _<taskId>_
        else if (args.length == 2 && args[0].equalsIgnoreCase("remove")){
            return Collections.singletonList("<task_id>");
        }
        // fn task set _<taskId>_ <var> <value>
        else if (args.length == 2 && args[0].equalsIgnoreCase("set")){
            return Collections.singletonList("<task_id>");
        }
        // fn task set <taskId> _<var>_ <value>
        else if (args.length == 3 && args[0].equalsIgnoreCase("set")){
            return DataManager.getClassFields(Task.class);
        }
        // fn task set <taskId> <var> _<value>_
        else if (args.length == 4 && args[0].equalsIgnoreCase("set")){
            return Collections.singletonList("<value>");
        }
        // fn task info _<taskId>_
        else if (args.length == 2 && args[0].equalsIgnoreCase("info")){
            return Collections.singletonList("<task_id>");
        }
        return new ArrayList<>();
    }
}
