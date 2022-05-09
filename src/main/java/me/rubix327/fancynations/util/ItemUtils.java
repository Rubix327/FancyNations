package me.rubix327.fancynations.util;

import io.lumine.mythic.lib.api.item.NBTItem;
import me.rubix327.fancynations.Settings;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ItemUtils {

    /**
     * <p>Get id of the item like "DIAMOND_SWORD" or "STEEL_SWORD" for MMOItems.</p>
     * <p>Note that this method does not return null.</p>
     * <p>If item is null it will return "AIR".</p>
     * <p>If MMOItems is not installed it will return a vanilla item id.</p>
     * @param item to get id from
     * @return item id or "AIR" if item is null;
     */
    @NotNull
    public static String extractItemId(ItemStack item){

        if (item == null) return "AIR";

        if (DependencyManager.getInstance().IS_MMOITEMS_LOADED){
            NBTItem nbtItem = NBTItem.get(item);
            if (nbtItem.hasType()){
                return Settings.General.MMOITEMS_PREFIX + nbtItem.getString("MMOITEMS_ITEM_ID");
            }
        }
        return item.getType().toString();

    }

}
