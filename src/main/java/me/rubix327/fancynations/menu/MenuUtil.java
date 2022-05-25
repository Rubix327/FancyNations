package me.rubix327.fancynations.menu;

import me.rubix327.fancynations.data.fnplayers.FNPlayer;
import me.rubix327.fancynations.data.objectives.Objective;
import me.rubix327.fancynations.data.takentasks.TakenTask;
import me.rubix327.fancynations.data.tasks.CreatorType;
import me.rubix327.fancynations.data.tasks.Task;
import me.rubix327.fancynations.data.tasks.TaskGroup;
import me.rubix327.fancynations.data.tasks.TaskType;
import me.rubix327.fancynations.util.DependencyManager;
import me.rubix327.fancynations.util.ItemUtils;
import me.rubix327.fancynations.util.Utils;
import net.Indyuce.mmocore.api.player.PlayerData;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.remain.CompMaterial;
import org.mineacademy.fo.remain.CompSound;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

public final class MenuUtil {

    final static class Tasks {

        private static final HashMap<TaskType, Material> itemMaterials = new HashMap<>() {{
            put(TaskType.Food, CompMaterial.PORKCHOP.toMaterial());
            put(TaskType.Resource, CompMaterial.GRANITE.toMaterial());
            put(TaskType.Crafting, CompMaterial.CHAINMAIL_CHESTPLATE.toMaterial());
            put(TaskType.MobKill, CompMaterial.SKELETON_SKULL.toMaterial());
            put(TaskType.No, CompMaterial.PAPER.toMaterial());
            put(TaskType.Combined, CompMaterial.BLAZE_POWDER.toMaterial());
        }};

        static String getName(Task task) {
            return "&f" + task.getName();
        }

        static List<String> getPlayerLore(Task task, Player player) {
            List<String> lore = new ArrayList<>();

            String category = "&7Категория: " + task.getLocalizedTypeName(player);
            String creatorName = (task.isServerCreated() ? task.getTown().getNation().getName() : task.getCreator().getName());
            String creator = "Государственное";

            if (task.getCreatorType() == CreatorType.Nation) {
                creator = "Государственное (" + creatorName + ")";
            } else if (task.getCreatorType() == CreatorType.Town) {
                creator = "Городское (" + creatorName + ")";
            } else if (task.getCreatorType() == CreatorType.Player) {
                creator = "Гражданское (" + creatorName + ")";
            }

            lore.add(category);
            lore.add("&8" + creator);
            lore.addAll(Arrays.asList("", "&8Требования:"));
            lore.addAll(getObjectives(task, player));
            lore.addAll(Arrays.asList("", "&8Условия:"));
            lore.addAll(getConditions(task, player));
            lore.add("");
            lore.addAll(getRewards(task));
            lore.add("");
            lore.addAll(getClickToTake(task, player));

            return lore;
        }

        static List<String> getAdminLore(Task task, Player player) {
            List<String> lore = getPlayerLore(task, player);
            lore.add("");
            lore.add("&8Город: " + task.getTown().getName());
            lore.add("&8Всего взято: " + task.getCount());
            lore.add("&8Приоритет: " + task.getPriority());

            return lore;
        }

        private static List<String> getObjectives(Task task, Player player) {
            List<String> s = new ArrayList<>();
            if (task.getType() == TaskType.No) {
                s.add(Utils.NO + "Не установлены");
                return s;
            }
            for (Objective objective : Objective.getAllFor(task.getId()).values()) {
                s.add((objective.isReadyToComplete(player) ? Utils.YES : Utils.NO)
                        + getObjectiveName(objective) + " x" + objective.getAmount() + "\n");
            }
            return s;
        }

        private static String getObjectiveName(Objective objective) {
            if (objective.getGroup().equalsIgnoreCase(TaskGroup.Gathering.toString())) {
                return ItemUtils.getItemName(objective.getTarget());
            }
            return "Моб1";
        }

        private static List<String> getConditions(Task task, Player player) {
            List<String> s = new ArrayList<>();
            if (getLevels(task, player) != null) s.add(getLevels(task, player));
            if (getTime(task, player) != null) s.addAll(getTime(task, player));
            s.add(getCompletions(task));
            return s;
        }

