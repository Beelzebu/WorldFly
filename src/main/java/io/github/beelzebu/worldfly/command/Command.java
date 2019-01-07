package io.github.beelzebu.worldfly.command;

import io.github.beelzebu.worldfly.WorldFly;
import java.util.Arrays;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

/**
 * @author Beelzebu
 */
public abstract class Command extends org.bukkit.command.Command {


    public Command(String command, String permission, String... aliases) {
        super(command);
        setPermission(permission);
        setAliases(Arrays.asList(aliases));
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (getPermission() == null || sender.hasPermission(getPermission())) {
            if (Bukkit.isPrimaryThread()) {
                Bukkit.getScheduler().runTaskAsynchronously(WorldFly.getPlugin(WorldFly.class), () -> onCommand(sender, args));
            } else {
                onCommand(sender, args);
            }
        }
        return true;
    }

    public abstract void onCommand(CommandSender sender, String[] args);

}