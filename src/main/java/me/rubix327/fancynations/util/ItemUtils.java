package me.rubix327.fancynations.util;

import io.lumine.mythic.lib.api.item.NBTItem;
import me.rubix327.fancynations.Settings;
import me.rubix327.itemslangapi.ItemsLangAPI;
import me.rubix327.itemslangapi.Lang;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.Type;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.remain.CompMaterial;

public class ItemUtils {

    /**
     * <p>Get the id of the item like "DIAMOND_SWORD" or "MI_STEEL_SWORD" for MMOItems.</p>
     * <p>Note that this method does not return null.</p>
     * <p>If the item is null it will return "AIR".</p>
     * <p>If MMOItems is not installed it will return a vanilla item id.</p>
     *
     * @param item to get id from
     * @return item id or "AIR" if item is null;
     */
    @NotNull
    public static String extractItemId(ItemStack item) {

        if (item == null) return "AIR";

        if (isFromMmoItems(item)) {
            return Settings.General.MMOITEMS_PREFIX + getMmoItemId(item);
        }
        return item.getType().toString();

    }

    /**
     * Check if the item is from MMOItems.
     *
     * @return false if the item is vanilla, or MythicLib or MMOItems is not installed.
     */
    public static boolean isFromMmoItems(ItemStack item) {
        if (DependencyManager.MYTHIC_LIB.isLoaded() && DependencyManager.MMO_ITEMS.isLoaded()) {
            NBTItem nbtItem = NBTItem.get(item);
            return nbtItem.hasType();
        }
        return false;
    }

    public static boolean isFromMmoItems(String id) {
        return id.startsWith(Settings.General.MMOITEMS_PREFIX);
    }

    /**
     * Get the pure id of the item from MMOItems (no prefixes included).
     *
     * @throws NullPointerException if MythicLib or MMOItems is not installed.
     */
    public static String getMmoItemId(ItemStack item) {
        if (DependencyManager.MYTHIC_LIB.isLoaded() && DependencyManager.MMO_ITEMS.isLoaded()) {
            return NBTItem.get(item).getString("MMOITEMS_ITEM_ID");
        }
        throw new NullPointerException("No MythicLib or MMOItems dependency found.");
    }

    /**
     * If item is from MMOItems, return its id without prefix.
     * If it is from vanilla, return unchanged id.
     */
    public static String getPureId(String id) {
        if (isFromMmoItems(id)) {
            return id.substring(Settings.General.MMOITEMS_PREFIX.length());
        }
        return id;
    }
    
    public static String getMmoItemDisplayName(ItemStack item) {
        if (!isFromMmoItems(item)) throw new IllegalArgumentException("Given item is not from MMOItems.");
        return Common.stripColors(item.getItemMeta().getDisplayName());
    }

    public static ItemStack getItemFromTarget(String target) {
        for (String type : MMOItems.plugin.getTypes().getAllTypeNames()) {
            if (!isFromMmoItems(target)) continue;
            ItemStack item = MMOItems.plugin.getItem(Type.get(type), getPureId(target));
            if (item != null) {
                return item;
            }
        }
        return ItemCreator.of(CompMaterial.fromString(target)).make();
    }

    public static String getItemName(String target, Lang lang) {
        if (isFromMmoItems(target)) {
            return getMmoItemDisplayName(getItemFromTarget(target));
        } else {
            return ItemsLangAPI.getApi().translate(ItemCreator.of(CompMaterial.fromString(target)).make(), lang);
        }
    }

}
