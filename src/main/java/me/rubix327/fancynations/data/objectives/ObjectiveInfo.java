package me.rubix327.fancynations.data.objectives;

import lombok.Getter;
import me.rubix327.fancynations.data.tasks.TaskGroup;
import me.rubix327.fancynations.data.tasks.TaskType;

import java.util.HashMap;
import java.util.List;

@Getter
public class ObjectiveInfo {

    private final static HashMap<TaskType, ObjectiveInfo> objectiveInfo = new HashMap<>();

    private final TaskGroup group;
    private final Integer share;
    private final String localeNode;

    public ObjectiveInfo(TaskGroup group, Integer share, String localeNode) {
        this.group = group;
        this.share = share;
        this.localeNode = localeNode;
    }

    public static ObjectiveInfo get(String name){
        return objectiveInfo.get(TaskType.valueOf(name));
    }

    public static void add(TaskType name, ObjectiveInfo objType){
        objectiveInfo.put(name, objType);
    }

    /**
     * Get all the objective types names.
     * @return names list
     */
    public static List<String> getStringList(){
        return objectiveInfo.keySet().stream().map(Object::toString).toList();
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
        objectiveInfo.forEach((key, value) -> locales.put(key, value.getLocaleNode()));
        return locales;
    }
}