package me.rubix327.fancynations.commands;

import lombok.SneakyThrows;
import me.rubix327.fancynations.data.DataManager;
import me.rubix327.fancynations.data.tasks.GatheringTask;
import me.rubix327.fancynations.data.tasks.Task;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.command.SimpleCommand;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.remain.CompMaterial;
import org.mineacademy.fo.remain.nbt.NBTContainer;
import org.mineacademy.fo.remain.nbt.NBTItem;

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
            GatheringTask gt = new GatheringTask(1, 1, "Rubix327", "123");
            DataManager.getTaskManager().add(gt);
        }
        if (args[0].equalsIgnoreCase("add")){
            int taskId = findNumber(1, "&cNo");

            GatheringTask gt = (GatheringTask) DataManager.getTaskManager().get(taskId);
        }
        else if (args[0].equalsIgnoreCase("remove")){
            int taskId = findNumber(1, "&cNo");
            String itemId = args[1];
            int amount = findNumber(2, "&cNot a number");

        }
        else if (args[0].equalsIgnoreCase("list")){
            for (Task task : DataManager.getTaskManager().getAll().values()){
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
