package me.rubix327.fancynations.data.objectives;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.rubix327.fancynations.data.DataManager;
import me.rubix327.fancynations.data.AbstractDto;

@Getter @Setter(AccessLevel.PACKAGE)
@AllArgsConstructor
public class Objective extends AbstractDto {

    private final int id;
    private final int task;
    private final String name;
    private final int amount;

    public Objective(int task, String name, int amount) {
        this.id = DataManager.getObjectivesManager().getMaxId() + 1;
        this.task = task;
        this.name = name;
        this.amount = amount;
    }
}
