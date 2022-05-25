package me.rubix327.fancynations.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DependencyManager {

    private static DependencyManager instance;

    public final boolean IS_VAULT_LOADED = isPluginLoaded("Vault");
    public boolean IS_MYTHICLIB_LOADED = isPluginLoaded("MythicLib");
    public boolean IS_MMOITEMS_LOADED = isPluginLoaded("MMOItems");
    public boolean IS_MMOCORE_LOADED = isPluginLoaded("MMOCore");
    public boolean IS_MYTHICMOBS_LOADED = isPluginLoaded("MythicMobs");

    public static DependencyManager getInstance(){
        if (instance == null){
            instance = new DependencyManager();
        }
        return instance;
    }

    private boolean isPluginLoaded(String plugin){
        return Bukkit.getPluginManager().getPlugin(plugin) != null;
    }

}
