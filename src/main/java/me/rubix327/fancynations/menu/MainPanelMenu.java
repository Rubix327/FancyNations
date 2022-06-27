package me.rubix327.fancynations.menu;

import lombok.Getter;
import me.rubix327.fancynations.data.tasks.TaskType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.mineacademy.fo.menu.AdvancedMenu;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.remain.CompMaterial;

import java.util.HashMap;

public class MainPanelMenu extends AdvancedMenu {

    @Getter
    private static final HashMap<TaskType, Material> itemMaterials = new HashMap<>() {{
        put(TaskType.Food, CompMaterial.PORKCHOP.toMaterial());
        put(TaskType.Resource, CompMaterial.GRANITE.toMaterial());
        put(TaskType.Crafting, CompMaterial.CHAINMAIL_CHESTPLATE.toMaterial());
        put(TaskType.MobKill, CompMaterial.SKELETON_SKULL.toMaterial());
        put(TaskType.No, CompMaterial.PAPER.toMaterial());
        put(TaskType.Combined, CompMaterial.BLAZE_POWDER.toMaterial());
    }};

    public MainPanelMenu(Player player) {
        super(player);
    }

    @Override
    protected void setup() {
        setTitle("Главное меню");
        setSize(9 * 6);

        addButton(10, getMenuButton(TasksListMenu.class, ItemCreator.of(CompMaterial.BOOK, "&7Задания").make()));
        addButton(12, getMenuButton(TownsMenu.class, ItemCreator.of(CompMaterial.BIRCH_FENCE, "&7Города").make()));
        addButton(14, getMenuButton(TownBoardMenu.class, ItemCreator.of(CompMaterial.ITEM_FRAME, "&7Доска заданий города").make()));
    }
}