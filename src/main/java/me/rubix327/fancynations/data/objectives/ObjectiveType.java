package me.rubix327.fancynations.data.objectives;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public enum ObjectiveType {
    Food,
    Resource,
    Crafting,
    Mobkill;

    private static final HashMap<ObjectiveType, String> locales = new HashMap<>() {{
        put(Food, "task_type_food");
        put(Resource, "task_type_resource");
        put(Crafting, "task_type_crafting");
        put(Mobkill, "task_type_food");
    }};

    private static final HashMap<ObjectiveType, String> groups = new HashMap<>() {{
        put(Food, "Gathering");
        put(Resource, "Gathering");
        put(Crafting, "Gathering");
        put(Mobkill, "Mobs");
    }};

    /**
     * Get the group of the type.
     * @param type to get a group for
     * @return the group
     */
    public static String getGroup(String type){
        return groups.get(ObjectiveType.valueOf(type));
    }

    /**
     * Get all the objective types names.
     * @return names list
     */
    public static List<String> getStringList(){
        return Arrays.stream(values()).map(Object::toString).toList();
    }

    /**
     * Get all the objective types names in string separated by comma.
     * @return names string separated by comma
     */
    public static String getFormattedString(){
        return String.join(", ", getStringList());
    }

    /**
     * Get locales hashmap of ObjectiveType enum.
     * @return locales hashmap
     */
    public static HashMap<ObjectiveType, String> getLocales(){
        return ObjectiveType.locales;
    }

}
