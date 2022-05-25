package me.rubix327.fancynations.commands;

import me.rubix327.fancynations.data.towns.Town;
import me.rubix327.fancynations.menu.MainPanelMenu;
import me.rubix327.fancynations.menu.TownBoardMenu;
import org.mineacademy.fo.command.SimpleCommandGroup;

public class GuiCommand extends SubCommandInterlayer{
    protected GuiCommand(SimpleCommandGroup group, String subLabel, String permLabel) {
        super(group, subLabel, permLabel);
    }

    @Override
    protected void onCommand() {
        checkConsole();

        if (args.length == 0) {
            new MainPanelMenu(getPlayer()).display();
            return;
        }

        // /fn gui tasksBoard <town_name>
        if (isArg(0, "tasksBoard")){
            checkArgs(2, "Please enter the town name.");
            int townId = Town.find(args[1], sender, "error_town_not_exist").getId();
            new TownBoardMenu(getPlayer(), townId).display();
        }
    }
}