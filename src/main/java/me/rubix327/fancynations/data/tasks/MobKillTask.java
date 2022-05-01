package me.rubix327.fancynations.data.tasks;

import me.rubix327.fancynations.data.DataManager;
import me.rubix327.fancynations.data.objectives.Objective;
import me.rubix327.fancynations.data.taskprogresses.TaskProgress;
import org.bukkit.entity.Player;

import java.sql.Timestamp;

public class MobKillTask extends Task{
    public MobKillTask(int townId, int taskTypeId, int cretorId, String taskName) {
        super(townId, taskTypeId, cretorId, taskName);
    }

    public MobKillTask(int id, int townId, int taskTypeId, int creatorId, String taskName, String description,
                         int takeAmount, int minLevel, int maxLevel, double moneyReward, double expReward,
                         int repReward, int priority, Timestamp placementDateTime, int timeToComplete) {
        super(id, townId, taskTypeId, creatorId, taskName, description,
                takeAmount, minLevel, maxLevel, moneyReward, expReward,
                repReward, priority, placementDateTime, timeToComplete);
    }

    @Override
    public boolean isObjectiveCompleted(Objective objective, Player player) throws IllegalArgumentException {
        if (player == null) throw new IllegalArgumentException("This player is not online");
        TaskProgress progress = DataManager.getTaskProgressManager().get(objective.getId());
        return progress.getProgress() >= objective.getAmount();
    }
}
