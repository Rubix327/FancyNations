package me.rubix327.fancynations.data.tasks;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.rubix327.fancynations.Localization;
import me.rubix327.fancynations.Settings;
import org.bukkit.command.CommandSender;
import org.mineacademy.fo.remain.CompMaterial;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents types that a task or objective can have.
 */
@AllArgsConstructor
public enum TaskType {
    No("task_type_no_objectives", CompMaterial.PAPER),
    Food("task_type_food", TaskGroup.Gathering, Settings.Rewards.TOWN_RESOURCE_SHARE, CompMaterial.PORKCHOP),
    Resource("task_type_resource", TaskGroup.Gathering, Settings.Rewards.TOWN_RESOURCE_SHARE, CompMaterial.GRANITE),
    Crafting("task_type_crafting", TaskGroup.Gathering, Settings.Rewards.TOWN_RESOURCE_SHARE, CompMaterial.CHAINMAIL_CHESTPLATE),
    MobKill("task_type_mob_kill", TaskGroup.Mobs, Settings.Rewards.TOWN_MOBS_SHARE, CompMaterial.SKELETON_SKULL),
    Combined("task_type_combined", CompMaterial.BLAZE_POWDER);

    @Getter
    private final String locale;
    @Getter
    private final TaskGroup group;
    @Getter
    private final Integer townShare;
    @Getter
    private final CompMaterial material;
    @Getter
    private final boolean isObjectiveType;

    TaskType(String locale, CompMaterial material) {
        this(locale, TaskGroup.Other, 0, material, false);
    }

    TaskType(String locale, TaskGroup group, Integer townShare, CompMaterial material) {
        this(locale, group, townShare, material, true);
    }

    /**
     * Get the name of the type translated to the language of this sender.
     *
     * @param sender the sender of the command
     * @return translated name
     */
    public String getLocalizedName(CommandSender sender) {
        return Localization.getInstance().get(getLocale(), sender);
    }

    /**
     * Get only those task types that are objective types at the same time.
     */
    public static List<TaskType> getObjectiveTypes() {
        return Arrays.stream(values()).filter(TaskType::isObjectiveType).collect(Collectors.toList());
    }

    public static List<String> getObjectiveTypesStrings() {
        return getObjectiveTypes().stream().map(Enum::toString).collect(Collectors.toList());
    }

    public static TaskType getOrNull(String taskType) {
        try {
            return TaskType.valueOf(taskType);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}