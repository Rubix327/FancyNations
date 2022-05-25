package me.rubix327.fancynations.menu;

import me.rubix327.fancynations.data.DataManager;
import me.rubix327.fancynations.data.tasks.Task;
import me.rubix327.fancynations.data.tasks.TaskType;
import me.rubix327.fancynations.data.towns.Town;
import me.rubix327.fancynations.util.Logger;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.menu.Menu;
import org.mineacademy.fo.menu.button.Button;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.remain.CompMaterial;
import org.mineacademy.fo.remain.CompSound;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TasksListMenu extends MenuPaginated<Task> {

    private boolean filterNotAvailable = false;
    private short sortingMode = 0;
    private int townId = 0;

    public TasksListMenu(Player player) {
        super(player);

        setTitle("&lСписок всех заданий");
        setSize(9 * 6);
        setLockedSlots("9x6_rows");
        addButton(48, getFilterNotAvailableButton());
        addButton(49, getSortingButton());
        addButton(50, getCompleteAllButton());
        enableDebug();
        init();
    }

    public TasksListMenu(Player player, int townId) {
        super(player);
        this.townId = townId;

        setTitle("&lСписок заданий " + Town.getManager().get(townId).getName());
        setSize(9 * 6);
        setLockedSlots("9x6_rows");
        addButton(48, getFilterNotAvailableButton());
        addButton(49, getSortingButton());
        addButton(50, getCompleteAllButton());
        enableDebug();
        init();
    }

    private Button getFilterNotAvailableButton() {
        return new Button() {
            @Override
            public void onClickedInMenu(Player player, Menu menu, ClickType click) {
                MenuUtil.Play.POP(player);
                filterNotAvailable = !filterNotAvailable;
                updateMenu();
            }

            @Override
            public ItemStack getItem() {
                String name = "&7» Фильтр";
                return (filterNotAvailable ?
                        ItemCreator.of(CompMaterial.GREEN_BANNER, name, "", "&aНажмите, чтобы показать задания", "&aс неподходящими условиями").build().make() :
                        ItemCreator.of(CompMaterial.RED_BANNER, name, "", "&cНажмите, чтобы убрать задания", "&cс неподходящими условиями").build().make());
            }
        };
    }

    private Button getSortingButton() {
        return new Button() {
            @Override
            public void onClickedInMenu(Player player, Menu menu, ClickType click) {
                if (sortingMode >= 2) {
                    sortingMode = 0;
                } else {
                    sortingMode += 1;
                }
                MenuUtil.Play.POP(player);
                updateMenu();
            }

            @Override
            public ItemStack getItem() {
                String name = "&7» Сортировка";
                String lore = "&8Режим:";
                if (sortingMode == 1)
                    return ItemCreator.of(CompMaterial.GREEN_BANNER, name, "", lore + "&2 порядковый номер").build().make();
                if (sortingMode == 2)
                    return ItemCreator.of(CompMaterial.PURPLE_BANNER, name, "", lore + "&d лучшие награды").build().make();
                else
                    return ItemCreator.of(CompMaterial.CYAN_BANNER, name, "", lore + "&b высший приоритет").build().make();
            }
        };
    }

    private Button getCompleteAllButton() {
        return new Button() {
            @Override
            public void onClickedInMenu(Player player, Menu menu, ClickType click) {
                short finished = 0;
                for (Task task : getElements()) {
                    if (MenuUtil.Tasks.isTaken(task, player) && task.isAllObjectivesReadyToComplete(player)
                            && !MenuUtil.Tasks.isTimeExpired(task, player)) {
                        task.complete(getPlayer());
                        msgs.locTell("success_all_tasks_finished", player);
                        finished += 1;
                        MenuUtil.Play.LEVEL_UP(player);
                        updateMenu();
                    }
                }
                if (finished == 0) {
                    msgs.locTell("info_no_tasks_to_complete", player);
                    MenuUtil.Play.NO(player);
                }
            }

            @Override
            public ItemStack getItem() {
                return ItemCreator.of(CompMaterial.LIME_BANNER, "&a» Сдать все задания").build().make();
            }
        };
    }

    @Override
    protected List<Task> getElements() {
        List<Task> tasks = new ArrayList<>(DataManager.getTaskManager().getAll().values());

        if (sortingMode == 0) {
            tasks.sort((o1, o2) -> (o1.getPriority() < o2.getPriority() ? 1 : o1.getPriority() != o2.getPriority() ? -1 :
                    o1.getCompletionsLeft() < o2.getCompletionsLeft() ? -1 :
                            o1.getCompletionsLeft() != o2.getCompletionsLeft() ? 1 : 0));
        } else if (sortingMode == 1) {
            tasks.sort(Comparator.comparingInt(Task::getId));
        } else if (sortingMode == 2) {
            tasks.sort((o1, o2) -> o1.getMoneyReward() < o2.getMoneyReward() ? 1 : o1.getMoneyReward() != o2.getMoneyReward() ? -1 :
                    o1.getExpReward() < o2.getExpReward() ? 1 : o1.getExpReward() != o2.getExpReward() ? -1 :
                            o1.getRepReward() < o2.getRepReward() ? 1 : o1.getRepReward() != o2.getRepReward() ? -1 : 0);
        }

        if (townId != 0) {
            tasks = tasks.stream().filter(task -> task.getTownId() == this.townId).toList();
        }

        if (isDebugEnabled()) Logger.info("Sorted elements: " + tasks.stream().map(Task::getId).toList());
        if (filterNotAvailable) {
            tasks = tasks.stream().filter(t -> MenuUtil.Tasks.isAllConditionsKept(t, getPlayer())).toList();
        }
        if (!isPlayerAdmin) {
            tasks = tasks.stream().filter(t -> t.getType() != TaskType.No).toList();
            tasks = tasks.stream().filter(task -> task.getCompletionsLeft() != 0).toList();
            tasks = tasks.stream().filter(task -> !MenuUtil.Tasks.isTimeExpired(task, getPlayer())).toList();
        }
        return tasks;
    }

    @Override
    protected void onElementClick(Player player, Task task, int slot, ClickType clickType) {

        tell(clickType.toString());
        if (clickType == ClickType.LEFT) {
            if (MenuUtil.Tasks.isTaken(task, player)) {
                // Cannot be completed
                if (!task.isAllObjectivesReadyToComplete(player)) {
                    MenuUtil.Play.NO(player);
                    msgs.locTell("error_task_objective_not_completed", getPlayer());
                    return;
                }
                if (MenuUtil.Tasks.isTimeExpired(task, player)) {
                    MenuUtil.Play.NO(player);
                    msgs.locTell("error_task_time_to_complete_expired", getPlayer());
                    updateMenu();
                    return;
                }
                // Task completion
                MenuUtil.Play.LEVEL_UP(player);
                task.complete(player);
                msgs.locTell("success_task_finished", getPlayer(), msgs.replace("@id", task.getId()));
            } else {
                if (!MenuUtil.Tasks.isAllConditionsKept(task, player)) {
                    MenuUtil.Play.NO(player);
                    msgs.locTell("error_task_conditions_not_kept", getPlayer());
                    return;
                }
                if (!MenuUtil.Tasks.isRequirementsSet(task)) {
                    MenuUtil.Play.NO(player);
                    msgs.locTell("error_task_requirements_not_set", getPlayer());
                    return;
                }
                // Task taking
                MenuUtil.Play.ORB(player);
                task.take(player);
                msgs.locTell("success_task_started", getPlayer(), msgs.replace("@id", task.getId()));
            }

        } else if (clickType == ClickType.MIDDLE) {
            // Cannot cancel because not taken
            if (!MenuUtil.Tasks.isTaken(task, player)) {
                MenuUtil.Play.NO(player);
                msgs.locTell("error_task_cannot_cancel", getPlayer());
                return;
            }
            // Task cancellation
            task.cancel(player);
            CompSound.NOTE_PLING.play(player, 1F, 0.2F);
            msgs.locTell("success_task_cancelled", getPlayer(), msgs.replace("@id", task.getId()));
        }
        this.updateMenu();
    }

    @Override
    protected ItemStack convertToItemStack(Task task) {
        return ItemCreator.of(
                MenuUtil.Tasks.getItemMaterial(task.getType()).toString(),
                MenuUtil.Tasks.getName(task),
                (isPlayerAdmin ?
                        MenuUtil.Tasks.getAdminLore(task, getPlayer()) :
                        MenuUtil.Tasks.getPlayerLore(task, getPlayer()))
        ).glow(MenuUtil.Tasks.isTaken(task, getPlayer())).build().make();
    }

}
