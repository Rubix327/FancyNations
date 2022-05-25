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
    private final String typeName;
    private final String target;
    private final int amount;
    private final int taskId;

    public Objective(String typeName, String target, int amount, int taskId) {
        this.id = DataManager.getObjectivesManager().getMaxId() + 1;
        this.typeName = typeName;
        this.target = target;
        this.amount = amount;
        this.taskId = taskId;
    }

    public static boolean exists(int objectiveId){
        return manager.exists(objectiveId);
    }

    public static void add(String type, String target, int amount, int taskId){
        if (ObjectiveInfo.get(type).getGroup() == TaskGroup.Gathering){
            GatheringObjective obj = new GatheringObjective(type, target, amount, taskId);
            manager.add(obj);
        }
        else if (ObjectiveInfo.get(type).getGroup() == TaskGroup.Mobs){
            MobKillObjective obj = new MobKillObjective(type, target, amount, taskId);
            manager.add(obj);
        }
    }

    public static void remove(int id){
        manager.remove(id);
    }

    public static HashMap<Integer, Objective> getAllFor(int taskId){
        return manager.getAllFor(taskId);
    }

    public static void setTaskId(int objectiveId, int taskId){
        manager.update(objectiveId, "task", taskId);
    }

    public Task getTask(){
        return DataManager.getTaskManager().get(taskId);
    }

    public String getGroup(){
        return ObjectiveInfo.getObjectiveInfo().get(TaskType.valueOf(typeName)).getGroup().toString();
    }
    public abstract boolean isReadyToComplete(Player player);

    public abstract void complete(Player player, CreatorType creatorType);
}
