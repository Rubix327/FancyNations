package me.rubix327.fancynations;

import me.rubix327.fancynations.commands.FNCommandGroup;
import me.rubix327.fancynations.commands.TestCommands;
import me.rubix327.fancynations.data.DatabaseManager;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.plugin.SimplePlugin;

import java.sql.SQLException;

public final class FancyNations extends SimplePlugin {

    public DatabaseManager database;

    @Override
    protected void onPluginStart() {

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
}
