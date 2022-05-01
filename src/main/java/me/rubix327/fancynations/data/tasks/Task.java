package me.rubix327.fancynations.data.tasks;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.rubix327.fancynations.Settings;
import me.rubix327.fancynations.data.AbstractDto;
import me.rubix327.fancynations.data.DataManager;
import me.rubix327.fancynations.data.objectives.Objective;
import me.rubix327.fancynations.data.objectives.ObjectivesDao;
import org.bukkit.entity.Player;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;

@Getter @Setter(AccessLevel.PACKAGE)
@AllArgsConstructor
public abstract class Task extends AbstractDto {

    @Getter
    private final int id;
    private final int townId;
    private final int taskTypeId;
    private final int creatorId;
    private final String taskName;
    private String description;
    private int takeAmount;
    private int minLevel;
    private int maxLevel;
    private double moneyReward;
    private double expReward;
    private int repReward;
    private int priority;
    private Timestamp placementDateTime;
    private int timeToComplete;

    protected Task(int townId, int taskTypeId, int creatorId, String taskName){
        this.id = DataManager.getTaskManager().getMaxId() + 1;
        this.townId = townId;
        this.taskTypeId = taskTypeId;
        this.creatorId = creatorId;
        this.taskName = taskName;
        this.description = Settings.Tasks.DEFAULT_DESCRIPTION;
        this.takeAmount = Settings.Tasks.DEFAULT_TAKE_AMOUNT;
        this.minLevel = Settings.Tasks.DEFAULT_MIN_LEVEL;
        this.maxLevel = Settings.Tasks.DEFAULT_MAX_LEVEL;
        this.moneyReward = Settings.Tasks.DEFAULT_MONEY_REWARD;
        this.expReward = Settings.Tasks.DEFAULT_EXP_REWARD;
        this.repReward = Settings.Tasks.DEFAULT_REP_REWARD;
        this.priority = Settings.Tasks.DEFAULT_PRIORITY;
        this.placementDateTime = Timestamp.valueOf(LocalDateTime.now());
        this.timeToComplete = Settings.Tasks.DEFAULT_TIME_TO_COMPLETE;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public abstract boolean isObjectiveCompleted(Objective objective, Player player);

    public boolean isAllObjectivesCompleted(Player player, int taskId){

        ObjectivesDao objDao = (ObjectivesDao) DataManager.getObjectivesManager();
        for (Map.Entry<Integer, Objective> entry : objDao.getAllFor(player.getName(), taskId).entrySet()){
            Objective objective = entry.getValue();
            if (!isObjectiveCompleted(objective, player)) return false;
        }
        return true;
    }
    
}
