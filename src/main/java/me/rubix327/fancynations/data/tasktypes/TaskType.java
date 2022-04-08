package me.rubix327.fancynations.data.tasktypes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.rubix327.fancynations.data.AbstractDto;
import me.rubix327.fancynations.data.IUniqueNamable;

@Getter
@AllArgsConstructor
public class TaskType extends AbstractDto implements IUniqueNamable {

    private final int id;
    private final String group;
    private final String name;

}
