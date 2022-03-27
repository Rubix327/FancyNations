package me.rubix327.fancynations.data.task;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.remain.nbt.NBTContainer;
import org.mineacademy.fo.remain.nbt.NBTItem;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GatheringTask extends Task {

    @Getter
    private final HashMap<String, Integer> objectiveItems = new HashMap<>();

    public GatheringTask(String townName, TaskType taskType, String creatorName, String taskName) {
        super(townName, taskType, creatorName, taskName);
    }

    public boolean containsItem(String itemId){
        return objectiveItems.containsKey(itemId);
    }

    public void addItem(String itemId, int amount){
        if (!containsItem(itemId)){
            objectiveItems.put(itemId, amount);
        }
        else{
            objectiveItems.put(itemId, objectiveItems.get(itemId) + amount);
        }
    }

    public void removeItem(String itemId){
        if (!objectiveItems.containsKey(itemId)){
            throw new IllegalArgumentException("This task does not contain the specified item.");
        }
        objectiveItems.remove(itemId);
    }

    public boolean allObjectivesCompleted(String playerName){
        for (Map.Entry<String, Integer> entry : objectiveItems.entrySet()){
            if (!objectiveCompleted(entry.getKey(), entry.getValue(), playerName)) return false;
        }
        return true;
    }

    public boolean objectiveCompleted(String reqItemId, int reqAmount, String playerName) throws IllegalArgumentException{
        Player player = Bukkit.getPlayer(playerName);
        if (player == null) throw new IllegalArgumentException("This player is not online");

        Inventory inventory = player.getInventory();
        int totalAmount = 0;

        for (int slot = 0; slot < inventory.getSize(); slot++){
            // If slot does not contain anything
            if (inventory.getItem(slot) == null) continue;

            if (extractItemId(inventory.getItem(slot)).equalsIgnoreCase(reqItemId)){
                int foundAmount = inventory.getItem(slot).getAmount();
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
