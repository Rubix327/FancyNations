package me.rubix327.fancynations.commands;

import me.rubix327.fancynations.Localization;
import me.rubix327.fancynations.data.DataManager;
import me.rubix327.fancynations.data.townworkers.TownWorker;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MayorCommands extends SubCommandInterlayer {
    protected MayorCommands(String sublabel, String permLabel) {
        super(sublabel, permLabel);
    }

    @Override
    protected void onCommand() {

        Localization msgs = Localization.getInstance();
        setPermission("fancynations.mayor");
        addTellPrefix(false);

        // fn mayor assign <townName> <playerName>
        if (args[0].equals("assign")) {
            if (args.length < 3) {
                tell(msgs.get("syntax_mayor_assign", sender));
                return;
            }

            String townName = args[1];
            String playerName = args[2];

            if (!DataManager.getTownManager().exists(townName)){
                tell(msgs.get("error_town_not_exist", sender));
                return;
            }
            int townId = DataManager.getTownManager().get(townName).getId();

            if (!DataManager.getFNPlayerManager().exists(playerName)){
                tell(msgs.get("error_player_not_exist", sender));
                return;
            }
            int playerId = DataManager.getFNPlayerManager().get(args[2]).getId();

            List<String> players = new ArrayList<>();
            Arrays.asList(Bukkit.getOfflinePlayers()).forEach(player -> players.add(player.getName()));

            if (!players.contains(playerName)) {
                tell(msgs.get("error_player_not_exist", sender));
                return;
            }

            int workerTypeId = DataManager.getWorkerTypeManager().get("Mayor").getId();

            TownWorker mayor = new TownWorker(playerId, workerTypeId, townId);
            DataManager.getTownWorkerManager().add(mayor);
        }
    }
}
