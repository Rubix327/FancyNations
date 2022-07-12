package me.rubix327.fancynations.menu;

import me.rubix327.fancynations.data.DataManager;
import me.rubix327.fancynations.data.towns.Town;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.menu.AdvancedMenuPagged;
import org.mineacademy.fo.menu.LockedSlotsFigure;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.remain.CompMaterial;

import java.util.ArrayList;
import java.util.List;

public class TownsMenu extends AdvancedMenuPagged<Town> {

    public TownsMenu(Player player) {
        super(player);
    }

    @Override
    protected void setup() {
        setTitle("Города");
        setSize(9 * 6);
        setLockedSlots(LockedSlotsFigure.ROWS_9X6);
    }

    @Override
    protected List<Town> getElements() {
        return new ArrayList<>(DataManager.getTownManager().getAll().values());
    }

    @Override
    protected ItemStack convertToItemStack(Town town) {
        return ItemCreator.of(CompMaterial.GRASS_BLOCK).name(town.getName())
                .lore("&8Нация: " + DataManager.getNationManager().get(town.getNationId()).getName(),
                        "",
                        "&7Баланс: &f" + town.getBalance() + " монет",
                        "&7Налог аукциона: &f" + town.getAuctionTax() + "%",
                        "&7Налог заданий: &f" + town.getTasksTax() + "%",
                        "&7Налог станков: &f" + town.getStationsTax() + "%",
                        "",
                        "&7Мэр: &fRubix327"
                )
                .hideTags(true)
                .make();
    }
}
