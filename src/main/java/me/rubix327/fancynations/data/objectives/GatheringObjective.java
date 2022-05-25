package me.rubix327.fancynations.data.objectives;

import me.rubix327.fancynations.Settings;
import me.rubix327.fancynations.data.DataManager;
import me.rubix327.fancynations.data.tasks.CreatorType;
import me.rubix327.fancynations.data.townresources.TownResource;
import me.rubix327.fancynations.util.ItemUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static me.rubix327.fancynations.util.ItemUtils.*;

public class GatheringObjective extends Objective{
    public GatheringObjective(String type, String target, int amount, int taskId) {
        super(type, target, amount, taskId);
    }

    public GatheringObjective(int id, String type, String target, int amount, int taskId) {
        super(id, type, target, amount, taskId);
    }

    public boolean isReadyToComplete(Player player) throws IllegalArgumentException{
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

    /**
     * Completes the objective for the player.
     * Removes items from the player's inventory and sends resources to the creator.
     */
    @Override
    public void complete(Player player, CreatorType creatorType){
        if (!isReadyToComplete(player)) return;
        if (getTaskId() == Settings.General.NULL) return;

        Inventory inventory = player.getInventory();
        boolean fromMmoItems = isFromMmoItems(getTarget());
        String pureTarget = getPureId(getTarget());

        for (int slot = 0; slot < inventory.getSize(); slot++){
            ItemStack itemStack = inventory.getItem(slot);
            if (itemStack == null) continue;

            // Item is MMOItem
            if (fromMmoItems){
                if (!ItemUtils.isFromMmoItems(itemStack)) continue;
                if (getMmoItemId(itemStack).equalsIgnoreCase(ItemUtils.getPureId(pureTarget))){
                    removeItems(player, slot, itemStack);
                }
            }
            // Item is vanilla
            else if (itemStack.getType().toString().equalsIgnoreCase(pureTarget)){
                removeItems(player, slot, itemStack);
            }
        }

        if (creatorType == CreatorType.Player){
            // TODO: send to mail
        }
        else if (creatorType == CreatorType.Town){
            DataManager.getTownResourceManager().add(new TownResource(getTask().getTownId(), getTarget(), getAmount()));
        }
        else if (creatorType == CreatorType.Nation){
            DataManager.getTownResourceManager().add(new TownResource(
                    getTask().getTownId(), getTarget(), getAmount() * Settings.Rewards.TOWN_RESOURCE_SHARE));
        }
    }

    private void removeItems(Player player, int slot, ItemStack itemStack){
        itemStack.setAmount(itemStack.getAmount() - getAmount());
        player.getInventory().setItem(slot, itemStack);
    }
}
