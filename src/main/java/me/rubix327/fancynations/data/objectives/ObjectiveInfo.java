package me.rubix327.fancynations.data.objectives;

import lombok.Getter;
import me.rubix327.fancynations.Settings;
import me.rubix327.fancynations.data.tasks.TaskGroup;
import me.rubix327.fancynations.data.tasks.TaskType;

import java.util.HashMap;
import java.util.List;

@Getter
public class ObjectiveInfo {

    @Getter
    private final static HashMap<TaskType, ObjectiveInfo> objectiveInfo = new HashMap<>();
    private static boolean initiated = false;

    private final TaskGroup group;
    private final Integer share;
    private final String localeNode;

    public ObjectiveInfo(TaskGroup group, Integer share, String localeNode) {
        this.group = group;
        this.share = share;
        this.localeNode = localeNode;
    }

    public static void init(){
        if (initiated) return;
        ObjectiveInfo.add(TaskType.Food, new ObjectiveInfo(
                TaskGroup.Gathering, Settings.Rewards.TOWN_RESOURCE_SHARE, "task_type_food"));
        ObjectiveInfo.add(TaskType.Resource, new ObjectiveInfo(
                TaskGroup.Gathering, Settings.Rewards.TOWN_RESOURCE_SHARE, "task_type_resource"));
        ObjectiveInfo.add(TaskType.Crafting, new ObjectiveInfo(
                TaskGroup.Gathering, Settings.Rewards.TOWN_RESOURCE_SHARE, "task_type_crafting"));
        ObjectiveInfo.add(TaskType.MobKill, new ObjectiveInfo(
                TaskGroup.Mobs, Settings.Rewards.TOWN_MOBS_SHARE, "task_type_mob_kill"));
        initiated = true;
    }

    public static ObjectiveInfo get(String name){
        return ObjectiveInfo.getObjectiveInfo().get(TaskType.valueOf(name));
    }

    public static void add(TaskType name, ObjectiveInfo objType){
        ObjectiveInfo.getObjectiveInfo().put(name, objType);
    }

    /**
     * Get all the objective types names.
     * @return names list
     */
    public static List<String> getStringList(){
        return ObjectiveInfo.getObjectiveInfo().keySet().stream().map(Object::toString).toList();
    }

    /**
     * Get all the objective types names in string separated by comma.
     * @return names string separated by comma
     */
    public static String getFormattedString(){
        return String.join(", ", getStringList());
    }

    public static HashMap<TaskType, String> getLocales(){
        HashMap<TaskType, String> locales = new HashMap<>();
        ObjectiveInfo.getObjectiveInfo().forEach((key, value) -> locales.put(key, value.getLocaleNode()));
        return locales;
    }

    public static List<String> getObjectiveTypes(){
        return ObjectiveInfo.getObjectiveInfo().keySet().stream().map(TaskType::toString).toList();
    }
}