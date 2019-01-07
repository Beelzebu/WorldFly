package io.github.beelzebu.worldfly.command;

import io.github.beelzebu.worldfly.WorldFly;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Beelzebu
 */
public class FlyCommand extends Command {

    public FlyCommand(String command, String permission, String... aliases) {
        super(command, permission, aliases);
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 1) {
                handleFlyOther(sender, Bukkit.getPlayer(args[0]), args);
            } else {
                boolean currentStatus = player.getAllowFlight();
                if (currentStatus) {
                    sender.sendMessage(WorldFly.replace(WorldFly.getPlugin(WorldFly.class).getConfig().getString("messages.fly-command.disabled"), sender));
                } else {
                    sender.sendMessage(WorldFly.replace(WorldFly.getPlugin(WorldFly.class).getConfig().getString("messages.fly-command.enabled"), sender));
                }
                player.setAllowFlight(!currentStatus);
                player.setFlying(!currentStatus);
            }
        } else {
            if (args.length == 1) {
                handleFlyOther(sender, Bukkit.getPlayer(args[0]), args);
            } else {
                sender.sendMessage(WorldFly.replace("&cPlease use /" + getName() + " <player>"));
            }
        }
    }

    private void handleFlyOther(CommandSender sender, Player target, String[] args) {
        if (target == null) {
            sender.sendMessage(WorldFly.replace("&cPlayer " + args[0] + " doesn't exists."));
        } else {
            boolean currentStatus = target.getAllowFlight();
            if (currentStatus) {
                sender.sendMessage(WorldFly.replace(WorldFly.getPlugin(WorldFly.class).getConfig().getString("messages.fly-command.disabled-other"), target));
                target.sendMessage(WorldFly.replace(WorldFly.getPlugin(WorldFly.class).getConfig().getString("messages.fly-command.disabled-other-target"), sender));
            } else {
                sender.sendMessage(WorldFly.replace(WorldFly.getPlugin(WorldFly.class).getConfig().getString("messages.fly-command.enabled-other"), target));
                target.sendMessage(WorldFly.replace(WorldFly.getPlugin(WorldFly.class).getConfig().getString("messages.fly-command.enabled-other-target"), sender));
            }
            target.setAllowFlight(!currentStatus);
            target.setFlying(!currentStatus);
        }
    }
}
