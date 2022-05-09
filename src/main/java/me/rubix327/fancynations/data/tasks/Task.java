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
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Getter @Setter(AccessLevel.PACKAGE)
@AllArgsConstructor
public class Task extends AbstractDto {

    @Getter
    private final int id;
    private final int townId;
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

    public Task(int townId, int creatorId, String taskName){
        this.id = DataManager.getTaskManager().getMaxId() + 1;
        this.townId = townId;
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

    public boolean isAllObjectivesCompleted(Player player){

        ObjectivesDao objDao = (ObjectivesDao) DataManager.getObjectivesManager();
        for (Map.Entry<Integer, Objective> entry : objDao.getAllFor(getId()).entrySet()){
            Objective objective = entry.getValue();
            if (!objective.isCompleted(player)) return false;
        }
        return true;
    }

    public TaskType getType(){
        Map<String, String> objTypes = new HashMap<>();
        DataManager.getObjectivesManager().getAllFor(this.getId()).values()
                .forEach(obj -> objTypes.put(obj.getType(), obj.getType()));

        if (objTypes.isEmpty()) return TaskType.No;
        else if (objTypes.size() == 1) return TaskType.valueOf(objTypes.keySet().toArray()[0].toString());
        else return TaskType.Combined;
    }

    public String getLocalizedTypeName(CommandSender sender){
        return TaskType.getLocalizedName(getType().toString(), sender);
    }

    public String getLocalizedDescription(CommandSender sender){
        if (this.description.equalsIgnoreCase(Settings.Tasks.DEFAULT_DESCRIPTION)){
            return Localization.getInstance().get("task_no_description", sender);
        }
        return this.description;
    }

    public String getLocalizedCreatorName(CommandSender sender){
        FNPlayer fnPlayer = DataManager.getFNPlayerManager().get(this.getCreatorId());
        return fnPlayer.getName().equals(Settings.General.SERVER_VAR) ?
                Localization.getInstance().get("server_label", sender) : fnPlayer.getName();
    }

    public String getTownName(){
        return DataManager.getTownManager().get(this.getTownId()).getName();
    }
    
}
