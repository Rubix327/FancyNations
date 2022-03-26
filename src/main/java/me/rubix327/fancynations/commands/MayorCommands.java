package me.rubix327.fancynations.commands;

import me.rubix327.fancynations.data.DataManager;
import me.rubix327.fancynations.data.Mayor;
import org.bukkit.Bukkit;
import org.mineacademy.fo.command.SimpleCommandGroup;
import org.mineacademy.fo.command.SimpleSubCommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MayorCommands extends SimpleSubCommand {
    protected MayorCommands(SimpleCommandGroup parent, String sublabel) {
        super(parent, sublabel);
    }

    @Override
    protected void onCommand() {
        // fn mayor assign <townName> <playerName>
        if (args[0].equals("assign")) {
            if (args.length < 3) {
                tell("&cSyntax: /fn mayor assign <town_name> <player_name>");
                return;
            }

            if (!DataManager.isTownExists(args[1])) {
                tell("&cThis town does not exist.");
                return;
            }

            List<String> players = new ArrayList<>();
            Arrays.asList(Bukkit.getOfflinePlayers()).forEach(player -> players.add(player.getName()));

            if (!players.contains(args[2])) {
                tell("&cThere is no such player.");
                return;
            }

            Mayor mayor = new Mayor(args[2]);
            DataManager.addMayor(mayor.getId(), mayor);
        }
    }
}
