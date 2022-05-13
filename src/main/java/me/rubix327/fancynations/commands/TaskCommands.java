package me.rubix327.fancynations.commands;

import me.rubix327.fancynations.Localization;
import me.rubix327.fancynations.Settings;
import me.rubix327.fancynations.data.DataManager;
import me.rubix327.fancynations.data.fnplayers.FNPlayer;
import me.rubix327.fancynations.data.objectives.Objective;
import me.rubix327.fancynations.data.objectives.ObjectiveInfo;
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
    protected TaskCommands(SimpleCommandGroup group, String subLabel, String permLabel) {
        super(group, subLabel, permLabel);
    }

    @Override
    protected void onCommand() {

        if (args.length == 0){
            checkPermission("help");
            locReturnTell("syntax_task");
        }

        // Create new task - fn task create <town_name> <taskName>
        if (args[0].equalsIgnoreCase("create")){
            checkPermission("create");

            int townId;

            // Wrong syntax
            if (args.length < 4) {
                locReturnTell("syntax_task_create");
            }

            // Town does not exist
            if (!DataManager.getTownManager().exists(args[1])){
                locReturnTell("error_town_not_exist");
            }
            townId = (DataManager.getTownManager().get(args[1])).getId();

            final String taskName = String.join(" ", Arrays.asList(args).subList(3, args.length));
            final int taskCreatorId =
                    (sender instanceof Player ?
                    DataManager.getFNPlayerManager().get(getPlayer().getName()).getId() :
                    DataManager.getFNPlayerManager().get(Settings.General.SERVER_VAR).getId());

            // Name length is too long
            if (taskName.length() > Settings.Tasks.MAX_NAME_LENGTH){
                locReturnTell("error_task_name_too_long", replace("@max_length", Settings.Tasks.MAX_NAME_LENGTH));
            }

            // Create new Task instance
            Task task = new Task(townId, taskCreatorId, taskName);
            DataManager.getTaskManager().add(task);
            locTell("success_task_created", replace("@name", taskName));
        }

        // Remove task - fn task remove <task_id>
        else if (args[0].equalsIgnoreCase("remove")){
            checkPermission("remove");

            // Wrong syntax
            checkArgs(2, getMsg("syntax_task_remove"));

            int taskId = findNumber(1, getMsg("error_id_must_be_number"));

            DataManager.getTaskManager().remove(taskId);
            locTell("success_task_removed", replace("@id", taskId));
        }

        // Change any task value - fn task set <task_id> <variable> <value>
        else if (args[0].equalsIgnoreCase("set")){

            final List<String> taskFields = DataManager.getClassFields(Task.class);
            final List<String> shouldBeIntegers = DataManager.getClassFieldsByType(Task.class, int.class);
            final List<String> shouldBeDoubles = DataManager.getClassFieldsByType(Task.class, double.class);

            // Wrong syntax
            checkArgs(4, getMsg("syntax_task_set"));

            final int taskId = findNumber(1, getMsg("error_id_must_be_number"));
            final String variable = args[2];
            final String value = args[3];

            // Variable does not exist
            if (!taskFields.contains(args[2])){
                locReturnTell("error_variable_not_exist");
            }

            checkPermission("setvalue." + variable);

            // Task does not exist
            if (!DataManager.getTaskManager().exists(taskId)){
                locReturnTell("error_task_not_exist");
            }

            // Validations
            if (shouldBeDoubles.contains(variable.toLowerCase())){
                if (!Utils.isStringDouble(args[3])) {
                    locReturnTell("error_value_must_be_double");
                }
            }
            else if (shouldBeIntegers.contains(variable.toLowerCase())){
                if (!Utils.isStringInt(args[3])) {
                    locReturnTell("error_value_must_be_integer");
                }
            }

            DataManager.getTaskManager().update(taskId, variable, value);
            locTell("success_task_updated", replace("@id", taskId));
        }

        // Get info about task - fn task info <task_id>
        else if (args[0].equalsIgnoreCase("info")){
            checkPermission("info");

            // Wrong syntax
            checkArgs(2, getMsg("syntax_task_info"));
            int taskId = findNumber(1, getMsg("error_id_must_be_number"));

            // Task does not exist
            if (!DataManager.getTaskManager().exists(taskId)){
                locReturnTell("error_task_not_exist");
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
                    .replace("@name", String.valueOf(task.getName()))
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
                locReturnTell("error_task_no_tasks");
            }

            if (args.length == 2){
                if (!Utils.isStringInt(args[1])){
                    locReturnTell("error_page_should_be_number");
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
                String elementTemplate = getMsg("template_task_list_element");

                final String type = task.getLocalizedTypeName(sender);
                final String town = task.getTownName();
                final String creator = task.getLocalizedCreatorName(sender);

                String element = elementTemplate
                        .replace("@id", String.valueOf(task.getId()))
                        .replace("@type", type)
                        .replace("@town", town)
                        .replace("@creator", creator)
                        .replace("@name", task.getName());

                generalList.add(element);
            }

            // Adding footer
            generalList.addAll(listFooter);

            String tasksList = Localization.getInstance().replacePlaceholders(generalList)
                    .replace("@template_task_list_label", getMsg("template_task_list_label"))
                    .replace("@template_task_list_example", getMsg("template_task_list_example"))
                    .replace("@template_task_list_element", getMsg("template_task_list_element"))
                    .replace("@current", String.valueOf(currentPage))
                    .replace("@max", String.valueOf(maxPage));

            tell(tasksList);

        }

        // Start task - fn task start <task_id>
        else if (args[0].equalsIgnoreCase("start")){
            checkPermission("start");

            // Wrong syntax
            checkArgs(2, "syntax_task_start");
            int taskId = findNumber(2, getMsg("error_id_must_be_number"));

            // Task does not exist
            if (!DataManager.getTaskManager().exists(taskId)){
                locReturnTell("error_task_not_exist");
            }
            Task task = DataManager.getTaskManager().get(taskId);

            // Task is not available anymore
            if (task.getTakeAmount() <= 0){
                locReturnTell("error_task_not_available");
            }

            // Player's MMO level is not suitable for this task
            if (dependencies.IS_MMOCORE_LOADED) {
                PlayerData playerData = PlayerData.get(getPlayer().getUniqueId());
                if (playerData.getLevel() < task.getMinLevel() || playerData.getLevel() > task.getMaxLevel()) {
                    locReturnTell("error_level_not_suitable",
                            replace("@player_level", playerData.getLevel()),
                            replace("@task_min_level", task.getMinLevel()),
                            replace("@task_max_level", task.getMaxLevel()));
                }
            }

            // Player already own this task
            FNPlayer fnPlayer = DataManager.getFNPlayerManager().get(getPlayer().getName());
            if (DataManager.getTakenTaskManager().exists(fnPlayer.getId(), taskId)){
                locReturnTell("error_task_already_taken");
            }

            Task.increaseTakeAmount(taskId, -1);
            locTell("success_task_started");
        }

        // Finish task - fn task finish <taken_task_id>
        else if (args[0].equalsIgnoreCase("finish")){
            checkPermission("finish");

            // Wrong syntax
            checkArgs(2, getMsg("syntax_task_finish"));
            int takenTaskId = findNumber(2, getMsg("error_id_must_be_number"));

            // Taken task does not exist
            if (!DataManager.getTakenTaskManager().exists(takenTaskId)){
                locReturnTell("error_taken_task_not_exist");
            }
            TakenTask takenTask = DataManager.getTakenTaskManager().get(takenTaskId);

            // Time for completion has expired
            // Despite we have runTaskExpireListener in DataManager,
            // here we additionally check if the task is currently available just in case.
            Task task = DataManager.getTaskManager().get(takenTask.getTaskId());
            if (task.getPlacementDateTime().getNanos() + task.getTimeToComplete() < LocalDateTime.now().getNano()){
                DataManager.getInstance().resetTakenTask(takenTask);
                locReturnTell("error_task_time_to_complete_expired");
            }

            // One or more objectives is not completed
            if (!task.isAllObjectivesCompleted(getPlayer())){
                locReturnTell("error_task_objective_not_completed");
            }

            // Player does not own this task
            FNPlayer fnPlayer = FNPlayer.getFNPlayer(getPlayer().getName());
            if (!DataManager.getTakenTaskManager().exists(fnPlayer.getId(), task.getId())){
                locReturnTell("error_task_not_taken");
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
                TownResource townResource = new TownResource(task.getTownId(), objective.getTarget(),
                        objective.getAmount() / 100 * ObjectiveInfo.get(objective.getType()).getShare());
                DataManager.getTownResourceManager().add(townResource);
            }

            // Remove TakenTask attached to this Task
            DataManager.getTakenTaskManager().remove(takenTask.getId());
            locTell("success_task_finish",
                    replace("@money", task.getMoneyReward()),
                    replace("@exp", task.getExpReward()),
                    replace("@rep", task.getRepReward()));
        }

        // Cancel task - fn task cancel <task_id>
        else if (args[0].equalsIgnoreCase("cancel")){
            checkPermission("cancel");

            // Wrong syntax
            checkArgs(2, getMsg("syntax_task_cancel"));
            int taskId = findNumber(2, getMsg("error_id_must_be_number"));

            // Task does not exist
            if (!DataManager.getTaskManager().exists(taskId)){
                locReturnTell("error_task_not_exist");
            }
            Task task = DataManager.getTaskManager().get(taskId);

            // Remove connected task progresses
            if (!DataManager.getTakenTaskManager().exists(task.getCreatorId(), task.getId())){
                int takenTaskId = DataManager.getTakenTaskManager().get(task.getCreatorId(), task.getId()).getId();
                for (TaskProgress progress : DataManager.getTaskProgressManager().getAllByTakenTask(takenTaskId).values()){
                    DataManager.getTaskProgressManager().remove(progress.getId());
                }
                DataManager.getTakenTaskManager().remove(takenTaskId);
            }
            Task.increaseTakeAmount(taskId, 1);
            locTell("success_task_cancelled");
        }

        // /fn task addObjective <task_id> <objective_id>
        else if (isArg(0, "addObjective|addObj")){
            checkPermission("addObjective");

            checkArgs(3, getMsg("syntax_task_add_objective"));
            int taskId = findNumber(1, getMsg("error_id_must_be_number"));
            int objectiveId = findNumber(2, getMsg("error_id_must_be_number"));

            if (!Task.exists(taskId)) locReturnTell("error_task_not_exist");
            if (!Objective.exists(objectiveId)) locReturnTell("error_objective_not_exist");

            if (DataManager.getObjectivesManager().get(objectiveId).getTask() == Settings.General.NULL){
                locReturnTell("error_objective_already_has_task");
            }

            Objective.setTask(objectiveId, taskId);
        }

        // /fn task removeObjective <task_id> <objective_id>
        else if (isArg(0, "removeObjective")){
            checkPermission("removeObjective");

            checkArgs(2, getMsg("syntax_task_remove_objective"));
            int taskId = findNumber(1, getMsg("error_id_must_be_number"));
            int objectiveId = findNumber(2, getMsg("error_id_must_be_number"));

            if (!Task.exists(taskId)) locReturnTell("error_task_not_exist");
            if (!Objective.exists(objectiveId)) locReturnTell("error_objective_not_exist");

            if (!DataManager.getObjectivesManager().getAllFor(taskId).containsKey(objectiveId)){
                locReturnTell("error_task_not_contain_this_objective");
            }

            Objective.setTask(objectiveId, Settings.General.NULL);
        }

        else{
            locReturnTell("syntax_task");
        }
    }

    @Override
    protected List<String> tabComplete() {
        if (args.length == 1){
            return Arrays.asList("create", "remove", "set", "info", "start", "finish", "cancel", "list", "addObj", "removeObj");
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
        // fn task info|start|finish|cancel _<taskId>_
        else if (args.length == 2 && isArg(0, "info|start|finish|cancel")){
            return Collections.singletonList("<task_id>");
        }
        // fn task addObjective|removeObjective _<task_id>_ <objective_id>
        else if (args.length == 2 && isArg(0, "addObjective|addObj|removeObjective|removeObj")){
            return Collections.singletonList("<task_id>");
        }
        // fn task addObjective|removeObjective <task_id> _<objective_id>_
        else if (args.length == 3 && isArg(0, "addObjective|addObj|removeObjective|removeObj")){
            return Collections.singletonList("<objective_id>");
        }
        return new ArrayList<>();
    }
}
