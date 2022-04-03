package me.rubix327.fancynations.data.tasks;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.rubix327.fancynations.data.DataManager;
import me.rubix327.fancynations.data.Settings;
import org.bukkit.entity.Player;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;

@Getter @Setter(AccessLevel.PACKAGE)
@AllArgsConstructor
public abstract class Task {

    @Getter
    private final int id;
    private final int townId;
    private final int taskTypeId;
    private final String creatorName;
    private String taskName;
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

    protected Task(int townId, int taskTypeId, String creatorName, String taskName){
        this.id = DataManager.getTaskManager().getMaxId() + 1;
        this.townId = townId;
        this.taskTypeId = taskTypeId;
        this.creatorName = creatorName;
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
    public abstract boolean isObjectiveCompleted(String reqItemId, int reqAmount, Player player);

    public boolean isAllObjectivesCompleted(Player player, int taskId){
        for (Map.Entry<String, Integer> entry : DataManager.getObjectivesManager()
                .getAllFor(player.getName(), taskId).entrySet()){
            if (!isObjectiveCompleted(entry.getKey(), entry.getValue(), player)) return false;
        }
        return true;
    }

    @Override
    public String toString(){
        return "&7#" + this.getId() + " | "
                + DataManager.getTaskTypeManager().get(this.getTaskTypeId()).getName() + ", "
                + this.getCreatorName() + ", "
                + this.getTaskName();
    }
}
