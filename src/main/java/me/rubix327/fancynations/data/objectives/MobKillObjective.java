package me.rubix327.fancynations.data.objectives;

import me.rubix327.fancynations.Settings;
import me.rubix327.fancynations.data.DataManager;
import me.rubix327.fancynations.data.fnplayers.FNPlayer;
import me.rubix327.fancynations.data.takentasks.TakenTask;
import me.rubix327.fancynations.data.taskprogresses.TaskProgress;
import me.rubix327.fancynations.data.tasks.CreatorType;
import me.rubix327.fancynations.data.townresources.TownResource;
import org.bukkit.entity.Player;

public class MobKillObjective extends Objective{
    public MobKillObjective(String type, String target, int amount, int taskId) {
        super(type, target, amount, taskId);
    }

    public MobKillObjective(int id, String type, String target, int amount, int taskId) {
        super(id, type, target, amount, taskId);
    }

    public boolean isReadyToComplete(Player player) throws IllegalArgumentException{
        if (player == null) throw new IllegalArgumentException("This player is not online");

        int playerId = DataManager.getFNPlayerManager().get(player.getName()).getId();
        if (!TakenTask.getManager().exists(playerId, getTaskId())) return false;
        int takenTaskId = TakenTask.getManager().get(playerId, getTaskId()).getId();
        if (!TaskProgress.getManager().exists(getId(), takenTaskId)) return false;
        TaskProgress progress = TaskProgress.get(getId(), takenTaskId);

        return progress.getProgress() >= getAmount();
    }

    @Override
    public void complete(Player player, CreatorType creatorType) {

        if (creatorType == CreatorType.Player){
            return;
        }
        else if (creatorType == CreatorType.Town){
            DataManager.getTownResourceManager().add(new TownResource(getTask().getTownId(), getTarget(), getAmount()));
        }
        else if (creatorType == CreatorType.Nation){
            DataManager.getTownResourceManager().add(new TownResource(
                    getTask().getTownId(), getTarget(), getAmount() * Settings.Rewards.TOWN_MOBS_SHARE));
        }

        int takenTaskId = TakenTask.getManager().get(FNPlayer.get(player.getName()).getId(), getTaskId()).getId();
        int taskProgressId = TaskProgress.get(getId(), takenTaskId).getId();
        TaskProgress.remove(taskProgressId);

    }
}
