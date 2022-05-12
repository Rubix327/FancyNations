package me.rubix327.fancynations.data.objectives;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.rubix327.fancynations.data.AbstractDto;
import me.rubix327.fancynations.data.DataManager;
import me.rubix327.fancynations.data.tasks.TaskGroup;
import org.bukkit.entity.Player;

@Getter @Setter(AccessLevel.PACKAGE)
@AllArgsConstructor
public abstract class Objective extends AbstractDto {

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
    }

    public abstract boolean isCompleted(Player player);

    public TaskGroup getGroup(){
        return ObjectiveInfo.get(type).getGroup();
    }
}
