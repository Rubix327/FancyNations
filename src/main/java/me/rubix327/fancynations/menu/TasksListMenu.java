package me.rubix327.fancynations.menu;

import me.rubix327.fancynations.Localization;
import me.rubix327.fancynations.data.DataManager;
import me.rubix327.fancynations.data.tasks.Task;
import me.rubix327.fancynations.data.tasks.TaskType;
import me.rubix327.fancynations.data.towns.Town;
import me.rubix327.fancynations.menu.sorting.IDSortingMode;
import me.rubix327.fancynations.menu.sorting.ISortingMode;
import me.rubix327.fancynations.util.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.SoundUtil;
import org.mineacademy.fo.menu.AdvancedMenu;
import org.mineacademy.fo.menu.AdvancedMenuPagged;
import org.mineacademy.fo.menu.LockedSlotsFigure;
import org.mineacademy.fo.menu.button.Button;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.remain.CompMaterial;
import org.mineacademy.fo.remain.CompSound;

import java.util.List;

public class TasksListMenu extends AdvancedMenuPagged<Task> {

    private final Localization msgs = Localization.getInstance();
    private ISortingMode sortingMode = IDSortingMode.get();
    private boolean filterNotAvailable = false;
    private final int townId;

    public TasksListMenu(Player player) {
        this(player, 0);
    }

    @Override
    protected void setup() {
        Common.setTellPrefix(null);
        setTitle(townId == 0 ? "Все задания" : "Задания " + Town.getManager().get(townId).getName());
        setSize(9 * 6);
        setLockedSlots(LockedSlotsFigure.ROWS_9X6);
        addButton(48, getFilterNotAvailableButton());
        addButton(49, getSortingButton());
        addButton(50, getCompleteAllButton());
        addButton(53, getMenuButton(new TownBoardMenu(getPlayer(), townId), ItemCreator.of(CompMaterial.OAK_TRAPDOOR).name("&7Назад").make()));
    }

    public TasksListMenu(Player player, int townId) {
        super(player);
        this.townId = townId;
    }

    private Button getFilterNotAvailableButton() {
        return new Button() {
            @Override
            public void onClickedInMenu(Player player, AdvancedMenu menu, ClickType click) {
                SoundUtil.Play.POP(player);
                filterNotAvailable = !filterNotAvailable;
                refreshMenu();
            }

            @Override
            public ItemStack getItem() {
                String name = "&7» Фильтр";
                return (filterNotAvailable ?
                        ItemCreator.of(CompMaterial.GREEN_BANNER, name, "", "&aНажмите, чтобы показать задания", "&aс неподходящими условиями").make() :
                        ItemCreator.of(CompMaterial.RED_BANNER, name, "", "&cНажмите, чтобы убрать задания", "&cс неподходящими условиями").make());
            }
        };
    }

    private Button getSortingButton() {
        return new Button() {
            @Override
            public void onClickedInMenu(Player player, AdvancedMenu menu, ClickType click) {
                sortingMode = sortingMode.getNext();
                SoundUtil.Play.POP(player);
                refreshMenu();
            }

            @Override
            public ItemStack getItem() {
                return sortingMode.getItem();
            }
        };
    }

    private Button getCompleteAllButton() {
        return new Button() {
            @Override
            public void onClickedInMenu(Player player, AdvancedMenu menu, ClickType click) {
                short finished = 0;
                for (Task task : getElements()) {
                    if (MenuUtil.Tasks.isTaken(task, player) && task.isAllObjectivesReadyToComplete(player)
                            && !MenuUtil.Tasks.isTimeExpired(task, player)) {
                        task.complete(getPlayer());
                        msgs.locTell("success_all_tasks_finished", player);
                        finished += 1;
                        SoundUtil.Play.LEVEL_UP(player);
                        refreshMenu();
                    }
                }
                if (finished == 0) {
                    msgs.locTell("info_no_tasks_to_complete", player);
                    SoundUtil.Play.NO(player);
                }
            }

            @Override
            public ItemStack getItem() {
                return ItemCreator.of(CompMaterial.LIME_BANNER, "&a» Сдать все задания").make();
            }
        };
    }

    @Override
    protected List<Task> getElements() {
        List<Task> tasks = sortingMode.getSorted(DataManager.getTaskManager().getAll().values());

        if (townId != 0) {
            tasks = tasks.stream().filter(task -> task.getTownId() == this.townId).toList();
        }

        if (filterNotAvailable) {
            tasks = tasks.stream().filter(t -> MenuUtil.Tasks.isAllConditionsKept(t, getPlayer())).toList();
        }
        if (!Utils.isPlayerAdmin(getPlayer())) {
            tasks = tasks.stream().filter(t -> t.getType() != TaskType.No).toList();
            tasks = tasks.stream().filter(task -> task.getCompletionsLeft() != 0).toList();
            tasks = tasks.stream().filter(task -> !MenuUtil.Tasks.isTimeExpired(task, getPlayer())).toList();
        }
        return tasks;
    }

    @Override
    protected void onElementClick(Player player, Task task, int slot, ClickType clickType) {
        if (clickType == ClickType.LEFT) {
            if (MenuUtil.Tasks.isTaken(task, player)) {
                // Cannot be completed
                if (!task.isAllObjectivesReadyToComplete(player)) {
                    SoundUtil.Play.NO(player);
                    msgs.locTell("error_task_objective_not_completed", getPlayer());
                    return;
                }
                if (MenuUtil.Tasks.isTimeExpired(task, player)) {
                    SoundUtil.Play.NO(player);
                    msgs.locTell("error_task_time_to_complete_expired", getPlayer());
                    refreshMenu();
                    return;
                }
                // Task completion
                SoundUtil.Play.LEVEL_UP(player);
                task.complete(player);
                msgs.locTell("success_task_finished", getPlayer(), msgs.replace("@id", task.getId()));
            } else {
                if (!MenuUtil.Tasks.isAllConditionsKept(task, player)) {
                    SoundUtil.Play.NO(player);
                    msgs.locTell("error_task_conditions_not_kept", getPlayer());
                    return;
                }
                if (!MenuUtil.Tasks.isRequirementsSet(task)) {
                    SoundUtil.Play.NO(player);
                    msgs.locTell("error_task_requirements_not_set", getPlayer());
                    return;
                }
                // Task taking
                SoundUtil.Play.ORB(player);
                task.take(player);
                msgs.locTell("success_task_started", getPlayer(), msgs.replace("@id", task.getId()));
            }

        } else if (clickType == ClickType.MIDDLE) {
            // Cannot cancel because not taken
            if (!MenuUtil.Tasks.isTaken(task, player)) {
                SoundUtil.Play.NO(player);
                msgs.locTell("error_task_cannot_cancel", getPlayer());
                return;
            }
            // Task cancellation
            task.cancel(player);
            CompSound.NOTE_PLING.play(player, 1F, 0.2F);
            msgs.locTell("success_task_cancelled", getPlayer(), msgs.replace("@id", task.getId()));
        }
        this.refreshMenu();
    }

    @Override
    protected ItemStack convertToItemStack(Task task) {
        return ItemCreator.of(
                        MenuUtil.Tasks.getItemMaterial(task.getType()))
                .name(MenuUtil.Tasks.getName(task))
                .lore((Utils.isPlayerAdmin(getPlayer()) ?
                        MenuUtil.Tasks.getAdminLore(task, getPlayer()) :
                        MenuUtil.Tasks.getPlayerLore(task, getPlayer()))
                ).hideTags(true)
                .glow(MenuUtil.Tasks.isTaken(task, getPlayer())).make();
    }

}
