package me.rubix327.fancynations.commands;

import lombok.SneakyThrows;
import me.rubix327.fancynations.data.DataManager;
import me.rubix327.fancynations.data.objectives.ObjectiveInfo;
import me.rubix327.fancynations.data.tasks.Task;
import org.bukkit.Location;
import org.mineacademy.fo.command.SimpleCommand;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.remain.CompMaterial;

public class TestCommands extends SimpleCommand {
    public TestCommands() {
        super("test");
        setTellPrefix("");
    }

    // test add <task_id> <item> <amount>
    // test remove <task_id> <item> <amount>
    // test list
    @SneakyThrows
    @Override
    protected void onCommand() {

        if (args[0].equalsIgnoreCase("loc0")){
            tell(String.valueOf(getPlayer().getInventory().getItemInMainHand().getItemMeta().hasLocalizedName()));
            tell(getPlayer().getInventory().getItemInMainHand().getItemMeta().getLocalizedName());
        }
        if (args[0].equalsIgnoreCase("new")){
            Task task = new Task(1, "123", 1, "Player");
            DataManager.getTaskManager().add(task);
        }
        if (args[0].equalsIgnoreCase("add")){
            int taskId = findNumber(1, "&cNo");

            Task gt = DataManager.getTaskManager().get(taskId);
        }
        else if (args[0].equalsIgnoreCase("remove")){
            int taskId = findNumber(1, "&cNo");
            String itemId = args[1];
            int amount = findNumber(2, "&cNot a number");

        }
        else if (args[0].equalsIgnoreCase("item")){
            getPlayer().getInventory().addItem(ItemCreator.of(CompMaterial.PAPER, "&fЗадание №1 &7(крафтинг)",
                    "&8Городское (Rubix327)", "", "&8Требования:", "&a✔ Большое зелье здоровья x2",
                    "&a✔ Большое зелье маны х3", "&c✖ Магическая эссенция х2", "",
                    "&8Условия:", "&a✔ Уровень: 1-15", "&a✔ Время на выполнение: 12ч", "",
                    "&8Награды:", "&a- Монеты х300", "&a- Опыт х150", "&a- Репутация х5", "",
                    "&eНажмите, чтобы взять задание").make());
        }
        else if (args[0].equalsIgnoreCase("progress")){
            getPlayer().getInventory().addItem(ItemCreator.of(CompMaterial.REDSTONE,
                    "&7#52",
                    "&aAdded Logger and debug commands.",
                    "&aAdded MythicLib as a soft dependency.",
                    "&aAdded DependencyManager.",
                    "",
                    "&8Объект: &7FancyNations",
                    "&8Автор: &7Rubix327",
                    "&8Дата: &710.05.2022 01:38 МСК").make());
        }
        else if (args[0].equalsIgnoreCase("progress_en")){
            getPlayer().getInventory().addItem(ItemCreator.of(CompMaterial.REDSTONE,
                    "&7#52",
                    "&aAdded Logger and debug commands.",
                    "&aAdded MythicLib as a soft dependency.",
                    "&aAdded DependencyManager.",
                    "",
                    "&8Object: &7FancyNations",
                    "&8Author: &7Rubix327",
                    "&8Date: &710.05.2022 01:38 GMT+3").make());
        }
        else if (args[0].equalsIgnoreCase("loc")){
            tell(getPlayer().getLocation().toString());
            tell(DataManager.serializeLocation(getPlayer().getLocation()));
            Location loc = DataManager.deserializeLocation(DataManager.serializeLocation(getPlayer().getLocation()));
            tell(loc.getBlockX() + " " + loc.getBlockY() + " " + loc.getZ());
        }
        else if (args[0].equalsIgnoreCase("objtypes")){
            tell(ObjectiveInfo.getFormattedString());
        }

    }

}
