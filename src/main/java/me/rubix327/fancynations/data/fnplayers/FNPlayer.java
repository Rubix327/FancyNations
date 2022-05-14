package me.rubix327.fancynations.data.fnplayers;

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

@Getter @Setter(AccessLevel.PACKAGE)
@AllArgsConstructor
public class FNPlayer extends AbstractDto implements IUniqueNamable {

    private static IFNPlayerManager manager = DataManager.getFNPlayerManager();
    private static boolean initiated = false;

    private final int id;
    private final String name;

    public FNPlayer(String name) {
        this.id = manager.getMaxId() + 1;
        this.name = name;
    }

    public static void init(){
        if (initiated) return;
        manager.addIgnore(new FNPlayer(1, Settings.General.SERVER_VAR));
        initiated = true;
    }

    public static FNPlayer getFNPlayer(String name){
        return DataManager.getFNPlayerManager().get(name);
    }

    public static FNPlayer find(String name, CommandSender sender, String messageKey){
        if (!manager.exists(name)) Localization.getInstance().locReturnTell(messageKey, sender);
        return manager.get(name);
    }

    public static String getName(int playerId){
        return DataManager.getFNPlayerManager().get(playerId).getName();
    }
}
