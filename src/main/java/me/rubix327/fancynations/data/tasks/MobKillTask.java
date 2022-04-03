package me.rubix327.fancynations.data.tasks;

import org.bukkit.entity.Player;

public class MobKillTask extends Task{
    protected MobKillTask(int townId, int taskTypeId, String creatorName, String taskName) {
        super(townId, taskTypeId, creatorName, taskName);
    }

    @Override
    public boolean isObjectiveCompleted(String reqItemId, int reqAmount, Player player) {
        return false;
    }
}
