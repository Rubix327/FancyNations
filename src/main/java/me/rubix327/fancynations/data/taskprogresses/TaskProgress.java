package me.rubix327.fancynations.data.taskprogresses;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.rubix327.fancynations.data.AbstractDto;
import me.rubix327.fancynations.data.DataManager;
import me.rubix327.fancynations.data.objectives.Objective;
import me.rubix327.fancynations.data.takentasks.TakenTask;

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
        this.id = DataManager.getTaskProgressManager().getMaxId() + 1;
        this.objectiveId = objectiveId;
        this.takenTaskId = takenTaskId;
    }

    public static TaskProgress get(int objectiveId, int takenTaskId){
        return manager.get(objectiveId, takenTaskId);
    }

    public static void remove(int taskProgressId){
        manager.remove(taskProgressId);
    }

    public Objective getObjective(){
        return DataManager.getObjectivesManager().get(objectiveId);
    }

    public TakenTask getTakenTask(){
        return DataManager.getTakenTaskManager().get(takenTaskId);
    }

}
