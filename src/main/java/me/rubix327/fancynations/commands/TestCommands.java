package me.rubix327.fancynations.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.ItemUtil;
import org.mineacademy.fo.PlayerUtil;
import org.mineacademy.fo.command.SimpleCommand;
import org.mineacademy.fo.remain.nbt.NBTContainer;
import org.mineacademy.fo.remain.nbt.NBTItem;

import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TestCommands extends SimpleCommand {
    public TestCommands() {
        super("test");
    }

    public static ItemStack item;
    // /fn task create <town_id> <type> <name>
    @Override
    protected void onCommand() {

        // Получаем из базы
        HashMap<String, Integer> items = new HashMap<>();
        items.put("STEEL_SWORD", 1);

        if (args[0].equalsIgnoreCase("1")){
            item = getPlayer().getInventory().getItemInMainHand();
        }

        if (args[0].equalsIgnoreCase("2")){

            for(String str : items.keySet()){
                for(int i = 0 ; i < getPlayer().getInventory().getSize() ; i++) {
                    ItemStack item = getPlayer().getInventory().getItem(i);
                    tell(extractItemId(item));
                    if (extractItemId(item).equalsIgnoreCase(str)){
                        tell("yes");
                        return;
                    }
                }
            }
        }

    }

    protected String extractItemId(ItemStack item) {

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
