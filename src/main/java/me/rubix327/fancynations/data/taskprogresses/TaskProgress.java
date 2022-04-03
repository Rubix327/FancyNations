package me.rubix327.fancynations.data.taskprogresses;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.rubix327.fancynations.data.DataManager;

@Getter @Setter(AccessLevel.PACKAGE)
@AllArgsConstructor
public class TaskProgress {

    private final int id;
    private final int objectiveId;
    private int progress;

    public TaskProgress(int objectiveId) {
        this.id = DataManager.getTaskProgressManager().getMaxId() + 1;
        this.objectiveId = objectiveId;
    }

}