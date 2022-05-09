package me.rubix327.fancynations.data.tasks;

import me.rubix327.fancynations.Localization;
import me.rubix327.fancynations.data.objectives.ObjectiveType;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public enum TaskType {
    No,
    Food,
    Resource,
    Crafting,
    Mobkill,
    Combined;

    private static final HashMap<TaskType, String> locales = new HashMap<>() {{
        put(No, "task_type_no_objectives");
        put(Combined, "task_type_combined");
        putAll(toTaskTypeHashMap());
    }};

    /**
     * Get the name of the type translated to the language of this sender.
     * @param type to get name for
     * @param sender the sender of the command
     * @return translated name
     */
    public static String getLocalizedName(String type, CommandSender sender){
        return Localization.getInstance().get(locales.get(TaskType.valueOf(type)), sender);
    }

    /**
     * Convert all objective types to task types.
     * @return TaskTypes hashmap
     */
    public static Map<TaskType, String> toTaskTypeHashMap(){
        return ObjectiveType.getLocales().entrySet().stream().collect(
                Collectors.toMap(e -> TaskType.valueOf(e.getKey().toString()), Map.Entry::getValue));
    }

}