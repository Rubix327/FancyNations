package me.rubix327.fancynations.data.workertypes;

import lombok.Getter;
import me.rubix327.fancynations.data.AbstractDto;
import me.rubix327.fancynations.data.Settings;
import me.rubix327.fancynations.data.IUniqueNamable;

@Getter
public class WorkerType extends AbstractDto implements IUniqueNamable {

    private final int id;
    private final String name;
    private final String diplayName;

    public WorkerType(int id, String name) {
        this.id = id;
        this.name = name;
        this.diplayName = getDefaultDisplayName(name);
    }

    private String getDefaultDisplayName(String workerType){
        return switch (workerType.toLowerCase()) {
            case ("mayor") -> Settings.TownWorkers.MAYOR_DEFAULT_DISPLAYNAME;
            case ("helper") -> Settings.TownWorkers.HELPER_DEFAULT_DISPLAYNAME;
            case ("judge") -> Settings.TownWorkers.JUDGE_DEFAULT_DISPLAYNAME;
            case ("other") -> Settings.TownWorkers.OTHER_DEFAULT_DISPLAYNAME;
            default -> throw new NullPointerException("This worker type does not exist.");
        };
    }
}
