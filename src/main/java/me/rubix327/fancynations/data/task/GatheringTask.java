package me.rubix327.fancynations.data.task;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.remain.nbt.NBTContainer;
import org.mineacademy.fo.remain.nbt.NBTItem;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GatheringTask extends Task {

    public GatheringTask(String townName, TaskType taskType, String creatorName, String taskName) {
        super(townName, taskType, creatorName, taskName);
    }

    public boolean isObjectiveCompleted(String reqItemId, int reqAmount, String playerName) throws IllegalArgumentException{
        Player player = Bukkit.getPlayer(playerName);
        if (player == null) throw new IllegalArgumentException("This player is not online");

        Inventory inventory = player.getInventory();
        int totalAmount = 0;

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
