package me.rubix327.fancynations;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.rubix327.fancynations.commands.FNCommandGroup;
import me.rubix327.fancynations.commands.TestCommands;
import me.rubix327.fancynations.data.DataManager;
import me.rubix327.fancynations.data.DatabaseManager;
import me.rubix327.fancynations.data.fnplayers.FNPlayer;
import me.rubix327.fancynations.data.tasktypes.TaskType;
import me.rubix327.fancynations.data.workertypes.WorkerType;
import me.rubix327.fancynations.events.PlayerListener;
import org.bukkit.Bukkit;
import org.mineacademy.fo.plugin.SimplePlugin;
import org.mineacademy.fo.settings.YamlStaticConfig;

import java.util.Arrays;
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

        Localization localization = new Localization();
        localization.init(Arrays.asList("en", "ru"));

        if (Settings.General.DATA_MANAGEMENT_TYPE.equalsIgnoreCase("database")){
            this.database = new DatabaseManager();
            database.connect();
            if (database.isConnected()) {
                Bukkit.getLogger().info("[FancyNations] Database is connected");
            }
            else{
                Bukkit.getLogger().warning("[FancyNations] Using file system instead of database.");
            }
        }
        else {
            Bukkit.getLogger().info("[FancyNations] Using file system instead of database.");
        }

        registerCommand(new TestCommands());
        registerCommands("fancynations|fn", new FNCommandGroup());
        registerEvents(new PlayerListener());

        addDefaultEntries();
    }

    @Override
    protected void onPluginStop() {
        instance = null;

        if (database.isConnected()){
            database.disconnect();
        }
    }

    private void addDefaultEntries(){
        DataManager.getFNPlayerManager().add(new FNPlayer(1, "%server%"));
        DataManager.getTaskTypeManager().add(new TaskType(1, "Gathering", "Food"));
        DataManager.getTaskTypeManager().add(new TaskType(2, "Gathering", "Resource"));
        DataManager.getTaskTypeManager().add(new TaskType(3, "Gathering", "Crafting"));
        DataManager.getTaskTypeManager().add(new TaskType(4, "Mobs", "Mobkill"));
        DataManager.getWorkerTypeManager().add(new WorkerType(1, "Mayor"));
        DataManager.getWorkerTypeManager().add(new WorkerType(2, "Helper"));
        DataManager.getWorkerTypeManager().add(new WorkerType(3, "Judge"));
        DataManager.getWorkerTypeManager().add(new WorkerType(4, "Other"));
    }

    @Override
    public List<Class<? extends YamlStaticConfig>> getSettings() {
        return Collections.singletonList(Settings.class);
    }
}
