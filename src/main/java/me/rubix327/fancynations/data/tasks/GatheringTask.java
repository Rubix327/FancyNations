package me.rubix327.fancynations.data.tasks;

import me.rubix327.fancynations.data.objectives.Objective;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.remain.nbt.NBTContainer;
import org.mineacademy.fo.remain.nbt.NBTItem;

import java.sql.Timestamp;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GatheringTask extends Task {

    public GatheringTask(int townId, int taskTypeId, int creatorId, String taskName) {
        super(townId, taskTypeId, creatorId, taskName);
    }

    public GatheringTask(int id, int townId, int taskTypeId, int creatorId, String taskName, String description,
                         int takeAmount, int minLevel, int maxLevel, double moneyReward, double expReward,
                         int repReward, int priority, Timestamp placementDateTime, int timeToComplete) {
        super(id, townId, taskTypeId, creatorId, taskName, description,
                takeAmount, minLevel, maxLevel, moneyReward, expReward,
                repReward, priority, placementDateTime, timeToComplete);
    }

    public boolean isObjectiveCompleted(Objective objective, Player player) throws IllegalArgumentException{
        if (player == null) throw new IllegalArgumentException("This player is not online");

        String reqItemId = objective.getName();
        int reqAmount = objective.getAmount();
        int totalAmount = 0;

        Inventory inventory = player.getInventory();
        for (int slot = 0; slot < inventory.getSize(); slot++){
            ItemStack itemStack = inventory.getItem(slot);
            // If slot does not contain anything
            if (itemStack == null) continue;

            if (extractItemId(itemStack).equalsIgnoreCase(reqItemId)){
                int foundAmount = itemStack.getAmount();
                if (foundAmount >= reqAmount){
                    return true;
                }
                totalAmount += foundAmount;
                if (totalAmount >= reqAmount) return true;
            }
        }
        return false;
    }

    protected static String extractItemId(ItemStack item) {

        if (item == null) return "AIR";

        NBTContainer nbtItem = NBTItem.convertItemtoNBT(item);

        if (nbtItem.toString().contains("MMOITEMS_ITEM_ID")){

            String match = "";
            Pattern pattern = Pattern.compile("(MMOITEMS_ITEM_ID:\")+([^\"]*)");
            Matcher matcher = pattern.matcher(nbtItem.toString());
            while(matcher.find())
                match = matcher.group().replace("MMOITEMS_ITEM_ID:\"", "");
            return match;
        }
        else{
            return item.getType().toString();
        }

    }
}
