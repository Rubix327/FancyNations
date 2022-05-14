package me.rubix327.fancynations.commands;

import me.rubix327.fancynations.data.objectives.Objective;
import me.rubix327.fancynations.data.objectives.ObjectiveInfo;
import me.rubix327.fancynations.data.tasks.Task;
import me.rubix327.fancynations.util.ItemUtils;
import org.mineacademy.fo.command.SimpleCommandGroup;

public class ObjectiveCommands extends SubCommandInterlayer{
    protected ObjectiveCommands(SimpleCommandGroup group, String subLabel, String permLabel) {
        super(group, subLabel, permLabel);
    }

    @Override
    protected void onCommand() {

        if (args.length == 0){
            checkPermission("help");
            locReturnTell("syntax_objective");
        }

        // Create new objective - /fn objective create <type> <target> <amount>
        if (isArg(0, "create")){
            checkPermission("create");
            checkArgs(4, getMsg("syntax_objective_create"));

            String type = args[1];
            String target = args[2];
            int amount = findNumber(3, getMsg("error_amount_should_be_number"));

            if (!ObjectiveInfo.getObjectiveTypes().contains(args[1])){
                locReturnTell("error_objective_type_not_exist");
            }

            // Get item in hand.
            // If there's no item tell the error message.
            if (isArg(2, "@hand")){
                checkConsole();
                target = ItemUtils.extractItemId(getPlayer().getInventory().getItemInMainHand());
                if (target.equalsIgnoreCase("AIR")) locReturnTell("error_objective_item_in_hand");
            }

            Objective.add(type, target, amount);
        }

        // Remove objective - /fn objective remove <obj_id>
        if (isArg(0, "remove")){
            checkPermission("remove");

            // Wrong syntax
            checkArgs(2, getMsg("syntax_objective_remove"));
            int objectiveId = findNumber(1, getMsg("error_id_must_be_number"));

            Objective.remove(objectiveId);
            locTell("success_task_removed", replace("@id", objectiveId));
        }

        // Set the task for the objective - /fn objective set <obj_id> <task_id>
        if (isArg(0, "set|setTask")){
            checkPermission("set");

            // Wrong syntax
            checkArgs(3, getMsg("syntax_objective_set"));
            int taskId = findNumber(1, getMsg("error_id_must_be_number"));
            int objectiveId = findNumber(2, getMsg("error_id_must_be_number"));

            if (!Task.exists(taskId)) locReturnTell("error_task_not_exist");
            if (!Objective.exists(objectiveId)) locReturnTell("error_objective_not_exist");

            Objective.setTask(objectiveId, taskId);
        }

    }
}
