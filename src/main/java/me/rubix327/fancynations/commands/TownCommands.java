package me.rubix327.fancynations.commands;

import me.rubix327.fancynations.data.DataManager;
import me.rubix327.fancynations.data.fnplayers.FNPlayer;
import me.rubix327.fancynations.data.professions.Profession;
import me.rubix327.fancynations.data.towns.Town;
import me.rubix327.fancynations.data.townworkers.TownWorker;
import me.rubix327.fancynations.util.Utils;
import org.mineacademy.fo.command.SimpleCommandGroup;

public class TownCommands extends SubCommandInterlayer {
    protected TownCommands(SimpleCommandGroup group, String sublabel, String permLabel) {
        super(group, sublabel, permLabel);
    }

    @Override
    protected void onCommand() {

        if (args.length == 0){
            checkPermission("help");
            locReturnTell("syntax_town");
        }

        // /fn town create <name>
        if (isArg(0, "create")){
            checkPermission("create");
            checkArgs(2, "syntax_town_create");
            String name = args[1];

            if (DataManager.getTownManager().exists(name)){
                locReturnTell("error_town_already_exist");
            }

            if (Utils.hasSpecialChars(name)){
                locReturnTell("error_has_special_chars");
            }

            if (name.length() > 16){
                locReturnTell("error_town_name_too_long", replace("@max_length", 16));
            }

            Town.add(1, args[1]);
            locReturnTell("success_town_created", replace("@name", name));
        }

        // /fn town remove <name>
        if (isArg(0, "remove")){
            checkPermission("remove");
            checkArgs(2, "syntax_town_remove");
            int townId = findNumber(1, getMsg("error_id_must_be_number"));
            String name = DataManager.getTownManager().get(townId).getName();
            if (!Town.getManager().exists(townId)) locReturnTell("error_town_not_exist");
            Town.getManager().remove(townId);
            locReturnTell("success_town_removed", replace("@name", name));
        }

        // /fn town worker <town_name>
        if (isArg(0, "worker")){
            checkPermission("worker");
            checkArgs(5, "syntax_town_worker");

            Town town = Town.find(args[1], sender, "error_town_not_exist");

            // /fn town worker <town_name> hire <player> <profession>
            if (isArg(2, "assign|hire")){
                checkPermission("worker.hire");
                checkArgs(5, "syntax_town_worker_hire");

                int playerId = FNPlayer.find(args[3], sender, "error_player_not_exist").getId();
                Profession profession = Profession.find(args[4], sender, "error_profession_not_exist");
                if (TownWorker.isWorker(playerId, town.getId())) locReturnTell("error_player_already_works");

                TownWorker.add(town.getId(), playerId, profession.getId());
                tellOnlinePlayer(playerId, "info_you_hired",
                        replace("@profession", profession.getName()),
                        replace("@town", town.getName()));
                locReturnTell("success_town_worker_hired", replace("@player", args[3]),
                        replace("@profession", profession.getLocalizedName()), replace("@town", town.getName()));
            }

            // /fn town worker <town_name> fire <player>
            else if (isArg(2, "dismiss|fire")){
                checkPermission("worker.fire");
                checkArgs(4, "syntax_town_worker_fire");

                FNPlayer player = FNPlayer.find(args[3], sender, "error_player_not_exist");
                if (!TownWorker.isWorker(player.getId(), town.getId())) locReturnTell("error_player_not_worker");
                TownWorker worker = DataManager.getTownWorkerManager().getByPlayer(player.getName());

                TownWorker.remove(player.getId());
                tellOnlinePlayer(player.getId(), "info_you_fired",
                        replace("@profession", worker.getProfession().getName()),
                        replace("@town", town.getName()));
                locReturnTell("success_town_worker_fired");
            }

            // /fn town worker <town_name> profession <player> <profession>
            else if (isArg(2, "profession|position|pos")){
                checkArgs(5, "syntax_town_worker_hire");
                checkPermissionOrMayor("worker.profession", town.getId());

                int playerId = FNPlayer.find(args[3], sender, "error_player_not_exist").getId();
                int professionId = Profession.find(args[4], sender, "error_profession_not_exist").getId();

                TownWorker.setProfession(playerId, professionId);
                tellOnlinePlayer(playerId, "info_your_profession_changed",
                        replace("@profession", Profession.get(professionId).getName()),
                        replace("@town", town.getName()));
                locReturnTell("success_town_worker_profession_changed");
            }

        }

        else if (args[0].equalsIgnoreCase("info")){
            if (args.length < 2){
                tell("Syntax: /fn info <town_name>");
                return;
            }
            Town town = Town.getManager().get(args[1]);
            tell(town.getName() + " " + town.getBalance());
        }
        else if (args[0].equalsIgnoreCase("list")){
            tell("Existing towns: " + String.join(", ", DataManager.getTownManager().getNames()));
        }

    }
}
