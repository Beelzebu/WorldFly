package io.github.beelzebu.worldfly;

import io.github.beelzebu.worldfly.command.CommandAPI;
import io.github.beelzebu.worldfly.command.FlyCommand;
import io.github.beelzebu.worldfly.listener.FlyListener;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class WorldFly extends JavaPlugin {

    private FlyCommand flyCommand;

    public static String replace(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static String replace(String string, CommandSender commandSender) {
        return ChatColor.translateAlternateColorCodes('&', string).replace("%name%", commandSender.getName()).replace("%displayname%", commandSender instanceof Player ? ((Player) commandSender).getDisplayName() : commandSender.getName());
    }

    @Override
    public void onDisable() {
        CommandAPI.unregisterCommand(flyCommand);
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        if (getConfig().getBoolean("command.enabled")) {
            CommandAPI.registerCommand(this, flyCommand = new FlyCommand(getConfig().getString("command.name", "fly"), getConfig().getString("command.permission"), getConfig().getString("command.aliases")));
        }
        Bukkit.getPluginManager().registerEvents(new FlyListener(this), this);
    }
}
