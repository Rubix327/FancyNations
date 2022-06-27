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
import me.rubix327.itemslangapi.Lang;
import net.Indyuce.mmocore.api.player.PlayerData;
import org.bukkit.entity.Player;
import org.mineacademy.fo.remain.CompMaterial;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

public final class MenuUtil {

    final static class Tasks {

        private static final HashMap<TaskType, CompMaterial> itemMaterials = new HashMap<>() {{
            put(TaskType.Food, CompMaterial.PORKCHOP);
            put(TaskType.Resource, CompMaterial.GRANITE);
            put(TaskType.Crafting, CompMaterial.CHAINMAIL_CHESTPLATE);
            put(TaskType.MobKill, CompMaterial.SKELETON_SKULL);
            put(TaskType.No, CompMaterial.PAPER);
            put(TaskType.Combined, CompMaterial.BLAZE_POWDER);
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
            lore.add("&8ID: " + task.getId());
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
                        + getObjectiveName(objective, Lang.valueOf(player.getLocale().toUpperCase()))
                        + " x" + objective.getAmount() + "\n");
            }
            return s;
        }

        private static String getObjectiveName(Objective objective, Lang lang) {
            if (objective.getGroup().equals(TaskGroup.Gathering)) {
                return ItemUtils.getItemName(objective.getTarget(), lang);
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
            return (DependencyManager.MMO_CORE.isLoaded() ?
                    PlayerData.get(player.getUniqueId()).getLevel() : player.getLevel());
        }

        static boolean isTimeExpired(Task task, Player player) {
            return false; // TODO
        }

        static CompMaterial getItemMaterial(TaskType type) {
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

}
