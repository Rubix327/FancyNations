package me.rubix327.fancynations;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.rubix327.fancynations.commands.FNCommandGroup;
import me.rubix327.fancynations.commands.TestCommands;
import me.rubix327.fancynations.data.DatabaseManager;
import me.rubix327.fancynations.data.Settings;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.plugin.SimplePlugin;
import org.mineacademy.fo.settings.YamlStaticConfig;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FancyNations extends SimplePlugin {

    @Getter
    private static FancyNations instance;

    public DatabaseManager database;

    @Override
    protected void onPluginStart() {
        instance = this;

        this.database = new DatabaseManager();
        try {
            database.connect();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            Common.warning("[FancyNations] Database is not connected: Maybe login info is incorrect?");
        }

        if (database.isConnected()){
            Common.log("[FancyNations] Database is connected.");
        }

        registerCommand(new TestCommands());
        registerCommands("fancynations|fn", new FNCommandGroup());
    }

    @Override
    protected void onPluginStop() {
        database.disconnect();
    }

    @Override
    public List<Class<? extends YamlStaticConfig>> getSettings() {
        return Collections.singletonList(Settings.class);
    }
}
