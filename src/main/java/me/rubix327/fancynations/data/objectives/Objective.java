package me.rubix327.fancynations.data.objectives;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.rubix327.fancynations.Settings;
import me.rubix327.fancynations.data.AbstractDto;
import me.rubix327.fancynations.data.DataManager;
import me.rubix327.fancynations.data.tasks.TaskGroup;
import org.bukkit.entity.Player;

@Getter @Setter(AccessLevel.PACKAGE)
@AllArgsConstructor
public abstract class Objective extends AbstractDto {

    public static IObjectivesManager manager = DataManager.getObjectivesManager();

    private final int id;
    private final String type;
    private final String target;
    private final int amount;
    private int task;

    public Objective(String type, String target, int amount) {
        this.id = DataManager.getObjectivesManager().getMaxId() + 1;
        this.type = type;
        this.target = target;
        this.amount = amount;
        this.task = Settings.General.NULL;
    }

    public static boolean exists(int objectiveId){
        return manager.exists(objectiveId);
    }

    public static void add(String type, String target, int amount){
        if (ObjectiveInfo.get(type).getGroup() == TaskGroup.Gathering){
            GatheringObjective obj = new GatheringObjective(type, target, amount);
            manager.add(obj);
        }
        else if (ObjectiveInfo.get(type).getGroup() == TaskGroup.Mobs){
            MobKillObjective obj = new MobKillObjective(type, target, amount);
            manager.add(obj);
        }
    }

    public static void remove(int id){
        manager.remove(id);
    }

    public static void setTask(int objectiveId, int taskId){
        manager.update(objectiveId, "task", taskId);
    }

    public abstract boolean isCompleted(Player player);
}
