package me.rubix327.fancynations.commands;

import me.rubix327.fancynations.data.DataManager;
import me.rubix327.fancynations.data.towns.Town;

public class TownCommands extends SubCommandInterlayer {
    protected TownCommands(String sublabel, String permLabel) {
        super(sublabel, permLabel);
    }

    /*
           label  a0       a1          a2         a3
        fn town create <town_name>
        fn town set    <town_id>   <variable> <value>
        fn town remove <town_id>
        fn town info   <town_id>
        fn town list
     */
    @Override
    protected void onCommand() {

        if (args[0].equalsIgnoreCase("create")){
            if (args.length < 2){
                tell("Syntax: /fn town create <name>");
                return;
            }
            String name = args[1];

            Town town = new Town(1, name);
            DataManager.getTownManager().add(town);
        }
        else if (args[0].equalsIgnoreCase("info")){
            if (args.length < 2){
                tell("Syntax: /fn info <town_name>");
                return;
            }
            Town town = DataManager.getTownManager().get(args[1]);
            tell(town.getName() + " " + town.getBalance());
        }
        else if (args[0].equalsIgnoreCase("list")){
            tell("Existing towns: " + String.join(", ", DataManager.getTownManager().getNames()));
        }

    }
}
