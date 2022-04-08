package me.rubix327.fancynations.data.towns;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.rubix327.fancynations.data.DataManager;
import me.rubix327.fancynations.data.AbstractDto;
import me.rubix327.fancynations.Settings;
import me.rubix327.fancynations.data.IUniqueNamable;

@Getter @Setter(AccessLevel.PACKAGE)
@AllArgsConstructor
public class Town extends AbstractDto implements IUniqueNamable {

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

}
