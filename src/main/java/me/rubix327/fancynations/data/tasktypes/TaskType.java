package me.rubix327.fancynations.data.tasktypes;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TaskType {

    private final int id;
    private final String group;
    private final String name;

}
