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
import me.rubix327.fancynations.util.DependencyManager;
import me.rubix327.fancynations.util.Logger;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.plugin.SimplePlugin;
import org.mineacademy.fo.settings.YamlStaticConfig;

import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
public final class FancyNations extends SimplePlugin {

    @Getter
    private static FancyNations instance;
    @Getter
    private Economy economy = null;
    @Getter
    private DatabaseManager database;
    private DependencyManager dependencies;

    @Override
    protected void onPluginPreStart() {
        instance = this;
    }

    @Override
    protected void onPluginStart() {
        Common.setLogPrefix("");

        // Localization
        Localization localization = Localization.getInstance();
        localization.init(Arrays.asList("en", "ru"));

        // Database
        if (Settings.General.DATA_MANAGEMENT_TYPE.equalsIgnoreCase("database")){
            this.database = DatabaseManager.getInstance();
            database.connect("dbsetup.sql");
            if (database.isConnected()) {
                Logger.info("Database is connected.");
            }
            else{
                Logger.warning("Database is not connected.");
                Logger.warning("Using file system instead of database.");
            }
        }
        else {
            Logger.info("Using file system as indicated in settings.yml.");
        }

        // DataManager
        DataManager dataManager = DataManager.getInstance();
        dataManager.runTaskExpireListener();

        // Dependencies
        dependencies = DependencyManager.getInstance();

        // Commands and events
        registerCommand(new TestCommands());
        registerCommands("fancynations|fn", new MainCommandGroup());
        registerEvents(new PlayerListener());

        // Economy
        if (!setupEconomy() ) {
            Logger.warning("Disabled due to no Vault dependency found!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // Hooking into Mythic.io plugins
        if (dependencies.IS_MYTHICLIB_LOADED) Logger.info("Hooked into MythicLib.");
        if (dependencies.IS_MMOITEMS_LOADED) Logger.info("Hooked into MMOItems.");
        if (dependencies.IS_MMOCORE_LOADED) Logger.info("Hooked into MMOCore.");
        if (dependencies.IS_MYTHICMOBS_LOADED) Logger.info("Hooked into MythicMobs.");

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
        if (!dependencies.IS_VAULT_LOADED) return false;
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) return false;
        economy = rsp.getProvider();
        return true;
    }

    private void addDefaultEntries(){
        DataManager.getFNPlayerManager().addIgnore(new FNPlayer(1, Settings.General.SERVER_VAR));
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
        return List.of(Settings.class);
    }
}
