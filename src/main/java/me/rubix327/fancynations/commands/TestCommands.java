package me.rubix327.fancynations.commands;

import me.rubix327.fancynations.data.task.GatheringTask;
import me.rubix327.fancynations.data.task.Task;
import me.rubix327.fancynations.data.task.TaskManager;
import me.rubix327.fancynations.data.task.TaskType;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.command.SimpleCommand;
import org.mineacademy.fo.remain.nbt.NBTContainer;
import org.mineacademy.fo.remain.nbt.NBTItem;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestCommands extends SimpleCommand {
    public TestCommands() {
        super("test");
    }

    public static ItemStack item;
    // test add <task_id> <item> <amount>
    // test remove <task_id> <item> <amount>
    // test list
    @Override
    protected void onCommand() {

        if (args[0].equalsIgnoreCase("new")){
            GatheringTask gt = new GatheringTask("SunRise", TaskType.Food, "Rubix327", "123");
            TaskManager.add(gt.getId(), gt);
        }
        if (args[0].equalsIgnoreCase("add")){
            int taskId = findNumber(1, "&cNo");

            if (!(TaskManager.getById(taskId).getTaskType() == TaskType.Food)) returnTell("&cNot required type");

            GatheringTask gt = (GatheringTask) TaskManager.getById(taskId);
            gt.addItem(args[2], findNumber(3, "&cNot number"));
        }
        else if (args[0].equalsIgnoreCase("remove")){
            int taskId = findNumber(1, "&cNo");
            String itemId = args[1];
            int amount = findNumber(2, "&cNot a number");

            if (!TaskManager.exists(taskId)) returnTell("&cDoes not exist");
            if (!(TaskManager.getById(taskId).getTaskType() == TaskType.Food)) returnTell("&cNot required type");
            ((GatheringTask) TaskManager.getById(taskId)).removeItem(itemId);
        }
        else if (args[0].equalsIgnoreCase("items")){
            int taskId = findNumber(1, "&cNo");
            if (!(TaskManager.getById(taskId).getTaskType() == TaskType.Food)) returnTell("&cNot required type");
            GatheringTask gt1 = (GatheringTask)TaskManager.getById(taskId);
            for (Map.Entry<String, Integer> entry : gt1.getObjectiveItems().entrySet() ){
                tell(entry.getKey() + " - " + entry.getValue());
            }
        }
        else if (args[0].equalsIgnoreCase("list")){
            for (Task task : TaskManager.getTasks().values()){
                tell(task.toString());
            }
        }

    }

    protected String extractItemId(ItemStack item) {

        if (item == null) return "AIR";

        NBTContainer nbtItem = NBTItem.convertItemtoNBT(item);

        if (nbtItem.toString().contains("MMOITEMS_ITEM_ID")){

            String match = "";
            Pattern pattern = Pattern.compile("(MMOITEMS_ITEM_ID:\")+([^\"]*)");
            Matcher matcher = pattern.matcher(nbtItem.toString());
            while(matcher.find())
                match = matcher.group().replace("MMOITEMS_ITEM_ID:\"", "");
                return match;
        }
        else{
            return item.getType().toString();
        }

    }
}
