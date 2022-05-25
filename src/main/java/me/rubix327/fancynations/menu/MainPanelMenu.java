package me.rubix327.fancynations.menu;

import lombok.Getter;
import me.rubix327.fancynations.data.tasks.Task;
import me.rubix327.fancynations.data.tasks.TaskType;
import me.rubix327.fancynations.data.towns.Town;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.menu.button.Button;
import org.mineacademy.fo.menu.button.ButtonMenu;
import org.mineacademy.fo.remain.CompMaterial;

import java.util.HashMap;

public class MainPanelMenu extends MenuInterlayer {

    @Getter
    private static final HashMap<TaskType, Material> itemMaterials = new HashMap<>() {{
        put(TaskType.Food, CompMaterial.PORKCHOP.toMaterial());
        put(TaskType.Resource, CompMaterial.GRANITE.toMaterial());
        put(TaskType.Crafting, CompMaterial.CHAINMAIL_CHESTPLATE.toMaterial());
        put(TaskType.MobKill, CompMaterial.SKELETON_SKULL.toMaterial());
        put(TaskType.No, CompMaterial.PAPER.toMaterial());
        put(TaskType.Combined, CompMaterial.BLAZE_POWDER.toMaterial());
    }};

    public final Player player;

    private final Button tasksButton;
    private final ButtonMenu townsButton;
    private final ButtonMenu tasksBoardButton;

    public MainPanelMenu(Player player) {
        super(player);

        this.player = player;
        setTitle("Главное меню");
        setSize(9 * 6);
        tasksButton = new ButtonMenu(
                new TasksListMenu(player), CompMaterial.BOOK,
                "&7Tasks", "", "&8Total: " + Task.getManager().getAll().size());
        townsButton = new ButtonMenu(new TownsMenu(player), CompMaterial.BIRCH_FENCE,
                "&7Towns", "", "&8Total: " + Town.getManager().getAll().size());
        tasksBoardButton = new ButtonMenu(new TownBoardMenu(player, 1), CompMaterial.BIRCH_FENCE,
                "&7Tasks board", "", "&8Total: " + Town.getManager().getAll().size());
    }

    @Override
    public ItemStack getItemAt(int slot) {
        return switch (slot) {
            case (10) -> tasksButton.getItem();
            case (12) -> townsButton.getItem();
            case (14) -> tasksBoardButton.getItem();
            default -> MenuUtil.getWrapperItem();
        };
    }

}