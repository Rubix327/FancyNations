package me.rubix327.fancynations.data.taskprogresses;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.rubix327.fancynations.Settings;
import me.rubix327.fancynations.data.AbstractDto;
import me.rubix327.fancynations.data.DataManager;
import me.rubix327.fancynations.data.objectives.Objective;
import me.rubix327.fancynations.data.takentasks.TakenTask;
import me.rubix327.fancynations.data.tasks.TaskType;
import org.bukkit.entity.Player;

@Getter @Setter(AccessLevel.PACKAGE)
@AllArgsConstructor
public class TaskProgress extends AbstractDto {

    @Getter
    private static ITaskProgressManager manager = DataManager.getTaskProgressManager();

    private final int id;
    private final int objectiveId;
    private final int takenTaskId;
    private int progress;

    public TaskProgress(int objectiveId, int takenTaskId) {
        this.id = DataManager.getTaskProgressManager().getNextId();
        this.objectiveId = objectiveId;
        this.takenTaskId = takenTaskId;
    }

    public static TaskProgress get(int objectiveId, int takenTaskId){
        return manager.get(objectiveId, takenTaskId);
    }

    public static void remove(int taskProgressId){
        manager.remove(taskProgressId);
    }

    public Objective getObjective() {
        return DataManager.getObjectivesManager().get(objectiveId);
    }

    public TakenTask getTakenTask() {
        return DataManager.getTakenTaskManager().get(takenTaskId);
    }

    public static TaskProgress getOrCreate(int objectiveId, int takenTaskId) {
        if (manager.exists(objectiveId, takenTaskId)) {
            return manager.get(objectiveId, takenTaskId);
        } else {
            TaskProgress taskProgress = new TaskProgress(objectiveId, takenTaskId);
            manager.add(taskProgress);
            return taskProgress;
        }
    }

    public void increaseProgress() {
        manager.update(this.id, "Progress", this.getProgress() + 1);
    }

    public static void increaseMobProgress(Player player, String mobName) {
        for (TakenTask takenTask : TakenTask.getAllFor(player)) {
            for (Objective objective : takenTask.getTask().getObjectives().values()) {
                if (objective.getType() != TaskType.MobKill) continue;
                if (objective.getTarget().equalsIgnoreCase(mobName) ||
                        objective.getTarget().equalsIgnoreCase(Settings.General.MYTHICMOBS_PREFIX + mobName)) {
                    TaskProgress progress = TaskProgress.getOrCreate(objective.getId(), takenTask.getId());
                    progress.increaseProgress();
                }
            }
        }
    }
}
