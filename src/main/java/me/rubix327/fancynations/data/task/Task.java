package me.rubix327.fancynations.data.task;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import me.rubix327.fancynations.data.DataManager;

@Getter @Setter(AccessLevel.PACKAGE)
public abstract class Task {

    private static int maxId = 0;

    private final int id;
    private final String townName;
    private final TaskType taskType;
    private final String creatorName;
    private String taskName;
    private String description;
    private int takeAmount;
    private int minLevel;
    private int maxLevel;
    private int reputationReward;
    private int priority;
    private double moneyReward;
    private double expReward;

    protected Task(String townName, TaskType taskType, String creatorName, String taskName){
        this.id = DataManager.generateId(TaskManager.getTasks().keySet());
        this.townName = townName;
        this.taskType = taskType;
        this.creatorName = creatorName;
        this.taskName = taskName;
        this.description = "";
        this.takeAmount = 1;
        this.minLevel = 0;
        this.maxLevel = 100;
        this.moneyReward = 0;
        this.expReward = 0;
        this.reputationReward = 0;
        this.priority = 0;
    }

    protected Task(String townName, TaskType taskType, String taskName, String creatorName, String description,
                   int takeAmount, int minLevel, int maxLevel, double moneyReward, double expReward,
                   int reputationReward, int priority) {
        this.id = DataManager.generateId(TaskManager.getTasks().keySet());
        this.townName = townName;
        this.taskType = taskType;
        this.taskName = taskName;
        this.creatorName = creatorName;
        this.description = description;
        this.takeAmount = takeAmount;
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
        this.moneyReward = moneyReward;
        this.expReward = expReward;
        this.reputationReward = reputationReward;
        this.priority = priority;
    }

    // "Town: SunRise, Type: Food, Name: 'Hello world!', Creator: Rubix327
    public String deserialize(){
        return "ID: " + this.id +
                ", Town: " + this.townName +
                ", Type: " + this.taskType.toString() +
                ", Name: " + this.taskName +
                ", Creator: " + this.creatorName;
    }

    @Override
    public String toString(){
        return "&7#" + this.getId() + " | "
                + this.getTaskType() + ", "
                + this.getTownName() + ", "
                + this.getCreatorName() + ", "
                + this.getTaskName();
    }
}
