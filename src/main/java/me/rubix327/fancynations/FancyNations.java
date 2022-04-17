package me.rubix327.fancynations;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.rubix327.fancynations.commands.MainCommandGroup;
import me.rubix327.fancynations.commands.TestCommands;
import me.rubix327.fancynations.data.DataManager;
import me.rubix327.fancynations.data.DatabaseManager;
import me.rubix327.fancynations.data.fnplayers.FNPlayer;
import me.rubix327.fancynations.data.tasktypes.TaskType;
import me.rubix327.fancynations.data.workertypes.WorkerType;
import me.rubix327.fancynations.events.PlayerListener;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.plugin.SimplePlugin;
import org.mineacademy.fo.settings.YamlStaticConfig;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@NoArgsConstructor
public final class FancyNations extends SimplePlugin {

    @Getter
    private static FancyNations instance;
    @Getter
    private Economy economy = null;
    @Getter
    private DatabaseManager database;

    @Override
    protected void onPluginStart() {
        instance = this;

        // Localization
        Localization localization = new Localization();
        localization.init(Arrays.asList("en", "ru"));

        // Database
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

        // Commands and events
        registerCommand(new TestCommands());
        registerCommands("fancynations|fn", new MainCommandGroup());
        registerEvents(new PlayerListener());

        // Economy
        if (!setupEconomy() ) {
            Common.log(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // Filling with default entries for the first time
        addDefaultEntries();
    }

    @Override
    protected void onPluginStop() {
        instance = null;

        if (database.isConnected()){
            database.disconnect();
        }
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return true;
    }

    private void addDefaultEntries(){
        DataManager.getFNPlayerManager().addIgnore(new FNPlayer(1, "%server%"));
        DataManager.getTaskTypeManager().addIgnore(new TaskType(1, "Gathering", "Food"));
        DataManager.getTaskTypeManager().addIgnore(new TaskType(2, "Gathering", "Resource"));
        DataManager.getTaskTypeManager().addIgnore(new TaskType(3, "Gathering", "Crafting"));
        DataManager.getTaskTypeManager().addIgnore(new TaskType(4, "Mobs", "Mobkill"));
        DataManager.getWorkerTypeManager().addIgnore(new WorkerType(1, "Mayor"));
        DataManager.getWorkerTypeManager().addIgnore(new WorkerType(2, "Helper"));
        DataManager.getWorkerTypeManager().addIgnore(new WorkerType(3, "Judge"));
        DataManager.getWorkerTypeManager().addIgnore(new WorkerType(4, "Other"));
    }

    @Override
    public List<Class<? extends YamlStaticConfig>> getSettings() {
        return Collections.singletonList(Settings.class);
    }
}
