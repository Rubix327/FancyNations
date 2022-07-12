package me.rubix327.fancynations.data.townworkers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.rubix327.fancynations.Localization;
import me.rubix327.fancynations.data.AbstractDto;
import me.rubix327.fancynations.data.DataManager;
import me.rubix327.fancynations.data.fnplayers.FNPlayer;
import me.rubix327.fancynations.data.professions.Profession;
import me.rubix327.fancynations.data.towns.Town;
import org.bukkit.command.CommandSender;

@Getter @Setter(AccessLevel.PACKAGE)
@AllArgsConstructor
public class TownWorker extends AbstractDto {

    private static ITownWorkerManager manager = DataManager.getTownWorkerManager();

    private final int id;
    private final int playerId;
    private final int townId;
    private final int professionId;
    private String displayName;
    private double salary;

    public TownWorker(int townId, int playerId, int professionId) {
        this.id = manager.getNextId();
        this.townId = townId;
        this.playerId = playerId;
        this.professionId = professionId;
        this.salary = Profession.getSalary(professionId);
    }

    public static void add(int townId, int playerId, int profession){
        manager.add(new TownWorker(townId, playerId, profession));
    }

    public static void remove(int playerId){
        manager.remove(manager.getByPlayer(playerId).getId());
    }

    public static void setProfession(int playerId, int newProfessionId){
        int townWorkerId = manager.getByPlayer(playerId).getId();
        manager.update(townWorkerId, "professionId", newProfessionId);
    }

    /**
     * Shows is a player a mayor in any town.
     */
    public static boolean isWorker(int playerId){
        return manager.isWorker(playerId);
    }

    public static boolean isMayor(int playerId){
        return manager.isMayor(playerId);
    }

    /**
     * Shows is a player a mayor in the specified town or not.
     */
    public static boolean isMayor(int playerId, int townId){
        return manager.isMayor(playerId, townId);
    }

    /**
     * Shows is a player a worker in the specified town or not.
     */
    public static boolean isWorker(int playerId, int townId){
        return manager.isWorker(playerId, townId);
    }

    public static TownWorker findWorker(int playerId, int townId, CommandSender sender, String messageKey){
        if (!isWorker(playerId, townId)) Localization.getInstance().locReturnTell(messageKey, sender);
        return manager.getByPlayer(playerId);
    }

    public Town getTown(){
        return DataManager.getTownManager().get(townId);
    }

    public FNPlayer getFNPlayer(){
        return DataManager.getFNPlayerManager().get(playerId);
    }

    public Profession getProfession(){
        return DataManager.getProfessionManager().get(professionId);
    }

}
