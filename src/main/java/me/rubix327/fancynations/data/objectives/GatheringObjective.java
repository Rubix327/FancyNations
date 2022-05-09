package me.rubix327.fancynations.data.objectives;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static me.rubix327.fancynations.util.ItemUtils.extractItemId;

public class GatheringObjective extends Objective{
    public GatheringObjective(String type, String target, int amount) {
        super(type, target, amount);
    }

    public GatheringObjective(int id, String type, String target, int amount, int task) {
        super(id, type, target, amount, task);
    }

    public boolean isCompleted(Player player) throws IllegalArgumentException{
        if (player == null) throw new IllegalArgumentException("This player is not online");

        int totalAmount = 0;

        Inventory inventory = player.getInventory();
        for (int slot = 0; slot < inventory.getSize(); slot++){
            ItemStack itemStack = inventory.getItem(slot);
            // If slot does not contain anything
            if (itemStack == null) continue;

            if (extractItemId(itemStack).equalsIgnoreCase(getTarget())){
                int foundAmount = itemStack.getAmount();
                if (foundAmount >= getAmount()){
                    return true;
                }
                totalAmount += foundAmount;
                if (totalAmount >= getAmount()) return true;
            }
        }
        return false;
    }
}
