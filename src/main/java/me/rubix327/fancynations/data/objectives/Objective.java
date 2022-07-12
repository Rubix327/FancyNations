package me.rubix327.fancynations.data.objectives;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.rubix327.fancynations.data.AbstractDto;
import me.rubix327.fancynations.data.DataManager;
import me.rubix327.fancynations.data.tasks.CreatorType;
import me.rubix327.fancynations.data.tasks.Task;
import me.rubix327.fancynations.data.tasks.TaskGroup;
import me.rubix327.fancynations.data.tasks.TaskType;
import org.bukkit.entity.Player;

import java.util.HashMap;

@Getter @Setter(AccessLevel.PACKAGE)
@AllArgsConstructor
public abstract class Objective extends AbstractDto {

    @Getter
    private static IObjectivesManager manager = DataManager.getObjectivesManager();

    private final int id;
    private final TaskType type;
    private final String target;
    private final int amount;
    private final int taskId;

    public Objective(TaskType type, String target, int amount, int taskId) {
        this.id = DataManager.getObjectivesManager().getNextId();
        this.type = type;
        this.target = target;
        this.amount = amount;
        this.taskId = taskId;
    }

    public static boolean exists(int objectiveId) {
        return manager.exists(objectiveId);
    }

    public static void add(String typeName, String target, int amount, int taskId) {
        TaskType type = TaskType.getOrNull(typeName);
        if (TaskGroup.Gathering == type.getGroup()) {
            GatheringObjective obj = new GatheringObjective(type, target, amount, taskId);
            manager.add(obj);
        } else if (TaskGroup.Mobs == type.getGroup()) {
            MobKillObjective obj = new MobKillObjective(type, target, amount, taskId);
            manager.add(obj);
        }
    }

    public static void remove(int id) {
        manager.remove(id);
    }

    public static HashMap<Integer, Objective> getAllFor(int taskId){
        return manager.getAllFor(taskId);
    }

    public static void setTaskId(int objectiveId, int taskId) {
        manager.update(objectiveId, "task", taskId);
    }

    public Task getTask() {
        return DataManager.getTaskManager().get(taskId);
    }

    public TaskGroup getGroup() {
        return type.getGroup();
    }

    public abstract int getCurrentAmount(Player player);

    public abstract boolean isReadyToComplete(Player player);

    public abstract void complete(Player player, CreatorType creatorType);
}
