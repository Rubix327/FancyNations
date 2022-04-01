package me.rubix327.fancynations.commands;

import lombok.SneakyThrows;
import me.rubix327.fancynations.data.DataManager;
import me.rubix327.fancynations.data.task.GatheringTask;
import me.rubix327.fancynations.data.task.Task;
import me.rubix327.fancynations.data.task.TaskType;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.command.SimpleCommand;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.remain.CompMaterial;
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
    @SneakyThrows
    @Override
    protected void onCommand() {

        if (args[0].equalsIgnoreCase("new")){
            GatheringTask gt = new GatheringTask("SunRise", TaskType.Food, "Rubix327", "123");
            DataManager.getTaskManager().add(gt);
        }
        if (args[0].equalsIgnoreCase("add")){
            int taskId = findNumber(1, "&cNo");

            if (!(DataManager.getTaskManager().get(taskId).getTaskType() == TaskType.Food)) returnTell("&cNot required type");

            GatheringTask gt = (GatheringTask) DataManager.getTaskManager().get(taskId);
            gt.addObjective(args[2], findNumber(3, "&cNot number"));
        }
        else if (args[0].equalsIgnoreCase("remove")){
            int taskId = findNumber(1, "&cNo");
            String itemId = args[1];
            int amount = findNumber(2, "&cNot a number");

            if (!DataManager.getTaskManager().exists(taskId)) returnTell("&cDoes not exist");
            if (!(DataManager.getTaskManager().get(taskId).getTaskType() == TaskType.Food)) returnTell("&cNot required type");

            DataManager.getTaskManager().get(taskId).removeObjective(itemId);
        }
        else if (args[0].equalsIgnoreCase("items")){
            int taskId = findNumber(1, "&cNo");
            if (!(DataManager.getTaskManager().get(taskId).getTaskType() == TaskType.Food)) returnTell("&cNot required type");
            Task gt1 = DataManager.getTaskManager().get(taskId);
            for (Map.Entry<String, Integer> entry : gt1.getObjectives().entrySet() ){
                tell(entry.getKey() + " - " + entry.getValue());
            }
        }
        else if (args[0].equalsIgnoreCase("list")){
            for (Task task : DataManager.getTaskManager().getTasks().values()){
                tell(task.toString());
            }
        }
        else if (args[0].equalsIgnoreCase("item")){
            getPlayer().getInventory().addItem(ItemCreator.of(CompMaterial.PAPER, "&fЗадание №1 &7(крафтинг)",
                    "&8Городское (Rubix327)", "", "&8Требования:", "&a✔ Большое зелье здоровья x2",
                    "&a✔ Большое зелье маны х3", "&c✖ Магическая эссенция х2", "",
                    "&8Условия:", "&a✔ Уровень: 1-15", "&a✔ Время на выполнение: 12ч", "",
                    "&8Награды:", "&a- Монеты х300", "&a- Опыт х150", "&a- Репутация х5", "",
                    "&eНажмите, чтобы взять задание").build().make());
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
