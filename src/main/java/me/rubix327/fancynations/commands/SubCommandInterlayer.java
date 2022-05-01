package me.rubix327.fancynations.commands;

import lombok.NonNull;
import me.rubix327.fancynations.Localization;
import org.mineacademy.fo.command.SimpleCommandGroup;
import org.mineacademy.fo.command.SimpleSubCommand;
import org.mineacademy.fo.exception.CommandException;

public abstract class SubCommandInterlayer extends SimpleSubCommand {

    Localization msgs = Localization.getInstance();
    String permLabel;

    protected SubCommandInterlayer(SimpleCommandGroup group, String sublabel, String permLabel){
        super(group, sublabel);
        addTellPrefix(false);
        setPermission(null);
        this.permLabel = permLabel;
    }

    /**
     * Checks if the current sender has the given permission.
     * If instance has specified <i>permLabel</i> then full permission will look like <i>permLabel.perm</i>.
     * if no permLabel specified then permission will consist only of <i>perm</i>.
     *
     * @param perm to be checked
     * @throws CommandException if player has no specified permission.
     * Command will be aborted and the sender will receive error_no_permission message.
     */
    protected final void checkPermission(@NonNull String perm) throws CommandException {
        final String finalPerm = (permLabel == null ? perm : permLabel + "." + perm);
        if (isPlayer() && !sender.hasPermission(finalPerm)){
            throw new CommandException(msgs.get("error_no_permission", sender));
        }
    }

}
