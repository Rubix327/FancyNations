package me.rubix327.fancynations.data.tasks;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.rubix327.fancynations.FancyNations;
import me.rubix327.fancynations.Localization;
import me.rubix327.fancynations.Settings;
import me.rubix327.fancynations.data.AbstractDto;
import me.rubix327.fancynations.data.DataManager;
import me.rubix327.fancynations.data.fnplayers.FNPlayer;
import me.rubix327.fancynations.data.objectives.Objective;
import me.rubix327.fancynations.data.objectives.ObjectivesDao;
import me.rubix327.fancynations.data.reputations.Reputation;
import me.rubix327.fancynations.data.takentasks.TakenTask;
import me.rubix327.fancynations.data.taskprogresses.TaskProgress;
import me.rubix327.fancynations.data.towns.Town;
import me.rubix327.fancynations.util.DependencyManager;
import net.Indyuce.mmocore.api.player.PlayerData;
import net.Indyuce.mmocore.experience.EXPSource;
import net.milkbowl.vault.economy.Economy;
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
    private static ITaskManager manager = DataManager.getTaskManager();
    private static Localization msgs = Localization.getInstance();

    @Getter
    private final int id;
    private final int townId;
    private final String name;
    private final int creatorId;
    private String creatorTypeName;
    private String description;
    private int completionsLeft;
    private int minLevel;
    private int maxLevel;
    private double moneyReward;
    private double expReward;
    private int repReward;
    private int priority;
    private final Timestamp placementDateTime;
    private Timestamp terminationDateTime;
    private int timeToComplete;

    public Task(int townId, String name, int creatorId, String creatorTypeName){
        this.id = manager.getNextId();
        this.townId = townId;
        this.name = name;
        this.creatorId = creatorId;
        this.creatorTypeName = creatorTypeName;
        this.description = Settings.Tasks.DEFAULT_DESCRIPTION;
        this.completionsLeft = Settings.Tasks.DEFAULT_TAKE_AMOUNT;
        this.minLevel = 1;
        this.maxLevel = Integer.MAX_VALUE;
        this.moneyReward = Settings.Tasks.DEFAULT_MONEY_REWARD;
        this.expReward = Settings.Tasks.DEFAULT_EXP_REWARD;
        this.repReward = Settings.Tasks.DEFAULT_REP_REWARD;
        this.priority = Settings.Tasks.DEFAULT_PRIORITY;
        this.placementDateTime = Timestamp.valueOf(LocalDateTime.now());
        this.timeToComplete = Settings.Tasks.DEFAULT_TIME_TO_COMPLETE;
        this.terminationDateTime = Timestamp.valueOf(placementDateTime.toLocalDateTime().plusSeconds(timeToComplete));
    }

    public static boolean exists(int taskId){
        return manager.exists(taskId);
    }

    public static void add(int townId, String name, int creatorId, String creatorType){
        Task task = new Task(townId, name, creatorId, creatorType);
        manager.add(task);
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
    public static void increaseCompletionsLeft(int taskId, int amount){
        manager.update(taskId, "completionsLeft", manager.get(taskId).getCompletionsLeft() + amount);
    }

    /**
     * Get the type of this task, e.g. "Crafting" or "MobKill".
     */
    public TaskType getType(){
        Map<TaskType, TaskType> objTypes = new HashMap<>();
        DataManager.getObjectivesManager().getAllFor(this.getId()).values()
                .forEach(obj -> objTypes.put(obj.getType(), obj.getType()));

        if (objTypes.isEmpty()) return TaskType.No;
        else if (objTypes.size() == 1) {
            return objTypes.entrySet().iterator().next().getKey();
        }
        else return TaskType.Combined;
    }

    /**
     * Get localized type name of this task, e.g. "Крафтинг" or "Истребление".
     */
    public String getLocalizedTypeName(CommandSender sender){
        return getType().getLocalizedName(sender);
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
        FNPlayer fnPlayer = DataManager.getFNPlayerManager().get(getCreatorId());
        return isServerCreated() ? Localization.getInstance().get("server_label", sender) : fnPlayer.getName();
    }

    public boolean isServerCreated(){
        return FNPlayer.get(getCreatorId()).getName().equalsIgnoreCase(Settings.General.SERVER_VAR);
    }

    public Town getTown(){
        return DataManager.getTownManager().get(townId);
    }

    public CreatorType getCreatorType() {
        return CreatorType.valueOf(creatorTypeName);
    }

    public FNPlayer getCreator() {
        return DataManager.getFNPlayerManager().get(creatorId);
    }

    public HashMap<Integer, Objective> getObjectives() {
        return Objective.getManager().getAllFor(this.getId());
    }

    /**
     * Check if all the objectives are completed by specified player.
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isAllObjectivesReadyToComplete(Player player) {

        ObjectivesDao objDao = (ObjectivesDao) DataManager.getObjectivesManager();
        for (Map.Entry<Integer, Objective> entry : objDao.getAllFor(getId()).entrySet()) {
            Objective objective = entry.getValue();
            if (!objective.isReadyToComplete(player)) return false;
        }
        return true;
    }

    public void take(Player player){
        // Task is not available anymore
        if (getCompletionsLeft() <= 0){
            Localization.getInstance().locTell("error_task_not_available", player);
            return;
        }

        TakenTask.add(player, getId());
        Task.increaseCompletionsLeft(getId(), -1);
    }

    public void complete(Player player){
        if (!isAllObjectivesReadyToComplete(player)) return;
        for (Objective obj : Objective.getAllFor(getId()).values()){
            obj.complete(player, getCreatorType());
        }
        silentRemove(player);

        // Give money reward
        Economy economy = FancyNations.getInstance().getEconomy();
        economy.depositPlayer(player, getMoneyReward());

        FNPlayer fnPlayer = FNPlayer.get(player.getName());

        // Give reputation reward
        Reputation.increase(fnPlayer.getId(), getTownId(), getRepReward());

        // Give MMO experience reward
        if (DependencyManager.MMO_CORE.isLoaded()) {
            PlayerData.get(player.getUniqueId())
                    .giveExperience(getExpReward(), EXPSource.QUEST, player.getLocation(), false);
        } else {
            player.giveExp((int) getExpReward());
        }

        // Send a certain share of resources or mobs to a town
//        for (Objective objective : DataManager.getObjectivesManager().getAllFor(getId()).values()) {
//            int amount = (int) Math.ceil(objective.getAmount() * objective.getType().getTownShare() / 100.0);
//            TownResource resource = TownResource.getOrCreate(townId, objective.getTarget());
//            resource.addResources(amount);
//    }
    }

    public void cancel(Player player){
        silentRemove(player);
        Task.increaseCompletionsLeft(getId(), 1);
    }

    private void silentRemove(Player player){
        // Remove connected task progresses
        int takenTaskId = TakenTask.find(player, getId()).getId();
        for (TaskProgress progress : DataManager.getTaskProgressManager().getAllByTakenTask(takenTaskId).values()){
            DataManager.getTaskProgressManager().remove(progress.getId());
        }
        TakenTask.remove(player, getId());
    }

    public int getCount(){
        return TakenTask.getManager().getCountFor(getId());
    }

}