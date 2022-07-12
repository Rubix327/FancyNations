package me.rubix327.fancynations;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.rubix327.fancynations.commands.MainCommandGroup;
import me.rubix327.fancynations.commands.TestCommands;
import me.rubix327.fancynations.data.DataManager;
import me.rubix327.fancynations.data.DatabaseManager;
import me.rubix327.fancynations.data.fnplayers.FNPlayer;
import me.rubix327.fancynations.data.professions.Profession;
import me.rubix327.fancynations.events.PlayerListener;
import me.rubix327.fancynations.util.DependencyManager;
import me.rubix327.fancynations.util.Logger;
import me.rubix327.itemslangapi.ItemsLangAPI;
import me.rubix327.itemslangapi.Lang;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.plugin.SimplePlugin;

import java.util.Arrays;

@NoArgsConstructor
public final class FancyNations extends SimplePlugin {

    @Getter
    private static FancyNations instance;
    @Getter
    private Economy economy = null;
    @Getter
    private DatabaseManager database;

    public void init() {
        // Localization
        Localization localization = Localization.getInstance();
        localization.init(Arrays.asList(Lang.EN_US.toString(), Lang.RU_RU.toString()));
        Localization.resetPrefixes();
        ItemsLangAPI.getApi().load(Lang.EN_US, Lang.RU_RU);

        // Database
        if (Settings.General.DATA_MANAGEMENT_TYPE.equalsIgnoreCase("database")) {
            this.database = DatabaseManager.getInstance();
            database.connect("db_setup.sql");
            if (database.isConnected()) {
                Logger.info("Database is connected.");
                database.loadQueries("db_queries.sql");
            } else {
                Logger.warning("Database is not connected.");
                Logger.warning("Using file system instead of database.");
            }
        } else {
            Logger.info("Using file system as indicated in settings.yml.");
        }

        // DataManager
        DataManager dataManager = DataManager.getInstance();
        // TODO: new task expire listener
//        dataManager.runTaskExpireListener(1);

        // Commands and events
        registerCommand(new TestCommands());
        registerCommands(new MainCommandGroup());
        registerEvents(new PlayerListener());

        // Economy
        if (!setupEconomy()) {
            Logger.warning("Disabled due to no Vault dependency found!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // Hooking into Mythic.io plugins
        if (DependencyManager.MYTHIC_LIB.isLoaded()) Logger.info("Hooked into MythicLib.");
        if (DependencyManager.MMO_ITEMS.isLoaded()) Logger.info("Hooked into MMOItems.");
        if (DependencyManager.MMO_CORE.isLoaded()) Logger.info("Hooked into MMOCore.");
        if (DependencyManager.MYTHIC_MOBS.isLoaded()) Logger.info("Hooked into MythicMobs.");

        // Filling with default entries for the first time
        addDefaultEntries();
    }

    @Override
    protected void onPluginLoad() {
        instance = this;
        Common.setLogPrefix(null);
    }

    @Override
    protected void onPluginStart() {
        init();
    }

    @Override
    protected void onPluginStop() {
        instance = null;

        if (database.isConnected()){
            database.disconnect();
        }
    }

    private boolean setupEconomy() {
        if (!DependencyManager.VAULT.isLoaded()) return false;
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) return false;
        economy = rsp.getProvider();
        return true;
    }

    private void addDefaultEntries() {
        FNPlayer.init();
        Profession.init();
    }
    
}