        private static String getLevels(Task task, Player player) {
            if (task.getMinLevel() == 1 && task.getMaxLevel() == Integer.MAX_VALUE) return null;
            String s = "Уровень: " + task.getMinLevel() + "-" + task.getMaxLevel();
            return (isLevelAppropriate(task, player) ? Utils.YES + s : Utils.NO + s + " (Ваш: " + getPlayerLevel(player) + ")");
        }

        private static List<String> getTime(Task task, Player player) {
            String s = "Время на выполнение: " + Utils.timeToString(task.getTimeToComplete(), player);
            if (Timestamp.valueOf(LocalDateTime.now().plusSeconds(task.getTimeToComplete())).compareTo(task.getTerminationDateTime()) > 0) {
                return Arrays.asList(Utils.WARN + "&6" + s, "  &6Задание удалится через 15 сек! ");
            }
            return List.of(Utils.YES + s);
        }

        private static String getCompletions(Task task) {
            return Utils.YES + "Осталось выполнений: " + task.getCompletionsLeft();
        }

        private static List<String> getRewards(Task task) {
            List<String> s = new ArrayList<>();
            if (task.getMoneyReward() != 0) s.add("&a- Монеты: x" + (int) task.getMoneyReward());
            if (task.getExpReward() != 0) s.add("&a- Опыт: x" + (int) task.getExpReward());
            if (task.getRepReward() != 0) s.add("&a- Репутация: x" + task.getRepReward());
            if (s.size() != 0) {
                Collections.reverse(s);
                s.add("&8Награды:");
                Collections.reverse(s);
            }
            if (s.size() == 0) return new ArrayList<>(List.of("&8Награды: &7нет"));
            return s;
        }

        private static List<String> getClickToTake(Task task, Player player) {
            if (isTaken(task, player)) {
                return Arrays.asList("&eНажмите ЛКМ, чтобы сдать задание", "&7Нажмите колесиком, чтобы отменить задание");
            } else {
                return isAllConditionsKept(task, player) ?
                        List.of("&eНажмите, чтобы взять задание") :
                        Arrays.asList("&cВы не можете взять это задание!", "&cСначала выполните все условия!");
            }
        }

        @SuppressWarnings("RedundantIfStatement")
        static boolean isAllConditionsKept(Task task, Player player) {
            if (!isLevelAppropriate(task, player)) return false;
            if (task.getCompletionsLeft() <= 0) return false;
            if (isTimeExpired(task, player)) return false;
            return true;
        }

        static boolean isRequirementsSet(Task task) {
            return task.getType() != TaskType.No;
        }

        private static boolean isLevelAppropriate(Task task, Player player) {
            int level = getPlayerLevel(player);
            return level >= task.getMinLevel() && level <= task.getMaxLevel();
        }

        private static int getPlayerLevel(Player player) {
            return (DependencyManager.getInstance().IS_MMOCORE_LOADED ?
                    PlayerData.get(player.getUniqueId()).getLevel() : player.getLevel());
        }

        static boolean isTimeExpired(Task task, Player player) {
            return false; // TODO
        }

        static Material getItemMaterial(TaskType type) {
            return itemMaterials.get(type);
        }

        static boolean isTaken(Task task, Player player) {
            return TakenTask.getManager().exists(FNPlayer.get(player.getName()).getId(), task.getId());
        }

        static boolean isReadyToComplete(Task task, Player player) {
            if (isTaken(task, player)) {
                return !task.isAllObjectivesReadyToComplete(player);
            }
            return false;
        }

    }

    static class Play {
        static void LEVEL_UP(Player player) {
            CompSound.LEVEL_UP.play(player);
        }

        static void NO(Player player) {
            CompSound.VILLAGER_NO.play(player, 1F, 0.85F);
        }

        static void ORB(Player player) {
            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 1.4F);
        }

        static void CLICK_LOW(Player player) {
            CompSound.CLICK.play(player, 0.5F, 0.8F);
        }

        static void CLICK_HIGH(Player player) {
            CompSound.CLICK.play(player, 0.5F, 1.2F);
        }

        static void POP(Player player) {
            CompSound.CHICKEN_EGG_POP.play(player, 0.5F, 1F);
        }
    }

    public static ItemStack getWrapperItem() {
        return ItemCreator.of(CompMaterial.GRAY_STAINED_GLASS_PANE, "").build().make();
    }

}
