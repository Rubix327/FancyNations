package me.rubix327.fancynations.data.townworkers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.rubix327.fancynations.data.AbstractDto;
import me.rubix327.fancynations.data.DataManager;
import me.rubix327.fancynations.data.fnplayers.FNPlayer;
import me.rubix327.fancynations.data.professions.Profession;

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
        this.id = manager.getMaxId() + 1;
        this.townId = townId;
        this.playerId = playerId;
        this.professionId = professionId;
        this.salary = Profession.getSalary(professionId);
    }

    public static void add(int townId, int playerId, int profession){
        manager.add(new TownWorker(townId, playerId, profession));
    }

    public static void remove(int playerId){
        String playerName = DataManager.getFNPlayerManager().get(playerId).getName();
        manager.remove(manager.getByPlayer(playerName).getId());
    }

    public static void setProfession(int playerId, int newProfessionId){
        manager.update(manager.getByPlayer(FNPlayer.getName(playerId)).getId(), "professionId", newProfessionId);
    }

    public static Profession getProfession(int playerId){
        return manager.getProfession(playerId);
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

}
