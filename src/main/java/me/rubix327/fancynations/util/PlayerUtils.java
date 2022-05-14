package me.rubix327.fancynations.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;

public class PlayerUtils {

    public static boolean isOnline(String playerName){
        return Bukkit.getOnlinePlayers().stream().map(HumanEntity::getName).toList().contains(playerName);
    }

}