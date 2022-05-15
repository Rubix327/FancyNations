package me.rubix327.fancynations.data.takentasks;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.rubix327.fancynations.data.AbstractDto;
import me.rubix327.fancynations.data.DataManager;
import me.rubix327.fancynations.data.fnplayers.FNPlayer;
import me.rubix327.fancynations.data.tasks.Task;

@Getter @Setter(AccessLevel.PACKAGE)
@AllArgsConstructor
public class TakenTask extends AbstractDto {

    private final int id;
    private final int playerId;
    private final int taskId;

    public TakenTask(int playerId, int taskId) {
        this.id = DataManager.getTakenTaskManager().getMaxId() + 1;
        this.playerId = playerId;
        this.taskId = taskId;
    }

    public FNPlayer getFNPlayer(){
        return DataManager.getFNPlayerManager().get(playerId);
    }

    public Task getTask(){
        return DataManager.getTaskManager().get(taskId);
    }

}
