package me.rubix327.fancynations.data.takentasks;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.rubix327.fancynations.data.AbstractDto;
import me.rubix327.fancynations.data.DataManager;
import me.rubix327.fancynations.data.fnplayers.FNPlayer;
import me.rubix327.fancynations.data.tasks.Task;
import org.bukkit.entity.Player;
import org.mineacademy.fo.exception.CommandException;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter @Setter(AccessLevel.PACKAGE)
@AllArgsConstructor
public class TakenTask extends AbstractDto {

    @Getter
    private static final ITakenTaskManager manager = DataManager.getTakenTaskManager();

    private final int id;
    private final int playerId;
    private final int taskId;
    private final Timestamp takingDatetime;

    public TakenTask(int playerId, int taskId) {
        this.id = DataManager.getTakenTaskManager().getMaxId() + 1;
        this.playerId = playerId;
        this.taskId = taskId;
        this.takingDatetime = Timestamp.valueOf(LocalDateTime.now());
    }

    public static void add(Player player, int taskId){
        manager.add(new TakenTask(FNPlayer.get(player.getName()).getId(), taskId));
    }

    public static void remove(Player player, int taskId){
        TakenTask.getManager().remove(TakenTask.getManager().get(FNPlayer.get(player.getName()).getId(), taskId).getId());
    }

    public static TakenTask find(Player player, int taskId){
        if (!TakenTask.getManager().exists(FNPlayer.get(player.getName()).getId(), taskId)) throw new CommandException();
        return DataManager.getTakenTaskManager().get(FNPlayer.get(player.getName()).getId(), taskId);
    }

    public FNPlayer getFNPlayer(){
        return DataManager.getFNPlayerManager().get(playerId);
    }

    public Task getTask(){
        return DataManager.getTaskManager().get(taskId);
    }

}
