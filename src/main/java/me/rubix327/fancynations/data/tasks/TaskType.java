package me.rubix327.fancynations.data.tasks;

import me.rubix327.fancynations.Localization;
import me.rubix327.fancynations.data.objectives.ObjectiveInfo;
import org.bukkit.command.CommandSender;

import java.util.HashMap;

public enum TaskType {
    No,
    Food,
    Resource,
    Crafting,
    MobKill,
    Combined;

    private static final HashMap<TaskType, String> locales = new HashMap<>();

    public static void init(){
        locales.put(No, "task_type_no_objectives");
        locales.put(Combined, "task_type_combined");
        locales.putAll(ObjectiveInfo.getLocales());
    }

    /**
     * Get the name of the type translated to the language of this sender.
     * @param type to get name for
     * @param sender the sender of the command
     * @return translated name
     */
    public static String getLocalizedName(String type, CommandSender sender){
        return Localization.getInstance().get(locales.get(TaskType.valueOf(type)), sender);
    }

}