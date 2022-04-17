package me.rubix327.fancynations.commands;

import lombok.NonNull;
import me.rubix327.fancynations.Localization;
import org.mineacademy.fo.command.SimpleCommandGroup;
import org.mineacademy.fo.command.SimpleSubCommand;
import org.mineacademy.fo.exception.CommandException;

public abstract class SubCommandInterlayer extends SimpleSubCommand {

    Localization msgs = Localization.getInstance();
    String permLabel;

    protected SubCommandInterlayer(String sublabel) {
        super(sublabel);
        addTellPrefix(false);
        setPermission(null);
    }

    protected SubCommandInterlayer(SimpleCommandGroup parent, String sublabel) {
        super(parent, sublabel);
        addTellPrefix(false);
        setPermission(null);
    }

    protected SubCommandInterlayer(String sublabel, String permLabel) {
        super(sublabel);
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
        if (permLabel == null) { permLabel = ""; }
        if (isPlayer() && !sender.hasPermission(permLabel + "." + perm)){
            throw new CommandException(msgs.get("error_no_permission", sender));
        }
    }

}
