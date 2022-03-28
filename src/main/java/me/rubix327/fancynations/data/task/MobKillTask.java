package me.rubix327.fancynations.data.task;

public class MobKillTask extends Task{
    protected MobKillTask(String townName, TaskType taskType, String creatorName, String taskName) {
        super(townName, taskType, creatorName, taskName);
    }

    @Override
    public boolean isObjectiveCompleted(String reqItemId, int reqAmount, String playerName) {
        return false;
    }
}
