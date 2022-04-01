package me.rubix327.fancynations.data.task;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import me.rubix327.fancynations.data.DataManager;
import me.rubix327.fancynations.data.Settings;
import org.bukkit.entity.Player;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Getter @Setter(AccessLevel.PACKAGE)
public abstract class Task {

    @Getter
    private final HashMap<String, Integer> objectives = new HashMap<>();
    private final int id;
    private final String townName;
    private final TaskType taskType;
    private final String creatorName;
    private String taskName;
    private String description;
    private int takeAmount;
    private int minLevel;
    private int maxLevel;
    private int repReward;
    private int priority;
    private double moneyReward;
    private double expReward;
    private Timestamp placementDateTime;
    private int timeToComplete;

    protected Task(String townName, TaskType taskType, String creatorName, String taskName){
        this.id = DataManager.getTaskManager().getMaxId() + 1;
        this.townName = townName;
        this.taskType = taskType;
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

    public Task(int id, String townName, TaskType taskType, String creatorName, String taskName, String description,
                int takeAmount, int minLevel, int maxLevel, double moneyReward, double expReward, int repReward,
                int priority, Timestamp placementDateTime, int timeToComplete) {
        this.id = id;
        this.townName = townName;
        this.taskType = taskType;
        this.creatorName = creatorName;
        this.taskName = taskName;
        this.description = description;
        this.takeAmount = takeAmount;
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
        this.moneyReward = moneyReward;
        this.expReward = expReward;
        this.repReward = repReward;
        this.priority = priority;
        this.placementDateTime = placementDateTime;
        this.timeToComplete = timeToComplete;
    }

    public void addObjective(String objective, int amount){
        objectives.put(objective, amount);
    }

    public void removeObjective(String objId){
        objectives.remove(objId);
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public abstract boolean isObjectiveCompleted(String reqItemId, int reqAmount, Player player);

    public boolean isAllObjectivesCompleted(Player player){
        for (Map.Entry<String, Integer> entry : objectives.entrySet()){
            if (!isObjectiveCompleted(entry.getKey(), entry.getValue(), player)) return false;
        }
        return true;
    }

    @Override
    public String toString(){
        return "&7#" + this.getId() + " | "
                + this.getTaskType() + ", "
                + this.getCreatorName() + ", "
                + this.getTaskName();
    }
}
