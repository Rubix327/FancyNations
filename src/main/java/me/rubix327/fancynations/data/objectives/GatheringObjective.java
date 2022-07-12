package me.rubix327.fancynations.data.objectives;

import me.rubix327.fancynations.Settings;
import me.rubix327.fancynations.data.tasks.CreatorType;
import me.rubix327.fancynations.data.tasks.TaskType;
import me.rubix327.fancynations.data.townresources.TownResource;
import me.rubix327.fancynations.util.ItemUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static me.rubix327.fancynations.util.ItemUtils.*;

public class GatheringObjective extends Objective {
    public GatheringObjective(TaskType type, String target, int amount, int taskId) {
        super(type, target, amount, taskId);
    }

    public GatheringObjective(int id, TaskType type, String target, int amount, int taskId) {
        super(id, type, target, amount, taskId);
    }

    public int getCurrentAmount(Player player) throws IllegalArgumentException {
        if (player == null) throw new IllegalArgumentException("This player is not online");
        int totalAmount = 0;

        Inventory inventory = player.getInventory();
        for (int slot = 0; slot < inventory.getSize(); slot++) {
            ItemStack itemStack = inventory.getItem(slot);
            // If slot does not contain anything
            if (itemStack == null) continue;

            if (extractItemId(itemStack).equalsIgnoreCase(getTarget())) {
                int foundAmount = itemStack.getAmount();
                totalAmount += foundAmount;
            }
        }
        return totalAmount;
    }

    public boolean isReadyToComplete(Player player) {
        return getCurrentAmount(player) >= getAmount();
    }

    /**
     * Completes the objective for the player.
     * Removes items from the player's inventory and sends resources to the creator.
     */
    @Override
    public void complete(Player player, CreatorType creatorType) {
        if (!isReadyToComplete(player)) return;
        if (getTaskId() == Settings.General.NULL) return;

        Inventory inventory = player.getInventory();
        boolean fromMmoItems = isFromMmoItems(getTarget());
        String pureTarget = getPureId(getTarget());

        for (int slot = 0; slot < inventory.getSize(); slot++) {
            ItemStack itemStack = inventory.getItem(slot);
            if (itemStack == null) continue;

            // Item is MMOItem
            if (fromMmoItems) {
                if (!ItemUtils.isFromMmoItems(itemStack)) continue;
                if (getMmoItemId(itemStack).equalsIgnoreCase(ItemUtils.getPureId(pureTarget))) {
                    removeItems(player, slot, itemStack);
                }
            }
            // Item is vanilla
            else if (itemStack.getType().toString().equalsIgnoreCase(pureTarget)) {
                removeItems(player, slot, itemStack);
            }
        }

        // Send the resources to the creator
        if (creatorType == CreatorType.Player) {
            // TODO: send to mail
        } else if (creatorType == CreatorType.Town) {
            TownResource resource = TownResource.getOrCreate(getTask().getTownId(), getTarget());
            resource.addResources(getAmount());
        } else if (creatorType == CreatorType.Nation) {
            TownResource resource = TownResource.getOrCreate(getTask().getTownId(), getTarget());
            resource.addResources(getAmount() * Settings.Rewards.TOWN_RESOURCE_SHARE / 100);
        }
    }

    private void removeItems(Player player, int slot, ItemStack itemStack){
        itemStack.setAmount(itemStack.getAmount() - getAmount());
        player.getInventory().setItem(slot, itemStack);
    }
}
