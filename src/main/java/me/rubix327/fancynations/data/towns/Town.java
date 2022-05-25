package me.rubix327.fancynations.data.towns;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.rubix327.fancynations.Localization;
import me.rubix327.fancynations.Settings;
import me.rubix327.fancynations.data.AbstractDto;
import me.rubix327.fancynations.data.DataManager;
import me.rubix327.fancynations.data.IUniqueNamable;
import me.rubix327.fancynations.data.nations.Nation;
import org.bukkit.command.CommandSender;

@Getter @Setter(AccessLevel.PACKAGE)
@AllArgsConstructor
public class Town extends AbstractDto implements IUniqueNamable {

    @Getter
    private static ITownManager manager = DataManager.getTownManager();

    private final int id;
    private int nationId;
    private String name;
    private double balance;
    private double stationsTax;
    private double auctionTax;
    private double tasksTax;

    public Town(int nationId, String name){
        this.id = DataManager.getTownManager().getMaxId() + 1;
        this.nationId = nationId;
        this.name = name;
        this.balance = Settings.Towns.DEFAULT_BALANCE;
        this.stationsTax = Settings.Towns.DEFAULT_STATIONS_TAX;
        this.auctionTax = Settings.Towns.DEFAULT_AUCTION_TAX;
        this.tasksTax = Settings.Towns.DEFAULT_TASKS_TAX;
    }

    public static void add(int nationId, String name){
        manager.add(new Town(nationId, name));
    }

    public static Town find(int townId, CommandSender sender, String messageKey){
        if (!manager.exists(townId)) Localization.getInstance().locReturnTell(messageKey, sender);
        return manager.get(townId);
    }

    public static Town find(String townName, CommandSender sender, String messageKey){
        if (!manager.exists(townName)) Localization.getInstance().locReturnTell(messageKey, sender);
        return manager.get(townName);
    }

    public Nation getNation(){
        return DataManager.getNationManager().get(nationId);
    }
}
