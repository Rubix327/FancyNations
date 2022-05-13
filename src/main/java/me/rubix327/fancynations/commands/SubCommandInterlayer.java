package me.rubix327.fancynations.commands;

import lombok.NonNull;
import me.rubix327.fancynations.FancyNations;
import me.rubix327.fancynations.Localization;
import me.rubix327.fancynations.util.DependencyManager;
import me.rubix327.fancynations.util.Replacer;
import net.milkbowl.vault.economy.Economy;
import org.mineacademy.fo.command.SimpleCommandGroup;
import org.mineacademy.fo.command.SimpleSubCommand;
import org.mineacademy.fo.exception.CommandException;

public abstract class SubCommandInterlayer extends SimpleSubCommand {

    DependencyManager dependencies = DependencyManager.getInstance();
    Economy economy = FancyNations.getInstance().getEconomy();
    Localization msgs = Localization.getInstance();
    String permLabel;

    protected SubCommandInterlayer(SimpleCommandGroup group, String subLabel, String permLabel){
        super(group, subLabel);
        addTellPrefix(false);
        setPermission(null);
        this.permLabel = permLabel;
    }

    /**
     * <p>Checks if the current sender has the given permission.</p>
     * <p>If instance has specified <i>permLabel</i> then full permission will look like <i>permLabel.perm</i>.</p>
     * <p>If no permLabel was specified then permission will consist only of <i>perm</i>.</p>
     * <p>If sender does not have this permission then the command will be aborted
     * and the sender will receive error_no_permission message.</p>
     *
     * @param perm to be checked
     */
    protected final void checkPermission(@NonNull String perm) {
        if (perm.equals("")) throw new IllegalArgumentException("Permission must not be a blank string.");
        final String finalPerm = (permLabel == null || permLabel.isBlank() ? perm : permLabel + "." + perm);
        if (isPlayer() && !sender.hasPermission(finalPerm.toLowerCase())){
            locTell("error_no_permission");
        }
    }

    protected Replacer replace(String target, Object replacement){
        return new Replacer(target, String.valueOf(replacement));
    }

    /**
     * Tells localized message to the sender.
     * @param key The key from messages_x.yml
     */
    protected final void locTell(String key){
        tell(msgs.get(key, sender));
    }

    /**
     * Tells localized message to the sender replacing all the placeholders defined in r.
     * You can use <b>replace()</b> method to define placeholders and their replacements.
     * @param key The key from messages_x.yml
     * @param r Replaces
     */
    protected final void locTell(String key, Replacer... r){
        String msg = msgs.get(key, sender);
        for (Replacer replacer : r) {
            msg = msg.replace(replacer.target(), replacer.replacement());
        }
        tell(msg);
    }

    /**
     * Tells localized message to the sender and aborts the command.
     * Basically, it is just locTell + returnTell in one method.
     * @param key The key from messages_x.yml
     */
    protected final void locReturnTell(String key){
        locTell(key);
        throw new CommandException();
    }

    /**
     * Tells localized message to the sender replacing all the placeholders defined in r
     * and then aborts the command.
     * <p>You can use <b>replace()</b> method to define placeholders and their replacements.</p>
     * @param key The key from messages_x.yml
     * @param r Replaces
     */
    protected final void locReturnTell(String key, Replacer... r){
        locTell(key, r);
        throw new CommandException();
    }

    /**
     * Checks if the args[<b>i</b>] is equal to <b>command</b> ignoring case.
     * <p>You can use pipe ( | ) delimiter to split multiple commands in one line.</p>
     * <p>For example: isArg(0, "objective|obj") will return true if args[0]
     * is either equal to "objective" or to "obj"</p>
     */
    protected final boolean isArg(int i, String command){
        if (command.contains("|")){
            String[] list = command.split("\\|");
            for (String s : list){
                if (args[i].equalsIgnoreCase(s)) return true;
            }
        }
        return args[i].equalsIgnoreCase(command);
    }

    /**
     * Get localized message with the given key and defined sender.
     * @param key the key
     * @return the message
     */
    protected String getMsg(String key){
        return msgs.get(key, sender);
    }
}
