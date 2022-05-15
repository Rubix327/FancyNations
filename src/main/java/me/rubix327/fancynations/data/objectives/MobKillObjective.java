package me.rubix327.fancynations.data.objectives;

import me.rubix327.fancynations.data.DataManager;
import me.rubix327.fancynations.data.takentasks.TakenTask;
import me.rubix327.fancynations.data.taskprogresses.TaskProgress;
import org.bukkit.entity.Player;

public class MobKillObjective extends Objective{
    public MobKillObjective(String type, String target, int amount) {
        super(type, target, amount);
    }

    public MobKillObjective(int id, String type, String target, int amount, int task) {
        super(id, type, target, amount, task);
    }

    public boolean isCompleted(Player player) throws IllegalArgumentException{
        if (player == null) throw new IllegalArgumentException("This player is not online");

        int playerId = DataManager.getFNPlayerManager().get(player.getName()).getId();
        TakenTask takenTask = DataManager.getTakenTaskManager().get(playerId, getTaskId());
        TaskProgress progress = DataManager.getTaskProgressManager().get(getId(), takenTask.getId());

        return progress.getProgress() >= getAmount();
    }
}
