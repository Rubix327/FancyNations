package me.rubix327.fancynations.menu;

import me.rubix327.fancynations.data.DataManager;
import me.rubix327.fancynations.data.tasks.Task;
import me.rubix327.fancynations.data.tasks.TaskType;
import me.rubix327.fancynations.data.towns.Town;
import me.rubix327.fancynations.util.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.menu.AdvancedMenu;
import org.mineacademy.fo.menu.button.Button;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.remain.CompMaterial;
import org.mineacademy.fo.remain.CompMetadata;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TownBoardMenu extends AdvancedMenu {

    private final int townId;

    public TownBoardMenu(Player player) {
        this(player, 0);
    }

    @Override
    protected void setup() {
        if (townId == 0) return;
        setTitle("Доска заданий " + Town.getManager().get(townId).getName());
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

        addButton(49, getMenuButton(new TasksListMenu(getPlayer(), this.townId), ItemCreator.of(CompMaterial.ENCHANTED_BOOK, "&7Все задания города").make()));
    }

    public TownBoardMenu(Player player, int townId) {
        super(player);
        this.townId = townId;
    }

    private void setSlots(List<Button> list, int first, int second, int third) {
        addButton(first, list.get(0));
        addButton(second, list.get(1));
        addButton(third, list.get(2));
    }

    @SuppressWarnings("SameParameterValue")
    private List<Button> createButtons(List<Task> list, int amount) {
        List<Button> buttons = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            int finalI = i;
            buttons.add(new Button() {
                @Override
                public void onClickedInMenu(Player player, AdvancedMenu menu, ClickType click) {
                    if (CompMetadata.hasMetadata(this.getItem(), "task_not_ready")){
                        return;
                    }
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

        if (getButtons().containsKey(slot)) {
            return getButtons().get(slot).getItem();
        } else if (slot == 49) {
            return getButtons().get(49).getItem();
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
        if (list.size() < number) {
            return ItemCreator.of(TaskType.No.getMaterial())
                    .name("&7Задание еще не готово... ")
                    .tag("task_not_ready", "true")
                    .make();
        }
        Task task = list.get(number - 1);

        return ItemCreator.of(MenuUtil.Tasks.getItemMaterial(task.getType()))
                .name(MenuUtil.Tasks.getName(task))
                .lore(Utils.isPlayerAdmin(getPlayer()) ?
                        MenuUtil.Tasks.getAdminLore(task, getPlayer()) :
                        MenuUtil.Tasks.getPlayerLore(task, getPlayer()))
                .hideTags(true)
                .glow(MenuUtil.Tasks.isTaken(task, getPlayer())).make();
    }
}