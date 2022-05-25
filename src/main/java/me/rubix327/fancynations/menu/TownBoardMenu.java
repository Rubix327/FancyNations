package me.rubix327.fancynations.menu;

import me.rubix327.fancynations.data.DataManager;
import me.rubix327.fancynations.data.tasks.Task;
import me.rubix327.fancynations.data.tasks.TaskType;
import me.rubix327.fancynations.data.towns.Town;
import me.rubix327.fancynations.util.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.menu.AdvancedMenu;
import org.mineacademy.fo.menu.Menu;
import org.mineacademy.fo.menu.button.Button;
import org.mineacademy.fo.menu.button.ButtonMenu;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.remain.CompMaterial;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class TownBoardMenu extends AdvancedMenu {

    private final HashMap<Integer, ItemStack> itemSlots = new HashMap<>();
    private final ButtonMenu allTasks;

    public TownBoardMenu(Player player) {
        this(player, 1);
    }

    public TownBoardMenu(Player player, int townId) {
        super(player);
        setTitle("&aДоска заданий " + Town.getManager().get(townId).getName());
        setSize(9 * 6);
        setSlotNumbersVisible();

        List<Task> tasks = new ArrayList<>(DataManager.getTaskManager().getAllFor(townId).values());
        tasks.sort(Comparator.comparing(Task::getPriority));

        List<Task> foodTasks = new ArrayList<>();
        List<Task> resourceTasks = new ArrayList<>();
        List<Task> craftingTasks = new ArrayList<>();
        List<Task> mobKillTasks = new ArrayList<>();
        for (Task t : tasks) {
            if (t.getType() == TaskType.Food) {
                foodTasks.add(t);
            } else if (t.getType() == TaskType.Resource) {
                resourceTasks.add(t);
            } else if (t.getType() == TaskType.Crafting) {
                craftingTasks.add(t);
            } else if (t.getType() == TaskType.MobKill) {
                mobKillTasks.add(t);
            }
        }

        setSlots(createButtons(foodTasks, 3), 10, 11, 12);
        setSlots(createButtons(resourceTasks, 3), 14, 15, 16);
        setSlots(createButtons(craftingTasks, 3), 28, 29, 30);
        setSlots(createButtons(mobKillTasks, 3), 32, 33, 34);

        allTasks = new ButtonMenu(new TasksListMenu(getPlayer(), townId), CompMaterial.ENCHANTED_BOOK, "&7Все задания города");
    }

    private Material getMaterial(TaskType type) {
        return MenuUtil.Tasks.getItemMaterial(type);
    }

    private void setSlots(List<Button> list, int first, int second, int third) {
        itemSlots.put(first, list.get(0).getItem());
        itemSlots.put(second, list.get(1).getItem());
        itemSlots.put(third, list.get(2).getItem());
    }

    @SuppressWarnings("SameParameterValue")
    private List<Button> createButtons(List<Task> list, int amount) {
        List<Button> buttons = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            int finalI = i;
            buttons.add(new Button() {
                @Override
                public void onClickedInMenu(Player player, Menu menu, ClickType click) {
                    tell("123");
                }

                @Override
                public ItemStack getItem() {
                    return makeItem(list, finalI + 1);
                }
            });
        }
        return buttons;
    }

    @Override
    public ItemStack getItemAt(int slot) {

        if (itemSlots.containsKey(slot)) {
            return itemSlots.get(slot);
        } else if (slot == 49) {
            return allTasks.getItem();
        } else {
            return getWrapperItem();
        }

    }

    /**
     * Get the item that should be placed on the specified number, or default item.
     *
     * @param list   where to get items from
     * @param number serial number of button if that particular group
     * @return ready ItemStack
     */
    private ItemStack makeItem(List<Task> list, int number) {
        String defaultId = MainPanelMenu.getItemMaterials().get(TaskType.No).toString();
        if (list.size() < number) {
            return ItemCreator.of(CompMaterial.fromString(defaultId), "&7Задание еще не готово... ").build().make();
        }
        Task task = list.get(number - 1);

        return ItemCreator.of(
                MenuUtil.Tasks.getItemMaterial(task.getType()).toString(),
                MenuUtil.Tasks.getName(task),
                (Utils.isPlayerAdmin(getPlayer()) ?
                        MenuUtil.Tasks.getAdminLore(task, getPlayer()) :
                        MenuUtil.Tasks.getPlayerLore(task, getPlayer()))
        ).glow(MenuUtil.Tasks.isTaken(task, getPlayer())).build().make();
    }
}