package me.rubix327.fancynations.data.professions;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.rubix327.fancynations.Localization;
import me.rubix327.fancynations.Settings;
import me.rubix327.fancynations.data.AbstractDto;
import me.rubix327.fancynations.data.DataManager;
import me.rubix327.fancynations.data.IUniqueNamable;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

@Getter @Setter(AccessLevel.PACKAGE)
@AllArgsConstructor
public class Profession extends AbstractDto implements IUniqueNamable {

    private final static IProfessionManager manager = DataManager.getProfessionManager();
    private static boolean initiated = false;

    private final int id;
    private final String name;
    private double salary;
    private double stationsTaxBonus;
    private double auctionTaxBonus;
    private double tasksTaxBonus;
    private Boolean permissionOpenBank;
    private Boolean permissionDeposit;
    private Boolean permissionWithdraw;

    public Profession(int id, String name, double salary, double stationsTaxBonus, double auctionTaxBonus, double tasksTaxBonus) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.stationsTaxBonus = stationsTaxBonus;
        this.auctionTaxBonus = auctionTaxBonus;
        this.tasksTaxBonus = tasksTaxBonus;
        this.permissionOpenBank = true;
        this.permissionDeposit = true;
        this.permissionWithdraw = false;
    }

    public Profession(String name) {
        this.id = DataManager.getProfessionManager().getMaxId();
        this.name = name;
        this.salary = Settings.Professions.Other.SALARY;
        this.stationsTaxBonus = Settings.Professions.Other.STATIONS_TAX_BONUS;
        this.auctionTaxBonus = Settings.Professions.Other.AUCTION_TAX_BONUS;
        this.tasksTaxBonus = Settings.Professions.Other.TASKS_TAX_BONUS;
        this.permissionOpenBank = true;
        this.permissionDeposit = true;
        this.permissionWithdraw = false;
    }

    /**
     * Creates new profession object and puts its locale to the locales hashmap in PredefinedProfession
     * @return Profession object
     */
    public static Profession createNew(int id, String name,
                      double salary, double stationsTaxBonus, double auctionTaxBonus, double tasksTaxBonus,
                      boolean permissionOpenBank, boolean permissionDeposit, boolean permissionWithdraw, String locale) {
        PredefinedProfession.getLocales().put(PredefinedProfession.valueOf(name), locale);
        return new Profession(id, name, salary, stationsTaxBonus, auctionTaxBonus, tasksTaxBonus,
                permissionOpenBank, permissionDeposit, permissionWithdraw);
    }

    public static void init(){
        if (initiated) return;
        manager.addIgnore(createNew(1, PredefinedProfession.Mayor.toString(), Settings.Professions.Mayor.SALARY,
                Settings.Professions.Mayor.STATIONS_TAX_BONUS, Settings.Professions.Mayor.AUCTION_TAX_BONUS,
                Settings.Professions.Mayor.TASKS_TAX_BONUS, true, true, true,
                "profession_mayor"));
        manager.addIgnore(createNew(2, PredefinedProfession.Helper.toString(), Settings.Professions.Helper.SALARY,
                Settings.Professions.Helper.STATIONS_TAX_BONUS, Settings.Professions.Helper.AUCTION_TAX_BONUS,
                Settings.Professions.Helper.TASKS_TAX_BONUS, true, true, false,
                "profession_helper"));
        initiated = true;
    }

    public static void add(String name){
        Profession profession = new Profession(name);
        manager.add(profession);
    }

    public static Profession get(int id) {
        return manager.get(id);
    }

    public static Profession find(String name, CommandSender sender, String messageKey){
        if (!manager.exists(name)) Localization.getInstance().locReturnTell(messageKey, sender);
        return manager.get(name);
    }

    public static double getSalary(int professionId){
        return manager.get(professionId).getSalary();
    }

    public String getLocalizedName(){
        if (Arrays.stream(PredefinedProfession.values()).map(Enum::toString).toList().contains(name)){
            return PredefinedProfession.getLocales().get(PredefinedProfession.valueOf(name));
        }
        return name;
    }

}