package me.rubix327.fancynations.data.tasks;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.rubix327.fancynations.Localization;
import me.rubix327.fancynations.Settings;
import me.rubix327.fancynations.data.AbstractDto;
import me.rubix327.fancynations.data.DataManager;
import me.rubix327.fancynations.data.fnplayers.FNPlayer;
import me.rubix327.fancynations.data.objectives.Objective;
import me.rubix327.fancynations.data.objectives.ObjectivesDao;
import me.rubix327.fancynations.data.towns.Town;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Getter @Setter(AccessLevel.PACKAGE)
@AllArgsConstructor
public class Task extends AbstractDto {

    public static ITaskManager manager = DataManager.getTaskManager();

    @Getter
    private final int id;
    private final int townId;
    private final int creatorId;
    private final String name;
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

    public Task(int townId, int creatorId, String name){
        this.id = DataManager.getTaskManager().getMaxId() + 1;
        this.townId = townId;
        this.creatorId = creatorId;
        this.name = name;
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

    public static boolean exists(int taskId){
        return manager.exists(taskId);
    }

    public static void add(int townId, int creatorId, String name){
        Task task = new Task(townId, creatorId, name);
        manager.add(task);
    }

    public static void remove(int taskId){
        manager.remove(taskId);
    }

    /**
     * Attempts to find a Task by its id, but if it does not exist
     * sends a message to the command sender and throws CommandException.
     * @param sender command sender
     * @param messageKey key from messages_x.yml
     * @return Task object
     */
    public static Task find(int taskId, CommandSender sender, String messageKey){
        if (!manager.exists(taskId)) Localization.getInstance().locReturnTell(messageKey, sender);
        return manager.get(taskId);
    }

    /**
     * Increase or decrease take amount of the task.
     */
    public static void increaseTakeAmount(int taskId, int amount){
        manager.update(taskId, "takeAmount", manager.get(taskId).getTakeAmount() + amount);
    }

    /**
     * Check if all the objectives are completed by specified player.
     */
    public boolean isAllObjectivesCompleted(Player player){

        ObjectivesDao objDao = (ObjectivesDao) DataManager.getObjectivesManager();
        for (Map.Entry<Integer, Objective> entry : objDao.getAllFor(getId()).entrySet()){
            Objective objective = entry.getValue();
            if (!objective.isCompleted(player)) return false;
        }
        return true;
    }

    /**
     * Get the type of this task, e.g. "Crafting" or "MobKill".
     */
    public TaskType getType(){
        Map<String, String> objTypes = new HashMap<>();
        DataManager.getObjectivesManager().getAllFor(this.getId()).values()
                .forEach(obj -> objTypes.put(obj.getType(), obj.getType()));

        if (objTypes.isEmpty()) return TaskType.No;
        else if (objTypes.size() == 1) {
            return TaskType.valueOf(objTypes.entrySet().iterator().next().getKey());
        }
        else return TaskType.Combined;
    }

    /**
     * Get localized type name of this task, e.g. "Крафтинг" or "Истребление".
     */
    public String getLocalizedTypeName(CommandSender sender){
        return TaskType.getLocalizedName(getType().toString(), sender);
    }

    /**
     * Get localized description of this task.
     * If task has no description set, it will return "task_no_description" node.
     */
    public String getLocalizedDescription(CommandSender sender){
        if (this.description.equalsIgnoreCase(Settings.Tasks.DEFAULT_DESCRIPTION)){
            return Localization.getInstance().get("task_no_description", sender);
        }
        return this.description;
    }

    /**
     * Get localized server label if the task has been created by the server.
     */
    public String getLocalizedCreatorName(CommandSender sender){
        FNPlayer fnPlayer = DataManager.getFNPlayerManager().get(this.getCreatorId());
        return fnPlayer.getName().equals(Settings.General.SERVER_VAR) ?
                Localization.getInstance().get("server_label", sender) : fnPlayer.getName();
    }

    public Town getTown(){
        return DataManager.getTownManager().get(townId);
    }

    public FNPlayer getCreator(){
        return DataManager.getFNPlayerManager().get(creatorId);
    }

}