package me.rubix327.fancynations;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.rubix327.fancynations.commands.FNCommandGroup;
import me.rubix327.fancynations.commands.TestCommands;
import me.rubix327.fancynations.data.DatabaseManager;
import me.rubix327.fancynations.data.Settings;
import org.bukkit.Bukkit;
import org.mineacademy.fo.plugin.SimplePlugin;
import org.mineacademy.fo.settings.YamlStaticConfig;

import java.util.Collections;
import java.util.List;

@NoArgsConstructor
public final class FancyNations extends SimplePlugin {

    @Getter
    private static FancyNations instance;

    public DatabaseManager database;

    @Override
    protected void onPluginStart() {
        instance = this;

        if (Settings.General.DATA_MANAGEMENT_TYPE.equalsIgnoreCase("database")) {
            this.database = new DatabaseManager();
            database.connect();
            if (database.isConnected()) {
                Bukkit.getLogger().info("[FancyNations] Database is connected");
            }
        }
        else {
            Bukkit.getLogger().warning("[FancyNations] Using file system instead of database.");
        }
        registerCommand(new TestCommands());
        registerCommands("fancynations|fn", new FNCommandGroup());
    }

    @Override
    protected void onPluginStop() {
        if (database.isConnected()){
            database.disconnect();
        }
    }

    @Override
    public List<Class<? extends YamlStaticConfig>> getSettings() {
        return Collections.singletonList(Settings.class);
    }
}
