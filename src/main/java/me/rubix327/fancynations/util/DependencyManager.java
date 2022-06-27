package me.rubix327.fancynations.util;

import org.bukkit.Bukkit;

public enum DependencyManager {

    VAULT("Vault"),
    MYTHIC_LIB("MythicLib"),
    MMO_ITEMS("MMOItems"),
    MMO_CORE("MMOCore"),
    MYTHIC_MOBS("MythicMobs");

    private final String name;

    DependencyManager(String name) {
        this.name = name;
    }

    public final boolean isLoaded() {
        return Bukkit.getPluginManager().getPlugin(this.name) != null;
    }

}
