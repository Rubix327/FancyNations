package me.rubix327.fancynations.menu;

import org.bukkit.entity.Player;
import org.mineacademy.fo.menu.AdvancedMenu;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.remain.CompMaterial;

public class MainPanelMenu extends AdvancedMenu {

    public MainPanelMenu(Player player) {
        super(player);
    }

    @Override
    protected void setup() {
        setTitle("Главное меню");
        setSize(9 * 6);

        addButton(10, getMenuButton(TasksListMenu.class, ItemCreator.of(CompMaterial.BOOK, "&7Задания").make()));
        addButton(12, getMenuButton(TownsMenu.class, ItemCreator.of(CompMaterial.BIRCH_FENCE, "&7Города").make()));
        addButton(14, getMenuButton(new TownBoardMenu(getPlayer(), 1), ItemCreator.of(CompMaterial.ITEM_FRAME, "&7Доска заданий города").make()));
    }
}