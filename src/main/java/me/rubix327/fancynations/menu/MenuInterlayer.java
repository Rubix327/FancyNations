package me.rubix327.fancynations.menu;

import lombok.Getter;
import me.rubix327.fancynations.Localization;
import org.bukkit.entity.Player;
import org.mineacademy.fo.menu.Menu;

public class MenuInterlayer extends Menu {

    protected Localization msgs = Localization.getInstance();
    /**
     * Defines is the player has admin permission or not
     */
    protected final boolean isPlayerAdmin;

    /**
     * The player viewing the menu
     */
    @Getter
    private final Player player;

    public MenuInterlayer(Player player){
        this.player = player;
        this.isPlayerAdmin = getPlayer().hasPermission("fancynations.admin");
    }

    /**
     * Display this menu to the player from the constructor.
     */
    public final void display(){
        displayTo(getPlayer());
    }

}
