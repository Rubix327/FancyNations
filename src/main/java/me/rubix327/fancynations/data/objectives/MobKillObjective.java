package me.rubix327.fancynations.data.objectives;

import me.rubix327.fancynations.Settings;
import me.rubix327.fancynations.data.DataManager;
import me.rubix327.fancynations.data.fnplayers.FNPlayer;
import me.rubix327.fancynations.data.takentasks.TakenTask;
import me.rubix327.fancynations.data.taskprogresses.TaskProgress;
import me.rubix327.fancynations.data.tasks.CreatorType;
import me.rubix327.fancynations.data.tasks.TaskType;
import me.rubix327.fancynations.data.townresources.TownResource;
import org.bukkit.entity.Player;

public class MobKillObjective extends Objective {
    public MobKillObjective(TaskType type, String target, int amount, int taskId) {
        super(type, target, amount, taskId);
    }

    public MobKillObjective(int id, TaskType type, String target, int amount, int taskId) {
        super(id, type, target, amount, taskId);
    }

    public int getCurrentAmount(Player player) throws IllegalArgumentException {
        if (player == null) throw new IllegalArgumentException("This player is not online");

        int playerId = DataManager.getFNPlayerManager().get(player.getName()).getId();
        if (!TakenTask.getManager().exists(playerId, getTaskId())) return 0;
        int takenTaskId = TakenTask.getManager().get(playerId, getTaskId()).getId();
        if (!TaskProgress.getManager().exists(getId(), takenTaskId)) return 0;
        TaskProgress progress = TaskProgress.get(getId(), takenTaskId);

        return progress.getProgress();
    }

    public boolean isReadyToComplete(Player player) throws IllegalArgumentException {
        return getCurrentAmount(player) >= getAmount();
    }

    @Override
    public void complete(Player player, CreatorType creatorType) {

        // Send the resources to the creator
        if (creatorType == CreatorType.Player) {
            return;
        } else if (creatorType == CreatorType.Town) {
            TownResource resource = TownResource.getOrCreate(getTask().getTownId(), getTarget());
            resource.addResources(getAmount());
        } else if (creatorType == CreatorType.Nation) {
            TownResource resource = TownResource.getOrCreate(getTask().getTownId(), getTarget());
            resource.addResources(getAmount() * Settings.Rewards.TOWN_MOBS_SHARE / 100);
        }

        int takenTaskId = TakenTask.getManager().get(FNPlayer.get(player.getName()).getId(), getTaskId()).getId();
        int taskProgressId = TaskProgress.get(getId(), takenTaskId).getId();
        TaskProgress.remove(taskProgressId);

    }
}
